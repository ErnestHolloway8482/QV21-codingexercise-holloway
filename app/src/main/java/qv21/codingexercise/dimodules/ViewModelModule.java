package qv21.codingexercise.dimodules;

import dagger.Module;
import dagger.Provides;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.AlertDialogManager;
import qv21.codingexercise.managers.MainActivityProviderManager;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.managers.ResourceManager;
import qv21.codingexercise.managers.ScreenManager;
import qv21.codingexercise.models.viewmodels.MainActivityVM;
import qv21.codingexercise.models.viewmodels.SplashVM;
import qv21.codingexercise.models.viewmodels.WellDataDetailsVM;
import qv21.codingexercise.models.viewmodels.WellDataEditVM;
import qv21.codingexercise.models.viewmodels.WellDataListItemVM;
import qv21.codingexercise.models.viewmodels.WellDataListVM;

//A dagger {@link Module} that serves as a factory for {@link ViewModel} objects and fully enables dependency injection.
@Module
public class ViewModelModule {
    @Provides
    public static SplashVM provideSplashVM(final WellDataFacade wellDataFacade,
                                           final NavigationManager navigationManager,
                                           final MainActivityProviderManager mainActivityProviderManager,
                                           final ScreenManager screenManager) {
        return new SplashVM(wellDataFacade, navigationManager, mainActivityProviderManager, screenManager);
    }

    @Provides
    public static WellDataListVM provideWellDataListVM(final WellDataFacade wellDataFacade,
                                                       final MainActivityProviderManager mainActivityProviderManager) {
        return new WellDataListVM(wellDataFacade, mainActivityProviderManager);
    }

    @Provides
    public static WellDataListItemVM provideWellDataListItemVM(final WellDataFacade wellDataFacade,
                                                               final NavigationManager navigationManager,
                                                               final MainActivityProviderManager mainActivityProviderManager,
                                                               final ScreenManager screenManager) {
        return new WellDataListItemVM(wellDataFacade,
                navigationManager,
                mainActivityProviderManager,
                screenManager);
    }

    @Provides
    public static WellDataDetailsVM provideWellDataDetailsVM(final WellDataFacade wellDataFacade,
                                                             final NavigationManager navigationManager,
                                                             final MainActivityProviderManager mainActivityProviderManager,
                                                             final ScreenManager screenManager) {
        return new WellDataDetailsVM(wellDataFacade, navigationManager, mainActivityProviderManager, screenManager);
    }

    @Provides
    public static WellDataEditVM provideWellDataEditVM(final WellDataFacade wellDataFacade,
                                                       final NavigationManager navigationManager,
                                                       final AlertDialogManager alertDialogManager,
                                                       final MainActivityProviderManager mainActivityProviderManager,
                                                       final ResourceManager resourceManager) {
        return new WellDataEditVM(wellDataFacade,
                navigationManager,
                alertDialogManager,
                mainActivityProviderManager,
                resourceManager);

    }

    @Provides
    public static MainActivityVM provideMainActivityVM(final MainActivityProviderManager mainActivityProviderManager,
                                                       final ResourceManager resourceManager) {
        return new MainActivityVM(mainActivityProviderManager, resourceManager);
    }
}
