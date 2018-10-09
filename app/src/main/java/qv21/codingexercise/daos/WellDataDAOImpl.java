package qv21.codingexercise.daos;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import qv21.codingexercise.managers.DatabaseManager;
import qv21.codingexercise.managers.DatabaseManagerImpl;
import qv21.codingexercise.models.databasemodels.WellDataDM;
import qv21.codingexercise.models.databasemodels.WellDataDM_;

public class WellDataDAOImpl implements WellDataDAO {
    final DatabaseManager databaseManager;
    final Box<WellDataDM> wellDataBox;

    public WellDataDAOImpl(final DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        wellDataBox = ((DatabaseManagerImpl) databaseManager).getBoxStore().boxFor(WellDataDM.class);
    }

    @Override
    public boolean createWell(final WellDataDM wellData) {
        return wellDataBox.put(wellData) > 0;
    }

    @Override
    public List<WellDataDM> getAllWellData() {
        QueryBuilder<WellDataDM> queryBuilder = wellDataBox.query();
        Query<WellDataDM> query = queryBuilder.build();

        return query.findLazy();
    }

    @Override
    public Query<WellDataDM> getAllWellDataQuery() {
        QueryBuilder<WellDataDM> queryBuilder = wellDataBox.query();
        Query<WellDataDM> query = queryBuilder.build();

        return query;
    }

    @Override
    public long getNumberOfWellDataItems() {
        return wellDataBox.count();
    }

    @Override
    public boolean updateWell(final WellDataDM updatedWellData) {
        return wellDataBox.put(updatedWellData) > 0;
    }

    @Override
    public void deleteAllWellData() {
        wellDataBox.removeAll();
    }

    @Override
    public void deleteWellData(final WellDataDM wellData) {
        wellDataBox.remove(wellData);
    }

    @Override
    public WellDataDM getWellDataByUuid(final String uuid) {
        QueryBuilder<WellDataDM> queryBuilder = wellDataBox.query();
        queryBuilder.equal(WellDataDM_.uuid, uuid);

        Query<WellDataDM> query = queryBuilder.build();
        return query.findFirst();
    }

    @Override
    public Query<WellDataDM> getWellDataByUuidQuery(final String uuid) {
        QueryBuilder<WellDataDM> queryBuilder = wellDataBox.query();
        queryBuilder.equal(WellDataDM_.uuid, uuid);

        Query<WellDataDM> query = queryBuilder.build();
        return query;
    }
}
