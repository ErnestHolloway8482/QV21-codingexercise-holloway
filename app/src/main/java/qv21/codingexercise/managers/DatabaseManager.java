package qv21.codingexercise.managers;

public interface DatabaseManager {
    public static final int DATA_BASE_VERSION_NUMBER = 1;

    boolean openDataBase(String fileNameAndPath);

    boolean closeDataBase(String fileNameAndPath);

    boolean deleteDataBase(String fileNameAndPath);

    boolean doesDataBaseExist(String fileNameAndPath);
}
