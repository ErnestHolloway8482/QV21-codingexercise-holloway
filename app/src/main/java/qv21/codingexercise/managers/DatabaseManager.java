package qv21.codingexercise.managers;

import javax.inject.Singleton;

/**
 * This is the {@link Singleton} interface that any database manager class should implement.
 */
@Singleton
public interface DatabaseManager {
    public static final int DATA_BASE_VERSION_NUMBER = 1;

    boolean closeDataBase();

    boolean deleteDataBase();
}
