package qv21.codingexercise.dimodules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import qv21.codingexercise.daos.WellDataDAO;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.DatabaseManager;
import qv21.codingexercise.managers.MemoryCacheManager;
import qv21.codingexercise.managers.WellDataFileManager;
import qv21.codingexercise.mapper.WellDataMapper;

@Module
public class FacadeModule {
    @Singleton
    @Provides
    public static WellDataFacade provideWellDataFacade(final WellDataFileManager wellDataFileManager,
                                                       final WellDataMapper wellDataMapper,
                                                       final DatabaseManager databaseManager,
                                                       final WellDataDAO wellDataDAO,
                                                       final MemoryCacheManager memoryCacheManager) {
        return new WellDataFacade(wellDataFileManager, wellDataMapper, databaseManager, wellDataDAO, memoryCacheManager);
    }
}
