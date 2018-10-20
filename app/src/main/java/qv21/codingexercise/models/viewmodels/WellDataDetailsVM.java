package qv21.codingexercise.models.viewmodels;

import android.databinding.ObservableField;

import java.util.List;

import io.objectbox.android.AndroidScheduler;
import io.objectbox.reactive.DataSubscriptionList;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.MainActivityProviderManager;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.managers.ScreenManager;
import qv21.codingexercise.models.databasemodels.WellDataDM;
import qv21.codingexercise.models.domainmodels.WellDataItemDOM;
import qv21.codingexercise.views.Screen;
import qv21.codingexercise.views.WellDataEditScreen;

/**
 * @link android.arch.lifecycle.ViewModel} that serves as the controller logic for the {@link qv21.codingexercise.views.WellDataDetailsScreen}
 */
public class WellDataDetailsVM extends BaseVM {
    private static final String SCREEN_NAME = "Well Details";

    private final WellDataFacade wellDataFacade;
    private final NavigationManager navigationManager;
    private final MainActivityProviderManager mainActivityProviderManager;
    private final ScreenManager screenManager;

    private DataSubscriptionList subscriber = new DataSubscriptionList();

    public ObservableField<WellDataDM> wellData = new ObservableField<>();
    public ObservableField<WellDataItemDOM> wellDataDom = new ObservableField<>();

    public WellDataDetailsVM(final WellDataFacade wellDataFacade,
                             final NavigationManager navigationManager,
                             final MainActivityProviderManager mainActivityProviderManager,
                             final ScreenManager screenManager) {
        this.wellDataFacade = wellDataFacade;
        this.navigationManager = navigationManager;
        this.mainActivityProviderManager = mainActivityProviderManager;
        this.screenManager = screenManager;

        setupToolBar();

        getWellDataByUuid(wellDataFacade.getSelectedWellDataUuidFromMemoryCache());
    }

    @Override
    public void setupToolBar() {
        mainActivityProviderManager.provideMainActivity().getViewModel().displayToolBar(true, SCREEN_NAME);
    }

    public void navigateToWellDataListScreen() {
        wellDataFacade.clearSelectedWellDataUuidFromMemoryCache();

        mainActivityProviderManager.runOnUiThread(this::setupWellDataListScreen);
    }

    public void navigateToWellDataEditScreen() {
        mainActivityProviderManager.runOnUiThread(this::setupWellDataEditScreen);
    }

    private void getWellDataByUuid(final String uuid) {
        wellDataFacade.getWellDataByUuidQuery(uuid)
                .subscribe(subscriber)
                .on(AndroidScheduler.mainThread()).observer(this::updateDataModels);
    }

    private void updateDataModels(final List<WellDataDM> value) {
        if (value == null || value.isEmpty()) {
            return;
        }

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
        Screen wellDataEditScreen = screenManager.getScreenFromClass(WellDataEditScreen.class);
        navigationManager.push(wellDataEditScreen);
        navigationManager.showScreen();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mainActivityProviderManager.provideMainActivity().getViewModel().dismissToolbar();
        cleanupSubscribers();
    }
}
