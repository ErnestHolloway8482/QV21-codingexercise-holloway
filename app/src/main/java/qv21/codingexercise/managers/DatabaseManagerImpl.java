package qv21.codingexercise.managers;

import java.io.File;

import javax.inject.Singleton;

import io.objectbox.BoxStore;
import io.objectbox.DebugFlags;
import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.models.databasemodels.MyObjectBox;
import qv21.codingexercise.utilities.BuildConfigUtility;

/**
 * This is a {@link Singleton} implementation of {@link DatabaseManager} that allows the {@link BoxStore} to be initialized, opened, and closed and serves
 * as the main mechanism for allowing CRUD based operations on {@link io.objectbox.annotation.Entity} objects.
 */
@Singleton
public class DatabaseManagerImpl implements DatabaseManager {
    private final BoxStore boxStore;

    public DatabaseManagerImpl(final String fileNameAndPath, final boolean testModeEnabled) {
        boxStore = openDatabase(fileNameAndPath, testModeEnabled);
    }

    @Override
    public boolean closeDataBase() {
        boxStore.closeThreadResources();
        boxStore.close();

        if (boxStore.isClosed()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteDataBase() {
        boxStore.close();
        return boxStore.deleteAllFiles();
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }

    private BoxStore openDatabase(final String fileNameAndPath, final boolean testModeEnabled) {
        try {
            return createBoxStore(fileNameAndPath, testModeEnabled);
        } catch (Exception e) {
            return MyObjectBox.builder().buildDefault();
        }
    }

    private BoxStore createBoxStore(final String fileNameAndPath, final boolean testModeEnabled) {
        if (testModeEnabled) {
            return createBoxStoreForJava(fileNameAndPath);
        } else {
            if (BuildConfigUtility.isIsInAndroidTestMode()) {
                if (BuildConfigUtility.getBoxStore() == null) {
                    BuildConfigUtility.setBoxStore(createBoxStoreForAndroid(fileNameAndPath));
                } else {
                    BuildConfigUtility.getBoxStore().closeThreadResources();
                    BuildConfigUtility.getBoxStore().close();
                    BoxStore.deleteAllFiles(MainActivity.getInstance(), fileNameAndPath);
                    BuildConfigUtility.setBoxStore(createBoxStoreForAndroid(fileNameAndPath));
                }

                return BuildConfigUtility.getBoxStore();
            } else {
                return createBoxStoreForAndroid(fileNameAndPath);
            }
        }
    }

    private BoxStore createBoxStoreForJava(final String fileNameAndPath) {
        File testFile = new File(fileNameAndPath);

        BoxStore.deleteAllFiles(testFile);

        return MyObjectBox.builder()
                .debugFlags(DebugFlags.LOG_QUERIES | DebugFlags.LOG_QUERY_PARAMETERS)
                .directory(testFile)
                .build();
    }

    private BoxStore createBoxStoreForAndroid(final String fileNameAndPath) {
        BoxStore boxStore = MyObjectBox.builder()
                .androidContext(MainActivity.getInstance())
                .name(fileNameAndPath)
                .build();

        return boxStore;
    }
}
