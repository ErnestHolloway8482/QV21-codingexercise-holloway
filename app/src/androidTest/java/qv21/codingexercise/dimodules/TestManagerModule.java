package qv21.codingexercise.dimodules;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import qv21.codingexercise.MainActivityProviderManageAndroidTestImpl;
import qv21.codingexercise.managers.AlertDialogManager;
import qv21.codingexercise.managers.DatabaseManager;
import qv21.codingexercise.managers.DatabaseManagerImpl;
import qv21.codingexercise.managers.MainActivityProviderManager;
import qv21.codingexercise.managers.MemoryCacheManager;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.managers.NavigationManagerImpl;
import qv21.codingexercise.managers.ResourceManager;
import qv21.codingexercise.managers.ResourceManagerImpl;
import qv21.codingexercise.managers.ScreenManager;
import qv21.codingexercise.managers.ScreenManagerImpl;
import qv21.codingexercise.managers.WellDataFileManager;
import qv21.codingexercise.utilities.BuildConfigUtility;
import qv21.codingexercise.views.SplashScreen;
import qv21.codingexercise.views.WellDataDetailsScreen;
import qv21.codingexercise.views.WellDataEditScreen;
import qv21.codingexercise.views.WellDataListScreen;

//A dagger {@link Module} that serves as a factory for Manager type objects and fully enables dependency injection.
@Module
public class TestManagerModule {
    @Singleton
    @Provides
    public static NavigationManager provideNavigationManager() {
        return new NavigationManagerImpl();
    }

    @Singleton
    @Provides
    public static DatabaseManager providerDatabaseManager() {
        if (BuildConfigUtility.isIsInAndroidTestMode()) {
            return new DatabaseManagerImpl("well_data_android_test", false);
        } else if (BuildConfigUtility.isInTestMode()) {
            return new DatabaseManagerImpl("well_data_unit_test", true);
        } else {
            return new DatabaseManagerImpl("well_data", false);
        }
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

    @Singleton
    @Provides
    public static AlertDialogManager provideAlertDialogManager(final MainActivityProviderManager mainActivityProviderManager) {
        return Mockito.mock(AlertDialogManager.class);
    }

    @Singleton
    @Provides
    public static MainActivityProviderManager provideMainActivityProviderManager() {
        return new MainActivityProviderManageAndroidTestImpl();
    }

    @Singleton
    @Provides
    public static ResourceManager provideResourceManager(final MainActivityProviderManager mainActivityProviderManager) {
        return Mockito.mock(ResourceManagerImpl.class);
    }

    @Singleton
    @Provides
    public static ScreenManager provideScreenManager(final MainActivityProviderManager mainActivityProviderManager) {
        ScreenManagerImpl screenManager = Mockito.mock(ScreenManagerImpl.class);

        SplashScreen splashScreen = Mockito.mock(SplashScreen.class);
        WellDataListScreen wellDataListScreen = Mockito.mock(WellDataListScreen.class);
        WellDataDetailsScreen wellDataDetailsScreen = Mockito.mock(WellDataDetailsScreen.class);
        WellDataEditScreen wellDataEditScreen = Mockito.mock(WellDataEditScreen.class);


        Mockito.when(screenManager.getScreenFromClass(SplashScreen.class)).thenReturn(splashScreen);
        Mockito.when(screenManager.getScreenFromClass(WellDataListScreen.class)).thenReturn(wellDataListScreen);
        Mockito.when(screenManager.getScreenFromClass(WellDataDetailsScreen.class)).thenReturn(wellDataDetailsScreen);
        Mockito.when(screenManager.getScreenFromClass(WellDataEditScreen.class)).thenReturn(wellDataEditScreen);

        return screenManager;
    }
}
