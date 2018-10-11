package qv21.codingexercise.dimodules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import qv21.codingexercise.managers.DatabaseManager;
import qv21.codingexercise.managers.DatabaseManagerImpl;
import qv21.codingexercise.managers.MemoryCacheManager;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.managers.WellDataFileManager;

//A dagger {@link Module} that serves as a factory for Manager type objects and fully enables dependency injection.
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
