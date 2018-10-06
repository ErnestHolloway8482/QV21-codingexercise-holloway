package qv21.codingexercise.managers;

import java.io.File;

import javax.inject.Singleton;

import io.objectbox.BoxStore;
import io.objectbox.DebugFlags;
import qv21.codingexercise.MainActivity;
import qv21.codingexercise.models.database.MyObjectBox;

@Singleton
public class DatabaseManagerImpl implements DatabaseManager {
    private final BoxStore boxStore;

    public DatabaseManagerImpl(final String fileNameAndPath, final boolean testModeEnabled) {
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
}
