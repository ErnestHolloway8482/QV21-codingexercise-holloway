package qv21.codingexercise.daos;

import java.util.List;

import qv21.codingexercise.models.databasemodels.WellDataDM;

public interface WellDataDAO {
    boolean createWell(final WellDataDM wellData);
    List<WellDataDM> getAllWellData();
    long getNumberOfWellDataItems();
    boolean updateWell(final WellDataDM updatedWellData);
    void deleteAllWellData();
    void deleteWellData(final WellDataDM wellData);
    WellDataDM getWellDataByUuid(final String uuid);
}
