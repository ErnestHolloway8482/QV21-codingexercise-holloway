package qv21.codingexercise.managers;

/**
 * This is the interface that any database manager class should implement.
 */
public interface DatabaseManager {
    public static final int DATA_BASE_VERSION_NUMBER = 1;

    boolean closeDataBase();

    boolean deleteDataBase();
}
