package qv21.codingexercise.daos;

import java.util.List;

import io.objectbox.query.Query;
import qv21.codingexercise.models.databasemodels.WellDataDM;

/**
 * A Database Access Object that defines various CRUD database operations for the {@link WellDataDM} database object.
 */
public interface WellDataDAO {
    boolean createWell(final WellDataDM wellData);

    List<WellDataDM> getAllWellData();

    Query<WellDataDM> getAllWellDataQuery();

    long getNumberOfWellDataItems();

    boolean updateWell(final WellDataDM updatedWellData);

    void deleteAllWellData();

    void deleteWellData(final WellDataDM wellData);

    WellDataDM getWellDataByUuid(final String uuid);

    Query<WellDataDM> getWellDataByUuidQuery(final String uuid);
}
