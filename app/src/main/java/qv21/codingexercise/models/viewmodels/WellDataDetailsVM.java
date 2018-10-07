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
import qv21.codingexercise.models.database.WellData;
import qv21.codingexercise.utilities.LoggerUtils;
import qv21.codingexercise.views.WellDataEditScreen;

public class WellDataDetailsVM extends ViewModel {
    private final WellDataFacade wellDataFacade;
    private final NavigationManager navigationManager;
    private Disposable subscriber;

    public ObservableField<WellData> wellData = new ObservableField<>();

    public WellDataDetailsVM(final WellDataFacade wellDataFacade, final NavigationManager navigationManager) {
        this.wellDataFacade = wellDataFacade;
        this.navigationManager = navigationManager;

        getWellDataByUuid(wellDataFacade.getSelectedWellDataUuidFromMemoryCache());
    }

    public void navigateToWellDataListScreen() {
        wellDataFacade.clearSelectedWellDataUuidFromMemoryCache();
        navigationManager.pop();
    }

    public void navigateToWellDataEditScreen() {
        WellDataEditScreen wellDataEditScreen = new WellDataEditScreen(MainActivity.getInstance());
        navigationManager.push(wellDataEditScreen);
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
