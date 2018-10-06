package qv21.codingexercise.daos;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import qv21.codingexercise.managers.DatabaseManager;
import qv21.codingexercise.managers.DatabaseManagerImpl;
import qv21.codingexercise.models.database.WellData;
import qv21.codingexercise.models.database.WellData_;

public class WellDataDAOImpl implements WellDataDAO {
    final DatabaseManager databaseManager;
    final Box<WellData> wellDataBox;

    public WellDataDAOImpl(final DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        wellDataBox = ((DatabaseManagerImpl) databaseManager).getBoxStore().boxFor(WellData.class);
    }

    @Override
    public boolean createWell(final WellData wellData) {
        return wellDataBox.put(wellData) > 0;
    }

    @Override
    public List<WellData> getAllWellData() {
        QueryBuilder<WellData> queryBuilder = wellDataBox.query();
        Query<WellData> query = queryBuilder.build();

        return query.findLazy();
    }

    @Override
    public long getNumberOfWellDataItems() {
        return wellDataBox.count();
    }

    @Override
    public boolean updateWell(final WellData updatedWellData) {
        return wellDataBox.put(updatedWellData) > 0;
    }

    @Override
    public void deleteAllWellData() {
        wellDataBox.removeAll();
    }

    @Override
    public void deleteWellData(final WellData wellData) {
        wellDataBox.remove(wellData);
    }

    @Override
    public WellData getWellDataByUuid(final String uuid) {
        QueryBuilder<WellData> queryBuilder = wellDataBox.query();
        queryBuilder.equal(WellData_.uuid, uuid);

        Query<WellData> query = queryBuilder.build();
        return query.findFirst();
    }
}
