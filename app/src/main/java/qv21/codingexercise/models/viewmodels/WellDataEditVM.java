package qv21.codingexercise.models.viewmodels;

import android.databinding.ObservableField;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.models.database.WellData;
import qv21.codingexercise.utilities.LoggerUtils;

public class WellDataEditVM {
    private final NavigationManager navigationManager;
    private final WellDataFacade wellDataFacade;
    private Disposable subscriber;

    public ObservableField<WellData> wellData = new ObservableField<>();

    public WellDataEditVM(final WellDataFacade wellDataFacade, final NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
        this.wellDataFacade = wellDataFacade;

        getWellDataByUuid(wellDataFacade.getSelectedWellDataUuidFromMemoryCache());
    }

    public void navigateToWellDataDetailsScreen() {
        navigationManager.pop();
    }

    public void deleteWellData() {
        cleanupSubscribers();

        subscriber = Single.fromCallable(() -> {
            wellDataFacade.clearSelectedWellDataUuidFromMemoryCache();
            wellDataFacade.deleteWellData(wellData.get());
            return null;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(__ -> {
                }, throwable -> LoggerUtils.log(throwable.getMessage()));
    }

    public void updateWellData() {
        cleanupSubscribers();

        subscriber = Single.fromCallable(() -> wellDataFacade.updateWellData(wellData.get()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(__ -> {
                }, throwable -> LoggerUtils.log(throwable.getMessage()));
    }

    private void getWellDataByUuid(final String uuid) {
        cleanupSubscribers();

        subscriber = Single.fromCallable(() -> wellDataFacade.getWellDataByUuid(uuid))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> wellData.set(value), throwable -> LoggerUtils.log(throwable.getMessage()));
    }

    private void cleanupSubscribers() {
        if (subscriber != null) {
            if (!subscriber.isDisposed()) {
                subscriber.dispose();
                subscriber = null;
            }
        }
    }
}
