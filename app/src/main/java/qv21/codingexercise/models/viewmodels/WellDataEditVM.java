package qv21.codingexercise.models.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.models.databasemodels.WellDataDM;
import qv21.codingexercise.models.domainmodels.WellDataItemDOM;
import qv21.codingexercise.utilities.LoggerUtils;

public class WellDataEditVM extends ViewModel {
    private final NavigationManager navigationManager;
    private final WellDataFacade wellDataFacade;
    private Disposable subscriber;

    public ObservableField<WellDataDM> wellData = new ObservableField<>();
    public ObservableField<WellDataItemDOM> wellDataDom = new ObservableField<>();

    public WellDataEditVM(final WellDataFacade wellDataFacade, final NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
        this.wellDataFacade = wellDataFacade;

        getWellDataByUuid(wellDataFacade.getSelectedWellDataUuidFromMemoryCache());
    }

    public void navigateToWellDataDetailsScreen() {
        MainActivity.getInstance().onBackPressed();
    }

    public void deleteWellData() {
        cleanupSubscribers();

        subscriber = Single.fromCallable(this::cleanUpWellDataItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(__ -> navigateToWellDataListScreen(), throwable -> LoggerUtils.log(throwable.getMessage()));
    }

    public void updateWellData() {
        cleanupSubscribers();

        subscriber = Single.fromCallable(() -> wellDataFacade.updateWellData(wellData.get()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(__ -> navigateToWellDataDetailsScreen(), throwable -> LoggerUtils.log(throwable.getMessage()));
    }

    private void getWellDataByUuid(final String uuid) {
        cleanupSubscribers();

        subscriber = Single.fromCallable(() -> wellDataFacade.getWellDataByUuid(uuid))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    wellData.set(value);
                    wellDataDom.set(WellDataItemDOM.create(wellData.get()));
                }, throwable -> LoggerUtils.log(throwable.getMessage()));
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
        MainActivity.getInstance().runOnUiThread(this::setupWellDataListScreen);
    }

    private void setupWellDataListScreen() {
        navigationManager.pop();
        navigationManager.pop();
        navigationManager.showScreen();
    }

    private boolean cleanUpWellDataItem() {
        wellDataFacade.clearSelectedWellDataUuidFromMemoryCache();
        wellDataFacade.deleteWellData(wellData.get());
        wellData.set(null);
        wellDataDom.set(null);

        return true;
    }
}
