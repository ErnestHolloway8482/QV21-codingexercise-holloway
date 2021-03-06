package qv21.codingexercise.facades;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.inject.Singleton;

import io.objectbox.query.LazyList;
import io.objectbox.query.Query;
import qv21.codingexercise.daos.WellDataDAO;
import qv21.codingexercise.managers.DatabaseManager;
import qv21.codingexercise.managers.MemoryCacheManager;
import qv21.codingexercise.managers.WellDataFileManager;
import qv21.codingexercise.mapper.WellDataMapper;
import qv21.codingexercise.models.databasemodels.WellDataDM;

/**
 * This class combines the interaction between the {@link WellDataFileManager}, {@link WellDataMapper}, {@link DatabaseManager}, {@link WellDataDAO}, and the {@link MemoryCacheManager}
 * into a {@link Singleton} class that handles the primary operations.
 * <p>
 * -Seeding the well data information from the .csv file into the embedded database
 * -Cleaning up the database once the user is finished with a session
 * -Determing if the database exists
 * -CRUD operations on the {@link WellDataDM} database objects.
 * <p>
 * This makes it simple to give this type of operation to any of the {@link android.arch.lifecycle.ViewModel} and simplfiy the number of dependencies that need to be placed
 * inside of controller logic related classes. It all makes it simpler to do integration testing.
 */
@Singleton
public class WellDataFacade {
    private final WellDataFileManager wellDataFileManager;
    private final WellDataMapper wellDataMapper;
    private final DatabaseManager databaseManager;
    private final WellDataDAO wellDataDAO;
    private final MemoryCacheManager memoryCacheManager;

    public WellDataFacade(final WellDataFileManager wellDataFileManager,
                          final WellDataMapper wellDataMapper,
                          final DatabaseManager databaseManager,
                          final WellDataDAO wellDataDAO,
                          final MemoryCacheManager memoryCacheManager) {
        this.wellDataFileManager = wellDataFileManager;
        this.wellDataMapper = wellDataMapper;
        this.databaseManager = databaseManager;
        this.wellDataDAO = wellDataDAO;
        this.memoryCacheManager = memoryCacheManager;
    }

    public boolean doesWellDataExist() {
        return wellDataDAO.getNumberOfWellDataItems() > 0;
    }

    public boolean doesWellDataFileExist(String parentPath, String fileName){
        File file = new File(parentPath, fileName);

        return file.exists();
    }

    public boolean seedWellDataIntoDatabase(final String fileNameAndPath) {
        List<String> rawWellData = wellDataFileManager.readWellData(fileNameAndPath);

        return storeDataToDatabase(rawWellData);
    }

    public boolean seedWellDataIntoDatabase(final InputStream inputStream) {
        List<String> rawWellData = wellDataFileManager.readWellData(inputStream);

        return storeDataToDatabase(rawWellData);
    }

    public LazyList<WellDataDM> getAllWellDataItems() {
        return (LazyList<WellDataDM>) wellDataDAO.getAllWellData();
    }

    public Query<WellDataDM> getAllWellDataQuery() {
        return wellDataDAO.getAllWellDataQuery();
    }

    public boolean updateWellData(final WellDataDM wellData) {
        return wellDataDAO.updateWell(wellData);
    }

    public void deleteWellData(final WellDataDM wellData) {
        wellDataDAO.deleteWellData(wellData);
    }

    public WellDataDM getWellDataByUuid(final String uuid) {
        return wellDataDAO.getWellDataByUuid(uuid);
    }

    public Query<WellDataDM> getWellDataByUuidQuery(final String uuid) {
        return wellDataDAO.getWellDataByUuidQuery(uuid);
    }

    public void storeSelectedWellDataUuidToMemoryCache(final WellDataDM wellData) {
        memoryCacheManager.setSelectedWellDataUuid(wellData.getUuid());
    }

    public String getSelectedWellDataUuidFromMemoryCache() {
        return memoryCacheManager.getSelectedWellDataUuid();
    }

    public void clearSelectedWellDataUuidFromMemoryCache() {
        memoryCacheManager.setSelectedWellDataUuid(null);
    }

    public boolean closeDatabase() {
        return databaseManager.closeDataBase();
    }

    public void cleanUpWellData() {
        wellDataDAO.deleteAllWellData();
        databaseManager.closeDataBase();
        databaseManager.deleteDataBase();
        memoryCacheManager.setSelectedWellDataUuid(null);
    }

    private boolean storeDataToDatabase(final List<String> rawWellData) {
        if (rawWellData == null) {
            return false;
        }

        for (String csvRowData : rawWellData) {
            WellDataDM wellData = wellDataMapper.mapWellData(csvRowData);

            if (wellData != null) {
                wellDataDAO.createWell(wellData);
            }
        }

        return true;
    }
}
