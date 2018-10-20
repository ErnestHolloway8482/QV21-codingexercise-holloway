package qv21.codingexercise.viewmodeltests;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

import javax.inject.Inject;

import qv21.codingexercise.BaseAndroidUnitTest;
import qv21.codingexercise.R;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.MainActivityProviderManager;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.managers.ScreenManager;
import qv21.codingexercise.models.viewmodels.SplashVM;
import qv21.codingexercise.utilities.RawFileUtility;
import qv21.codingexercise.views.WellDataListScreen;

@RunWith(AndroidJUnit4.class)
public class SplashVMTest extends BaseAndroidUnitTest {
    @Inject
    WellDataFacade wellDataFacade;

    @Inject
    NavigationManager navigationManager;

    @Inject
    MainActivityProviderManager mainActivityProviderManager;

    @Inject
    ScreenManager screenManager;

    private SplashVM splashVM;

    @Before
    public void setup() {
        getTestAppComponent().inject(this);
    }

    @After
    public void tearDown() {
        wellDataFacade.cleanUpWellData();
    }

    @Test
    public void seedWellDataBeforeSettingUpTheWellDataListScreenTest() {
        Assert.assertFalse(wellDataFacade.doesWellDataExist());

        splashVM = new SplashVM(wellDataFacade, navigationManager, mainActivityProviderManager, screenManager);

        sleep(10);

        Assert.assertTrue(wellDataFacade.doesWellDataExist());

        sleep(2);

        Assert.assertTrue(navigationManager.isOnLastScreen());
        Assert.assertTrue(navigationManager.peek() instanceof WellDataListScreen);
    }

    @Test
    public void setupWellDataListScreenTest() {
        InputStream inputStream = RawFileUtility.getInputStreamFromResourceId(mainActivityProviderManager.getResources(), R.raw.well_data);

        Assert.assertTrue(wellDataFacade.seedWellDataIntoDatabase(inputStream));

        sleep(10);

        splashVM = new SplashVM(wellDataFacade, navigationManager, mainActivityProviderManager, screenManager);

        Assert.assertTrue(wellDataFacade.doesWellDataExist());

        sleep(10);

        Assert.assertTrue(navigationManager.peek() instanceof WellDataListScreen);
    }
}
