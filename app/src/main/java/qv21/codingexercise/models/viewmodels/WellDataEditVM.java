package qv21.codingexercise.models.viewmodels;

import android.databinding.ObservableField;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.MemoryCacheManager;
import qv21.codingexercise.models.database.WellData;
import qv21.codingexercise.utilities.LoggerUtils;

public class WellDataEditVM {
    private final MemoryCacheManager memoryCacheManager;
    private final WellDataFacade wellDataFacade;
    private Disposable disposable;

    public ObservableField<WellData> wellData = new ObservableField<>();

    public WellDataEditVM(final MemoryCacheManager memoryCacheManager, final WellDataFacade wellDataFacade) {
        this.memoryCacheManager = memoryCacheManager;
        this.wellDataFacade = wellDataFacade;

        getWellDataByUuid(memoryCacheManager.getSelectedWellDataUuid());
    }

    public void navigateToWellDataDetailsScreen() {

    }

    public void deleteWellData() {
        cleanupSubscribers();

        disposable = Single.fromCallable(() -> {
            memoryCacheManager.setSelectedWellDataUuid(null);
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

        disposable = Single.fromCallable(() -> wellDataFacade.updateWellData(wellData.get()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(__ -> {
                }, throwable -> LoggerUtils.log(throwable.getMessage()));
    }

    private void getWellDataByUuid(final String uuid) {
        cleanupSubscribers();

        disposable = Single.fromCallable(() -> wellDataFacade.getWellDataByUuid(uuid))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> wellData.set(value), throwable -> LoggerUtils.log(throwable.getMessage()));
    }

    private void cleanupSubscribers() {
        if (disposable != null) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
                disposable = null;
            }
        }
    }
}
