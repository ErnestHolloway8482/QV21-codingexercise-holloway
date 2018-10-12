package qv21.codingexercise.integrationtests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import qv21.codingexercise.BaseAndroidUnitTest;
import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.daos.WellDataDAO;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.AlertDialogManager;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.models.databasemodels.WellDataDM;
import qv21.codingexercise.models.viewmodels.WellDataEditVM;
import qv21.codingexercise.views.WellDataDetailsScreen;
import qv21.codingexercise.views.WellDataEditScreen;
import qv21.codingexercise.views.WellDataListScreen;

@RunWith(AndroidJUnit4.class)
public class WellDataEditVMTest extends BaseAndroidUnitTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Inject
    NavigationManager navigationManager;

    @Inject
    WellDataFacade wellDataFacade;

    @Inject
    AlertDialogManager alertDialogManager;

    @Inject
    WellDataDAO wellDataDAO;

    private WellDataEditVM wellDataEditVM;

    @Before
    public void setup() {
        getTestAppComponent().inject(this);

        WellDataDM wellDataDM = new WellDataDM();

        wellDataDAO.createWell(wellDataDM);

        wellDataFacade.storeSelectedWellDataUuidToMemoryCache(wellDataDM);

        navigationManager.clearAllViewsFromStack();

        MainActivity.getInstance().runOnUiThread(() -> {
            WellDataListScreen wellDataListScreen = new WellDataListScreen(MainActivity.getInstance());
            navigationManager.push(wellDataListScreen);

            WellDataDetailsScreen wellDataDetailsScreen = new WellDataDetailsScreen(MainActivity.getInstance());
            navigationManager.push(wellDataDetailsScreen);

            WellDataEditScreen wellDataEditScreen = new WellDataEditScreen(MainActivity.getInstance());
            navigationManager.push(wellDataEditScreen);
        });

        sleep(2);
    }

    @After
    public void tearDown() {
        wellDataFacade.cleanUpWellData();
        navigationManager.clearAllViewsFromStack();
    }

    @Test
    public void wellDataIsLoadedTest() {
        Assert.assertFalse(navigationManager.isOnLastScreen());
        Assert.assertTrue(navigationManager.peek() instanceof WellDataEditScreen);

        wellDataEditVM = new WellDataEditVM(wellDataFacade, navigationManager, alertDialogManager);

        sleep(10);

        Assert.assertNotNull(wellDataEditVM.wellData.get());
    }

    @Test
    public void navigateToWellDataDetailsScreenTest() {
        Assert.assertFalse(navigationManager.isOnLastScreen());
        Assert.assertTrue(navigationManager.peek() instanceof WellDataEditScreen);

        wellDataEditVM = new WellDataEditVM(wellDataFacade, navigationManager, alertDialogManager);

        wellDataEditVM.navigateToWellDataDetailsScreen();

        sleep(10);

        Assert.assertTrue(navigationManager.peek() instanceof WellDataDetailsScreen);
        Assert.assertFalse(navigationManager.isOnLastScreen());
        Assert.assertNotNull(wellDataFacade.getSelectedWellDataUuidFromMemoryCache());
    }

    @Test
    public void deleteWellDataTest() {
        Assert.assertFalse(navigationManager.isOnLastScreen());
        Assert.assertTrue(navigationManager.peek() instanceof WellDataEditScreen);

        wellDataEditVM = new WellDataEditVM(wellDataFacade, navigationManager, alertDialogManager);

        sleep(10);

        String uuid = wellDataFacade.getSelectedWellDataUuidFromMemoryCache();

        wellDataEditVM.deleteWellData();

        sleep(10);

        Assert.assertNull(wellDataFacade.getSelectedWellDataUuidFromMemoryCache());
        Assert.assertNull(wellDataEditVM.wellData.get());
        Assert.assertNull(wellDataFacade.getWellDataByUuid(uuid));

        Assert.assertTrue(navigationManager.peek() instanceof WellDataListScreen);
        Assert.assertTrue(navigationManager.isOnLastScreen());
    }

    @Test
    public void updateWellDataTest() {
        Assert.assertFalse(navigationManager.isOnLastScreen());
        Assert.assertTrue(navigationManager.peek() instanceof WellDataEditScreen);

        wellDataEditVM = new WellDataEditVM(wellDataFacade, navigationManager, alertDialogManager);

        sleep(10);

        String uuid = wellDataFacade.getSelectedWellDataUuidFromMemoryCache();

        WellDataDM currentWellData = wellDataFacade.getWellDataByUuid(uuid);
        currentWellData.setOwnerName("Owner-A");

        wellDataEditVM.wellData.set(currentWellData);

        wellDataEditVM.updateWellData();

        sleep(10);

        WellDataDM foundWellData = wellDataFacade.getWellDataByUuid(uuid);

        sleep(10);

        Assert.assertNotNull(foundWellData);
        Assert.assertEquals("Owner-A", foundWellData.getOwnerName());

        Assert.assertTrue(navigationManager.peek() instanceof WellDataDetailsScreen);
        Assert.assertFalse(navigationManager.isOnLastScreen());
    }
}
