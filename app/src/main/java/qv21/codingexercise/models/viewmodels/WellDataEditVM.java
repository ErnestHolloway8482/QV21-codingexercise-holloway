package qv21.codingexercise.models.viewmodels;

import android.databinding.ObservableField;

import io.reactivex.Completable;
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

/**
 * {@link android.arch.lifecycle.ViewModel} that serves as the controller logic for the {@link qv21.codingexercise.views.WellDataEditScreen}
 */
public class WellDataEditVM extends BaseVM {
    private static final String SCREEN_NAME = "Edit Well Details";

    private final NavigationManager navigationManager;
    private final WellDataFacade wellDataFacade;
    private Disposable subscriber;

    public ObservableField<WellDataDM> wellData = new ObservableField<>();
    public ObservableField<WellDataItemDOM> wellDataDom = new ObservableField<>();

    public WellDataEditVM(final WellDataFacade wellDataFacade, final NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
        this.wellDataFacade = wellDataFacade;

        setupToolBar();

        getWellDataByUuid(wellDataFacade.getSelectedWellDataUuidFromMemoryCache());
    }

    @Override
    public void setupToolBar() {
        MainActivity.getInstance().getViewModel().displayToolBar(true, SCREEN_NAME);
    }

    public void navigateToWellDataDetailsScreen() {
        MainActivity.getInstance().runOnUiThread(this::setupWellDataDetailsScreen);
    }

    public void deleteWellData() {
        cleanupSubscribers();

        subscriber = Completable.fromAction(this::cleanUpWellDataItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
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

    @Override
    protected void onCleared() {
        super.onCleared();
        MainActivity.getInstance().getViewModel().dismissToolbar();
        cleanupSubscribers();
    }
}
