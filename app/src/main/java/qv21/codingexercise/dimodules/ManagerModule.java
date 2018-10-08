package qv21.codingexercise.dimodules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import qv21.codingexercise.managers.DatabaseManager;
import qv21.codingexercise.managers.DatabaseManagerImpl;
import qv21.codingexercise.managers.MemoryCacheManager;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.managers.WellDataFileManager;

@Module
public class ManagerModule {
    @Singleton
    @Provides
    public static NavigationManager provideNavigationManager() {
        return new NavigationManager();
    }

    @Singleton
    @Provides
    public static DatabaseManager providerDatabaseManager() {
        return new DatabaseManagerImpl("well_data", false);
    }

    @Singleton
    @Provides
    public static WellDataFileManager provideWElWellDataFileManager() {
        return new WellDataFileManager();
    }

    @Singleton
    @Provides
    public static MemoryCacheManager provideMemoryCacheManager() {
        return new MemoryCacheManager();
    }
}
