package qv21.codingexercise;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

import javax.inject.Inject;

import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.models.viewmodels.SplashVM;
import qv21.codingexercise.utilities.RawFileUtility;
import qv21.codingexercise.views.WellDataListScreen;

@RunWith(AndroidJUnit4.class)
public class SplashVMTest extends BaseAndroidUnitTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Inject
    WellDataFacade wellDataFacade;

    @Inject
    NavigationManager navigationManager;

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

        splashVM = new SplashVM(wellDataFacade, navigationManager);

        sleep(30);

        Assert.assertTrue(wellDataFacade.doesWellDataExist());
        Assert.assertTrue(navigationManager.peek() instanceof WellDataListScreen);
    }

    @Test
    public void setupWellDataListScreenTest() {
        InputStream inputStream = RawFileUtility.getInputStreamFromResourceId(MainActivity.getInstance().getResources(), R.raw.well_data);

        Assert.assertTrue(wellDataFacade.seedWellDataIntoDatabase(inputStream));

        sleep(30);

        splashVM = new SplashVM(wellDataFacade, navigationManager);

        Assert.assertTrue(wellDataFacade.doesWellDataExist());
        Assert.assertTrue(navigationManager.peek() instanceof WellDataListScreen);
    }
}
