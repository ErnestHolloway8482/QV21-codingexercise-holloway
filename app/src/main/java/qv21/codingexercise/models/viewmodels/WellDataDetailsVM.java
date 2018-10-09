package qv21.codingexercise.models.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import java.util.List;

import io.objectbox.android.AndroidScheduler;
import io.objectbox.reactive.DataSubscriptionList;
import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.models.databasemodels.WellDataDM;
import qv21.codingexercise.models.domainmodels.WellDataItemDOM;
import qv21.codingexercise.views.WellDataEditScreen;

public class WellDataDetailsVM extends ViewModel {
    private final WellDataFacade wellDataFacade;
    private final NavigationManager navigationManager;
    private DataSubscriptionList subscriber = new DataSubscriptionList();

    public ObservableField<WellDataDM> wellData = new ObservableField<>();
    public ObservableField<WellDataItemDOM> wellDataDom = new ObservableField<>();

    public WellDataDetailsVM(final WellDataFacade wellDataFacade, final NavigationManager navigationManager) {
        this.wellDataFacade = wellDataFacade;
        this.navigationManager = navigationManager;

        getWellDataByUuid(wellDataFacade.getSelectedWellDataUuidFromMemoryCache());
    }

    public void navigateToWellDataListScreen() {
        wellDataFacade.clearSelectedWellDataUuidFromMemoryCache();

        MainActivity.getInstance().runOnUiThread(this::setupWellDataListScreen);
    }

    public void navigateToWellDataEditScreen() {
        MainActivity.getInstance().runOnUiThread(this::setupWellDataEditScreen);
    }

    private void getWellDataByUuid(final String uuid) {
        wellDataFacade.getWellDataByUuidQuery(uuid)
                .subscribe(subscriber)
                .on(AndroidScheduler.mainThread()).observer(this::updateDataModels);
    }

    private void updateDataModels(final List<WellDataDM> value) {
        wellData.set(value.get(0));
        wellDataDom.set(WellDataItemDOM.create(wellData.get()));
    }

    private void cleanupSubscribers() {
        if (subscriber != null) {
            if (!subscriber.isCanceled()) {
                subscriber.cancel();
                subscriber = null;
            }
        }
    }

    private void setupWellDataListScreen() {
        navigationManager.pop();
        navigationManager.showScreen();
    }

    private void setupWellDataEditScreen() {
        WellDataEditScreen wellDataEditScreen = new WellDataEditScreen(MainActivity.getInstance());
        navigationManager.push(wellDataEditScreen);
        navigationManager.showScreen();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        cleanupSubscribers();
    }
}
