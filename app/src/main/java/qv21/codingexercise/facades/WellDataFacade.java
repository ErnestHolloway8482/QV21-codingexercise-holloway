package qv21.codingexercise.facades;

import java.util.List;

import io.objectbox.query.LazyList;
import qv21.codingexercise.daos.WellDataDAO;
import qv21.codingexercise.managers.DatabaseManager;
import qv21.codingexercise.managers.WellDataFileManager;
import qv21.codingexercise.mapper.WellDataMapper;
import qv21.codingexercise.models.database.WellData;

public class WellDataFacade {
    private final WellDataFileManager wellDataFileManager;
    private final WellDataMapper wellDataMapper;
    private final DatabaseManager databaseManager;
    private final WellDataDAO wellDataDAO;

    public WellDataFacade(final WellDataFileManager wellDataFileManager, final WellDataMapper wellDataMapper, final DatabaseManager databaseManager, final WellDataDAO wellDataDAO) {
        this.wellDataFileManager = wellDataFileManager;
        this.wellDataMapper = wellDataMapper;
        this.databaseManager = databaseManager;
        this.wellDataDAO = wellDataDAO;
    }

    public boolean doesWellDataExist() {
        return wellDataDAO.getNumberOfWellDataItems() > 0;
    }

    public boolean seedWellDataIntoDatabase(final String fileNameAndPath) {
        List<String> rawWellData = wellDataFileManager.readWellData(fileNameAndPath);

        if (rawWellData == null) {
            return false;
        }

        for (String csvRowData : rawWellData) {
            WellData wellData = wellDataMapper.mapWellData(csvRowData);

            if (wellData != null) {
                wellDataDAO.createWell(wellData);
            }
        }

        return true;
    }

    public LazyList<WellData> getAllWellDataItems(){
        return (LazyList<WellData>) wellDataDAO.getAllWellData();
    }

    public boolean updateWellData(final WellData wellData){
        return wellDataDAO.updateWell(wellData);
    }

    public void deleteWellData(final WellData wellData){
        wellDataDAO.deleteWellData(wellData);
    }

    public WellData getWellDataByUuid(final String uuid){
        return wellDataDAO.getWellDataByUuid(uuid);
    }

    public void closeDatabase(){
        databaseManager.closeDataBase();
    }

    public void cleanUpWellData(){
        wellDataDAO.deleteAllWellData();
        databaseManager.closeDataBase();
        databaseManager.deleteDataBase();
    }
}
