package qv21.codingexercise.models.viewmodels;

import android.arch.lifecycle.ViewModel;

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

public class SplashVM extends ViewModel {
    private final WellDataFacade wellDataFacade;
    private final NavigationManager navigationManager;
    private Disposable subscriber;
    private Disposable delaySubscriber;

    public SplashVM(final WellDataFacade wellDataFacade, final NavigationManager navigationManager) {
        this.wellDataFacade = wellDataFacade;
        this.navigationManager = navigationManager;
    }

    public void navigateToWellDataListScreen() {
        if (wellDataFacade.doesWellDataExist()) {
            setupWellDataListScreen();
        } else {
            seedWellDataBeforeSettingUpTheWellDataListScreen();
        }

        cleanupSubscribers();
    }

    private void setupWellDataListScreen() {
        int navigationDelay = 2;

        cleanupSubscribers();

        MainActivity.getInstance().displayProgressDialog(true, null);

        delaySubscriber = Observable.timer(navigationDelay, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(__ -> setupNavigationStackForWellDataListScreen(), throwable -> LoggerUtils.log(throwable.getMessage()));

        MainActivity.getInstance().displayProgressDialog(false, null);
    }

    private void setupNavigationStackForWellDataListScreen() {
        //Guarantees that after we leave the splash screen that the well data list screen is the only screen on the navigation stack.
        WellDataListScreen wellDataListScreen = new WellDataListScreen(MainActivity.getInstance());
        navigationManager.pop();
        navigationManager.push(wellDataListScreen);
        navigationManager.showScreen();
    }

    private void seedWellDataBeforeSettingUpTheWellDataListScreen() {
        cleanupSubscribers();

        MainActivity.getInstance().displayProgressDialog(true, R.string.reading_well_data_file);

        subscriber = Single.fromCallable(this::seedWellData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(__ -> setupWellDataListScreen(), throwable -> LoggerUtils.log(throwable.getMessage()));
    }

    private boolean seedWellData() {
        String wellDataFileNameAndPath = RawFileUtility.getFullNameAndPathFromResourceId(
                MainActivity.getInstance().getPackageName(),
                R.raw.well_data);

        return wellDataFacade.seedWellDataIntoDatabase(wellDataFileNameAndPath);
    }

    private void cleanupSubscribers() {
        if (delaySubscriber != null) {
            if (delaySubscriber.isDisposed()) {
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
