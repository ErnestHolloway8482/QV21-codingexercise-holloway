package qv21.codingexercise.managers;

import java.io.File;

import javax.inject.Singleton;

import io.objectbox.BoxStore;
import io.objectbox.DebugFlags;
import qv21.codingexercise.application.QV21Application;
import qv21.codingexercise.models.databasemodels.MyObjectBox;
import qv21.codingexercise.utilities.LoggerUtils;

/**
 * This is a {@link Singleton} implementation of {@link DatabaseManager} that allows the {@link BoxStore} to be initialized, opened, and closed and serves
 * as the main mechanism for allowing CRUD based operations on {@link io.objectbox.annotation.Entity} objects.
 */
@Singleton
public class DatabaseManagerImpl implements DatabaseManager {
    private final BoxStore boxStore;

    public DatabaseManagerImpl(final String fileNameAndPath, final boolean javaTestModeEnabled) {
        boxStore = openDatabase(fileNameAndPath, javaTestModeEnabled);
    }

    @Override
    public boolean closeDataBase() {
        try {
            return attemptToCloseDatabase();
        } catch (Exception e) {
            LoggerUtils.logError(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteDataBase() {
        try {
            return attemptToCloseDatabase();
        } catch (Exception e) {
            LoggerUtils.logError(e.getMessage());
            return false;
        }
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }

    private boolean attemptToCloseDatabase() {
        boxStore.closeThreadResources();
        boxStore.close();

        if (boxStore.isClosed()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean attemptToDeleteDatabase() {
        boxStore.close();
        return boxStore.deleteAllFiles();
    }

    private BoxStore openDatabase(final String fileNameAndPath, final boolean javaTestModeEnabled) {
        try {
            return createBoxStore(fileNameAndPath, javaTestModeEnabled);
        } catch (Exception e) {
            LoggerUtils.logError(e.getMessage());
            return null;
        }
    }

    private BoxStore createBoxStore(final String fileNameAndPath, final boolean javaTestModeEnabled) {
        if (javaTestModeEnabled) {
            return createBoxStoreForJava(fileNameAndPath);
        } else {
            return createBoxStoreForAndroid(fileNameAndPath);
        }
    }

//    private BoxStore createBoxStore(final String fileNameAndPath, final boolean testModeEnabled) {
//        if (testModeEnabled) {
//            return createBoxStoreForJava(fileNameAndPath);
//        } else {
//            if (BuildConfigUtility.isIsInAndroidTestMode()) {
//                if (BuildConfigUtility.getBoxStore() == null) {
//                    BuildConfigUtility.setBoxStore(createBoxStoreForAndroid(fileNameAndPath));
//                } else {
//                    BuildConfigUtility.getBoxStore().closeThreadResources();
//                    BuildConfigUtility.getBoxStore().close();
//                    BoxStore.deleteAllFiles(MainActivity.getInstance(), fileNameAndPath);
//                    BuildConfigUtility.setBoxStore(createBoxStoreForAndroid(fileNameAndPath));
//                }
//
//                return BuildConfigUtility.getBoxStore();
//            } else {
//                return createBoxStoreForAndroid(fileNameAndPath);
//            }
//        }
//    }

    private BoxStore createBoxStoreForJava(final String fileNameAndPath) {
        File testFile = new File(fileNameAndPath);

        BoxStore.deleteAllFiles(testFile);

        return MyObjectBox.builder()
                .debugFlags(DebugFlags.LOG_QUERIES | DebugFlags.LOG_QUERY_PARAMETERS)
                .directory(testFile)
                .build();
    }

    private BoxStore createBoxStoreForAndroid(final String fileNameAndPath) {
        return MyObjectBox.builder()
                .androidContext(QV21Application.getInstance())
                .name(fileNameAndPath)
                .build();
    }
}
