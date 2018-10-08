package qv21.codingexercise;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.models.databasemodels.WellDataDM;
import qv21.codingexercise.models.viewmodels.WellDataListItemVM;
import qv21.codingexercise.utilities.LoggerUtils;
import qv21.codingexercise.views.WellDataDetailsScreen;

@RunWith(AndroidJUnit4.class)
public class WellDataListItemVMTest extends BaseAndroidUnitTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Inject
    NavigationManager navigationManager;

    @Inject
    WellDataFacade wellDataFacade;

    private WellDataListItemVM wellDataListItemVM;

    @Before
    public void setup() {
        getTestAppComponent().inject(this);

        wellDataListItemVM = new WellDataListItemVM(wellDataFacade, navigationManager);
    }

    @After
    public void tearDown() {
        wellDataFacade.cleanUpWellData();
    }

    @Test
    public void navigateToWellDataDetailsScreenTest() {
        WellDataDM wellData = new WellDataDM();

        wellDataListItemVM.wellData.set(wellData);

        wellDataListItemVM.navigateToWellDataDetailsScreen();

        sleep(2);

        Assert.assertEquals(wellData.getUuid(), wellDataFacade.getSelectedWellDataUuidFromMemoryCache());
        Assert.assertTrue(navigationManager.peek() instanceof WellDataDetailsScreen);
    }

    private void sleep(final int numberOfSeconds) {
        try {
            int timeInSecobds = 1000 * numberOfSeconds;
            Thread.sleep(timeInSecobds);
        } catch (InterruptedException e) {
            LoggerUtils.logError(e.getMessage());
        }
    }
}
