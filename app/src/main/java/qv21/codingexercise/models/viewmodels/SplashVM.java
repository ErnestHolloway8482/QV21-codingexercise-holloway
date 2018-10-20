package qv21.codingexercise.models.viewmodels;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import qv21.codingexercise.R;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.MainActivityProviderManager;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.managers.ScreenManager;
import qv21.codingexercise.utilities.LoggerUtils;
import qv21.codingexercise.utilities.RawFileUtility;
import qv21.codingexercise.views.Screen;
import qv21.codingexercise.views.WellDataListScreen;

/**
 * {@link android.arch.lifecycle.ViewModel} that defines the controller logic for the {@link qv21.codingexercise.views.SplashScreen} that is presented anytime the user starts the app.
 * This class will navigate directly to the {@link WellDataListScreen} if the data has already been seeded from the file into the database, otherwise it will
 * seed the data first before allowing the user to view the {@linkl WellDataListScreen}.
 */
public class SplashVM extends BaseVM {
    private final WellDataFacade wellDataFacade;
    private final NavigationManager navigationManager;
    private final MainActivityProviderManager mainActivityProviderManager;
    private final ScreenManager screenManager;
    private Disposable subscriber;
    private Disposable delaySubscriber;

    public SplashVM(final WellDataFacade wellDataFacade,
                    final NavigationManager navigationManager,
                    final MainActivityProviderManager mainActivityProviderManager,
                    final ScreenManager screenManager) {
        this.wellDataFacade = wellDataFacade;
        this.navigationManager = navigationManager;
        this.mainActivityProviderManager = mainActivityProviderManager;
        this.screenManager = screenManager;

        navigateToWellDataListScreen();
    }

    private void navigateToWellDataListScreen() {
        if (wellDataFacade.doesWellDataExist()) {
            mainActivityProviderManager.provideMainActivity().getViewModel().displayProgressDialog();
            setupWellDataListScreen();
        } else {
            mainActivityProviderManager.provideMainActivity().getViewModel().displayProgressDialog(R.string.reading_well_data_file);
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
        mainActivityProviderManager.runOnUiThread(this::finalizeNavigationStackForWellDataListScreen);
    }

    private void finalizeNavigationStackForWellDataListScreen() {
        mainActivityProviderManager.provideMainActivity().getViewModel().dismissProgressDialog();

        //Guarantees that after we leave the splash screen that the well data list screen is the only screen on the navigation stack.
        Screen wellDataListScreen = screenManager.getScreenFromClass(WellDataListScreen.class);
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
        InputStream inputStream = RawFileUtility.getInputStreamFromResourceId(mainActivityProviderManager.getResources(), R.raw.well_data);

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
