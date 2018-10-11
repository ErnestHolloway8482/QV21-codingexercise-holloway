package qv21.codingexercise.dimodules;

import dagger.Module;
import dagger.Provides;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.NavigationManager;
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
    public static SplashVM provideSplashVM(final WellDataFacade wellDataFacade, final NavigationManager navigationManager) {
        return new SplashVM(wellDataFacade, navigationManager);
    }

    @Provides
    public static WellDataListVM provideWellDataListVM(final WellDataFacade wellDataFacade) {
        return new WellDataListVM(wellDataFacade);
    }

    @Provides
    public static WellDataListItemVM provideWellDataListItemVM(final WellDataFacade wellDataFacade, final NavigationManager navigationManager) {
        return new WellDataListItemVM(wellDataFacade, navigationManager);
    }

    @Provides
    public static WellDataDetailsVM provideWellDataDetailsVM(final WellDataFacade wellDataFacade, final NavigationManager navigationManager) {
        return new WellDataDetailsVM(wellDataFacade, navigationManager);
    }

    @Provides
    public static WellDataEditVM provideWellDataEditVM(final WellDataFacade wellDataFacade, final NavigationManager navigationManager) {
        return new WellDataEditVM(wellDataFacade, navigationManager);
    }

    @Provides
    public static MainActivityVM provideMainActivityVM() {
        return new MainActivityVM();
    }
}
