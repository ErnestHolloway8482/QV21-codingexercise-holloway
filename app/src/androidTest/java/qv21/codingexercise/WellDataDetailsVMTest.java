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
import qv21.codingexercise.daos.WellDataDAO;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.models.databasemodels.WellDataDM;
import qv21.codingexercise.models.viewmodels.WellDataDetailsVM;
import qv21.codingexercise.views.WellDataDetailsScreen;
import qv21.codingexercise.views.WellDataEditScreen;
import qv21.codingexercise.views.WellDataListScreen;

@RunWith(AndroidJUnit4.class)
public class WellDataDetailsVMTest extends BaseAndroidUnitTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Inject
    NavigationManager navigationManager;

    @Inject
    WellDataFacade wellDataFacade;

    @Inject
    WellDataDAO wellDataDAO;

    private WellDataDetailsVM wellDataDetailsVM;

    @Before
    public void setup() {
        getTestAppComponent().inject(this);

        WellDataDM wellDataDM = new WellDataDM();

        wellDataDAO.createWell(wellDataDM);

        wellDataFacade.storeSelectedWellDataUuidToMemoryCache(wellDataDM);

        MainActivity.getInstance().runOnUiThread(() -> {
            WellDataListScreen wellDataListScreen = new WellDataListScreen(MainActivity.getInstance());
            navigationManager.push(wellDataListScreen);

            WellDataDetailsScreen wellDataDetailsScreen = new WellDataDetailsScreen(MainActivity.getInstance());
            navigationManager.push(wellDataDetailsScreen);
        });
    }

    @After
    public void tearDown() {
        wellDataFacade.cleanUpWellData();
        navigationManager.clearAllViewsFromStack();
    }

    @Test
    public void wellDataIsLoadedTest() {
        Assert.assertFalse(navigationManager.isOnLastScreen());
        Assert.assertFalse(navigationManager.peek() instanceof WellDataDetailsScreen);

        wellDataDetailsVM = new WellDataDetailsVM(wellDataFacade, navigationManager);

        sleep(10);

        Assert.assertNotNull(wellDataDetailsVM.wellData.get());
    }

    @Test
    public void navigateToWellDataListScreenTest() {
        Assert.assertFalse(navigationManager.isOnLastScreen());
        Assert.assertFalse(navigationManager.peek() instanceof WellDataDetailsScreen);

        wellDataDetailsVM = new WellDataDetailsVM(wellDataFacade, navigationManager);

        wellDataDetailsVM.navigateToWellDataListScreen();

        sleep(10);

        Assert.assertTrue(navigationManager.peek() instanceof WellDataListScreen);
        Assert.assertTrue(navigationManager.isOnLastScreen());
        Assert.assertNull(wellDataFacade.getSelectedWellDataUuidFromMemoryCache());
    }

    @Test
    public void navigateToWellDataEditScreenTest() {
        Assert.assertFalse(navigationManager.isOnLastScreen());
        Assert.assertFalse(navigationManager.peek() instanceof WellDataDetailsScreen);

        wellDataDetailsVM = new WellDataDetailsVM(wellDataFacade, navigationManager);

        wellDataDetailsVM.navigateToWellDataEditScreen();

        sleep(10);

        Assert.assertTrue(navigationManager.peek() instanceof WellDataEditScreen);
        Assert.assertNotNull(wellDataFacade.getSelectedWellDataUuidFromMemoryCache());
    }
}
