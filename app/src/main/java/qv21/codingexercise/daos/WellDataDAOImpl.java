package qv21.codingexercise.daos;

import java.util.List;

import qv21.codingexercise.models.database.WellData;

public class WellDataDAOImpl implements WellDataDAO {
    @Override
    public boolean createWell(final WellData wellData) {
        return false;
    }

    @Override
    public List<WellData> getAllWellData() {
        return null;
    }

    @Override
    public int getNumberOfWellDataItems() {
        return 0;
    }

    @Override
    public boolean updateWell(final WellData updatedWellData) {
        return false;
    }

    @Override
    public boolean deleteAllWellData() {
        return false;
    }

    @Override
    public boolean deleteWellData(final WellData wellData) {
        return false;
    }

    @Override
    public WellData getWellDataByUuid(final String uuid) {
        return null;
    }
}
