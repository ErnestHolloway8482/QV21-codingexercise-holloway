package qv21.codingexercise.models.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.models.database.WellData;
import qv21.codingexercise.views.WellDataDetailsScreen;

public class WellDataItemVM extends ViewModel {
    private final WellDataFacade wellDataFacade;
    private final NavigationManager navigationManager;

    public ObservableField<WellData> wellData = new ObservableField<>();

    public WellDataItemVM(final WellDataFacade wellDataFacade, final NavigationManager navigationManager){
        this.wellDataFacade = wellDataFacade;
        this.navigationManager = navigationManager;
    }

    public void navigateToWellDataDetailsScreen(){
        wellDataFacade.storeSelectedWellDataUuidToMemoryCache(wellData.get());

        WellDataDetailsScreen wellDataDetailsScreen = new WellDataDetailsScreen(MainActivity.getInstance());
        navigationManager.push(wellDataDetailsScreen);
    }
}
