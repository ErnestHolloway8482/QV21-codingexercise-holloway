package qv21.codingexercise.models.viewmodels;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import qv21.codingexercise.R;
import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.utilities.LoggerUtils;
import qv21.codingexercise.utilities.RawFileUtility;
import qv21.codingexercise.views.WellDataListScreen;

public class SplashVM {
    private final WellDataFacade wellDataFacade;
    private final NavigationManager navigationManager;
    private Disposable subscriber;
    private Disposable delaySubscriber;

    public SplashVM(final WellDataFacade wellDataFacade, final NavigationManager navigationManager) {
        this.wellDataFacade = wellDataFacade;
        this.navigationManager = navigationManager;

        navigateToWellDataListScreen();
    }

    private void navigateToWellDataListScreen() {
        if (wellDataFacade.doesWellDataExist()) {
            MainActivity.getInstance().getViewModel().displayProgressDialog();
            setupWellDataListScreen();
        } else {
            MainActivity.getInstance().getViewModel().displayProgressDialog(R.string.reading_well_data_file);
            seedWellDataBeforeSettingUpTheWellDataListScreen();
        }
    }

    private void setupWellDataListScreen() {
        int navigationDelay = 2;

        cleanupSubscribers();

        delaySubscriber = Observable.timer(navigationDelay, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(__ -> setupNavigationStackForWellDataListScreen(), throwable -> LoggerUtils.log(throwable.getMessage()));
    }

    private void setupNavigationStackForWellDataListScreen() {
        MainActivity.getInstance().runOnUiThread(this::finalizeNavigationStackForWellDataListScreen);
    }

    private void finalizeNavigationStackForWellDataListScreen() {
        MainActivity.getInstance().getViewModel().dismissProgressDialog();

        //Guarantees that after we leave the splash screen that the well data list screen is the only screen on the navigation stack.
        WellDataListScreen wellDataListScreen = new WellDataListScreen(MainActivity.getInstance());
        navigationManager.clearAllViewsFromStack();
        navigationManager.push(wellDataListScreen);
        navigationManager.showScreen();
    }

    private void seedWellDataBeforeSettingUpTheWellDataListScreen() {
        cleanupSubscribers();

        subscriber = Single.fromCallable(this::seedWellData)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(__ -> setupWellDataListScreen(), throwable -> LoggerUtils.log(throwable.getMessage()));
    }

    private boolean seedWellData() {
        InputStream inputStream = RawFileUtility.getInputStreamFromResourceId(MainActivity.getInstance().getResources(), R.raw.well_data);

        return wellDataFacade.seedWellDataIntoDatabase(inputStream);
    }

    private void cleanupSubscribers() {
        if (delaySubscriber != null) {
            if (!delaySubscriber.isDisposed()) {
                delaySubscriber.dispose();
                delaySubscriber = null;
            }
        }

        if (subscriber != null) {
            if (!subscriber.isDisposed()) {
                subscriber.dispose();
                subscriber = null;
            }
        }
    }
}
