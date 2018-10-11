package qv21.codingexercise.dimodules;

import dagger.Module;
import dagger.Provides;
import qv21.codingexercise.daos.WellDataDAO;
import qv21.codingexercise.daos.WellDataDAOImpl;
import qv21.codingexercise.managers.DatabaseManager;

//A dagger {@link Module} that serves as a factory for the Database Access Objects and fully enables dependency injection.
@Module
public class DAOModule {
    @Provides
    public static WellDataDAO provideWellDataDAO(final DatabaseManager databaseManager) {
        return new WellDataDAOImpl(databaseManager);
    }
}
