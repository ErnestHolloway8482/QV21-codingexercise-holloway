package qv21.codingexercise.models.viewmodels;

import android.databinding.ObservableField;

import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.models.databasemodels.WellDataDM;
import qv21.codingexercise.models.domainmodels.WellDataItemDOM;
import qv21.codingexercise.views.WellDataDetailsScreen;

/**
 * {@link android.arch.lifecycle.ViewModel} that serves as the controller logic for the well data items that are displayed in the list as part of the {@link qv21.codingexercise.adapters.WellDataListRecyclerAdapter}
 */
public class WellDataListItemVM extends BaseVM {
    private final WellDataFacade wellDataFacade;
    private final NavigationManager navigationManager;

    public ObservableField<WellDataDM> wellData = new ObservableField<>();

    public ObservableField<WellDataItemDOM> wellDataDom = new ObservableField<>();

    public WellDataListItemVM(final WellDataFacade wellDataFacade, final NavigationManager navigationManager) {
        this.wellDataFacade = wellDataFacade;
        this.navigationManager = navigationManager;
    }

    public void navigateToWellDataDetailsScreen() {
        wellDataFacade.storeSelectedWellDataUuidToMemoryCache(wellData.get());

        MainActivity.getInstance().runOnUiThread(this::setupWellDataDetailsScreen);
    }

    private void setupWellDataDetailsScreen() {
        WellDataDetailsScreen wellDataDetailsScreen = new WellDataDetailsScreen(MainActivity.getInstance());

        navigationManager.push(wellDataDetailsScreen);
        navigationManager.showScreen();
    }
}
