package qv21.codingexercise.models.viewmodels;

import android.databinding.ObservableField;

import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.MainActivityProviderManager;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.managers.ScreenManager;
import qv21.codingexercise.models.databasemodels.WellDataDM;
import qv21.codingexercise.models.domainmodels.WellDataItemDOM;
import qv21.codingexercise.views.Screen;
import qv21.codingexercise.views.WellDataDetailsScreen;

/**
 * {@link android.arch.lifecycle.ViewModel} that serves as the controller logic for the well data items that are displayed in the list as part of the {@link qv21.codingexercise.adapters.WellDataListRecyclerAdapter}
 */
public class WellDataListItemVM extends BaseVM {
    private final WellDataFacade wellDataFacade;
    private final NavigationManager navigationManager;
    private final MainActivityProviderManager mainActivityProviderManager;
    private final ScreenManager screenManager;

    public ObservableField<WellDataDM> wellData = new ObservableField<>();

    public ObservableField<WellDataItemDOM> wellDataDom = new ObservableField<>();

    public WellDataListItemVM(final WellDataFacade wellDataFacade,
                              final NavigationManager navigationManager,
                              final MainActivityProviderManager mainActivityProviderManager,
                              final ScreenManager screenManager) {
        this.wellDataFacade = wellDataFacade;
        this.navigationManager = navigationManager;
        this.mainActivityProviderManager = mainActivityProviderManager;
        this.screenManager = screenManager;
    }

    public void navigateToWellDataDetailsScreen() {
        wellDataFacade.storeSelectedWellDataUuidToMemoryCache(wellData.get());

        mainActivityProviderManager.runOnUiThread(this::setupWellDataDetailsScreen);
    }

    private void setupWellDataDetailsScreen() {
        Screen wellDataDetailsScreen = screenManager.getScreenFromClass(WellDataDetailsScreen.class);

        navigationManager.push(wellDataDetailsScreen);
        navigationManager.showScreen();
    }
}
