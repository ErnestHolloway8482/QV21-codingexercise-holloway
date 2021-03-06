package qv21.codingexercise.models.viewmodels;

import android.databinding.ObservableField;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import qv21.codingexercise.R;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.AlertDialogManager;
import qv21.codingexercise.managers.MainActivityProviderManager;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.managers.ResourceManager;
import qv21.codingexercise.models.databasemodels.WellDataDM;
import qv21.codingexercise.models.domainmodels.WellDataItemDOM;
import qv21.codingexercise.utilities.LoggerUtils;

/**
 * {@link android.arch.lifecycle.ViewModel} that serves as the controller logic for the {@link qv21.codingexercise.views.WellDataEditScreen}
 */
public class WellDataEditVM extends BaseVM {
    private static final String SCREEN_NAME = "Edit Well Details";

    private final NavigationManager navigationManager;
    private final WellDataFacade wellDataFacade;
    private final AlertDialogManager alertDialogManager;
    private final MainActivityProviderManager mainActivityProviderManager;
    private final ResourceManager resourceManager;

    private Disposable subscriber;

    public ObservableField<WellDataDM> wellData = new ObservableField<>();
    public ObservableField<WellDataItemDOM> wellDataDom = new ObservableField<>();

    public WellDataEditVM(final WellDataFacade wellDataFacade,
                          final NavigationManager navigationManager,
                          final AlertDialogManager alertDialogManager,
                          final MainActivityProviderManager mainActivityProviderManager,
                          final ResourceManager resourceManager) {
        this.navigationManager = navigationManager;
        this.wellDataFacade = wellDataFacade;
        this.alertDialogManager = alertDialogManager;
        this.mainActivityProviderManager = mainActivityProviderManager;
        this.resourceManager = resourceManager;

        setupToolBar();

        getWellDataByUuid(wellDataFacade.getSelectedWellDataUuidFromMemoryCache());
    }

    @Override
    public void setupToolBar() {
        mainActivityProviderManager.provideMainActivity().getViewModel().displayToolBar(true, SCREEN_NAME);
    }

    public void navigateToWellDataDetailsScreen() {
        mainActivityProviderManager.runOnUiThread(this::setupWellDataDetailsScreen);
    }

    public void deleteWellData() {
        cleanupSubscribers();

        String title = resourceManager.getString(R.string.delete_well_data_alert_title);
        String body = resourceManager.getString(R.string.delete_well_data_alert_message);
        String actionButton1Text = resourceManager.getString(R.string.delete_well_data_alert_action_delete_message);
        String actionButton2Text = resourceManager.getString(R.string.delete_well_data_alert_action_cancel_message);

        alertDialogManager.displayAlertMessage(
                title,
                body,
                actionButton1Text,
                this::performDeleteAsync,
                actionButton2Text,
                () -> {
                });
    }

    public void updateWellData() {
        cleanupSubscribers();

        subscriber = Completable.fromAction(this::copyContentsFromDOMAndUpdate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void copyContentsFromDOMAndUpdate() {
        WellDataItemDOM.updateContentsOfWellData(wellData.get(), wellDataDom.get());
        wellDataFacade.updateWellData(wellData.get());

        navigateToWellDataDetailsScreen();
    }

    private void getWellDataByUuid(final String uuid) {
        cleanupSubscribers();

        subscriber = Single.fromCallable(() -> retrieveWellDataByUUIDFromDatabase(uuid))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(__ -> {
                }, throwable -> LoggerUtils.log(throwable.getMessage()));
    }

    private boolean retrieveWellDataByUUIDFromDatabase(final String uuid) {
        WellDataDM wellDataDM = wellDataFacade.getWellDataByUuid(uuid);
        wellData.set(wellDataDM);
        wellDataDom.set(WellDataItemDOM.create(wellData.get()));

        return (wellDataDM != null);
    }

    private void cleanupSubscribers() {
        if (subscriber != null) {
            if (!subscriber.isDisposed()) {
                subscriber.dispose();
                subscriber = null;
            }
        }
    }

    private void navigateToWellDataListScreen() {
        mainActivityProviderManager.runOnUiThread(this::setupWellDataListScreen);
    }

    private void setupWellDataDetailsScreen() {
        navigationManager.pop();
        navigationManager.showScreen();
    }

    private void setupWellDataListScreen() {
        navigationManager.pop();
        navigationManager.pop();
        navigationManager.showScreen();
    }

    private void cleanUpWellDataItem() {
        WellDataItemDOM.updateContentsOfWellData(wellData.get(), wellDataDom.get());
        wellDataFacade.clearSelectedWellDataUuidFromMemoryCache();
        wellDataFacade.deleteWellData(wellData.get());
        wellData.set(null);
        wellDataDom.set(null);

        navigateToWellDataListScreen();
    }

    private void performDeleteAsync() {
        subscriber = Completable.fromAction(this::cleanUpWellDataItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mainActivityProviderManager.provideMainActivity().getViewModel().dismissToolbar();
        cleanupSubscribers();
    }
}
