package qv21.codingexercise.managers;

import java.io.File;

import javax.inject.Singleton;

import io.objectbox.BoxStore;
import io.objectbox.DebugFlags;
import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.models.databasemodels.MyObjectBox;
import qv21.codingexercise.utilities.LoggerUtils;

/**
 * This is a {@link Singleton} implementation of {@link DatabaseManager} that allows the {@link BoxStore} to be initialized, opened, and closed and serves
 * as the main mechanism for allowing CRUD based operations on {@link io.objectbox.annotation.Entity} objects.
 */
@Singleton
public class DatabaseManagerImpl implements DatabaseManager {
    private BoxStore boxStore;

    public DatabaseManagerImpl(final String fileNameAndPath, final boolean testModeEnabled) {
        try {
            openDatabase(fileNameAndPath, testModeEnabled);
        } catch (Exception e) {
            LoggerUtils.logError(e.getMessage());
        }
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

    private void openDatabase(final String fileNameAndPath, final boolean testModeEnabled) {
        if (testModeEnabled) {
            File testFile = new File(fileNameAndPath + "_test");

            boxStore = MyObjectBox.builder()
                    .debugFlags(DebugFlags.LOG_QUERIES | DebugFlags.LOG_QUERY_PARAMETERS)
                    .directory(testFile)
                    .build();
        } else {
            boxStore = MyObjectBox.builder()
                    .androidContext(MainActivity.getInstance())
                    .name(fileNameAndPath)
                    .build();
        }
    }
}
