package qv21.codingexercise.daos;

import java.util.List;

import qv21.codingexercise.models.database.WellData;

public interface WellDataDAO {
    boolean createWell(final WellData wellData);
    List<WellData> getAllWellData();
    long getNumberOfWellDataItems();
    boolean updateWell(final WellData updatedWellData);
    void deleteAllWellData();
    void deleteWellData(final WellData wellData);
    WellData getWellDataByUuid(final String uuid);
}
