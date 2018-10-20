package qv21.codingexercise.integrationtests;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import qv21.codingexercise.BaseAndroidUnitTest;
import qv21.codingexercise.daos.WellDataDAO;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.AlertDialogManager;
import qv21.codingexercise.managers.MainActivityProviderManager;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.managers.ResourceManager;
import qv21.codingexercise.managers.ScreenManager;
import qv21.codingexercise.models.databasemodels.WellDataDM;
import qv21.codingexercise.models.viewmodels.WellDataEditVM;
import qv21.codingexercise.views.Screen;
import qv21.codingexercise.views.WellDataDetailsScreen;
import qv21.codingexercise.views.WellDataEditScreen;
import qv21.codingexercise.views.WellDataListScreen;

@RunWith(AndroidJUnit4.class)
public class WellDataEditVMTest extends BaseAndroidUnitTest {
    @Inject
    NavigationManager navigationManager;

    @Inject
    WellDataFacade wellDataFacade;

    @Inject
    AlertDialogManager alertDialogManager;

    @Inject
    WellDataDAO wellDataDAO;

    @Inject
    MainActivityProviderManager mainActivityProviderManager;

    @Inject
    ResourceManager resourceManager;

    @Inject
    ScreenManager screenManager;


    private WellDataEditVM wellDataEditVM;

    @Before
    public void setup() {
        getTestAppComponent().inject(this);

        WellDataDM wellDataDM = new WellDataDM();

        wellDataDAO.createWell(wellDataDM);

        wellDataFacade.storeSelectedWellDataUuidToMemoryCache(wellDataDM);

        navigationManager.clearAllViewsFromStack();

        Screen wellDataListScreen = screenManager.getScreenFromClass(WellDataListScreen.class);
        navigationManager.push(wellDataListScreen);

        Screen wellDataDetailsScreen = screenManager.getScreenFromClass(WellDataDetailsScreen.class);
        navigationManager.push(wellDataDetailsScreen);

        Screen wellDataEditScreen = screenManager.getScreenFromClass(WellDataEditScreen.class);
        navigationManager.push(wellDataEditScreen);

        assertThatNavigationStackIsOnWellDataEditScreen();
    }

    @After
    public void tearDown() {
        wellDataFacade.cleanUpWellData();
        navigationManager.clearAllViewsFromStack();
    }

    @Test
    public void wellDataIsLoadedTest() {
        wellDataEditVM = new WellDataEditVM(wellDataFacade, navigationManager, alertDialogManager, mainActivityProviderManager, resourceManager);

        sleep(10);

        Assert.assertNotNull(wellDataEditVM.wellData.get());
    }

    @Test
    public void navigateToWellDataDetailsScreenTest() {
        wellDataEditVM = new WellDataEditVM(wellDataFacade, navigationManager, alertDialogManager, mainActivityProviderManager, resourceManager);

        wellDataEditVM.navigateToWellDataDetailsScreen();

        sleep(10);

        Assert.assertTrue(navigationManager.peek() instanceof WellDataDetailsScreen);
        Assert.assertFalse(navigationManager.isOnLastScreen());
        Assert.assertNotNull(wellDataFacade.getSelectedWellDataUuidFromMemoryCache());
    }

    @Test
    public void deleteWellDataTest() {
        wellDataEditVM = new WellDataEditVM(wellDataFacade, navigationManager, alertDialogManager, mainActivityProviderManager, resourceManager);

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
        wellDataEditVM = new WellDataEditVM(wellDataFacade, navigationManager, alertDialogManager, mainActivityProviderManager, resourceManager);

        sleep(10);

        String uuid = wellDataFacade.getSelectedWellDataUuidFromMemoryCache();

        WellDataDM currentWellData = wellDataFacade.getWellDataByUuid(uuid);
        currentWellData.setOwnerName("Owner-A");

        wellDataEditVM.wellDataDom.get().ownerName = "Owner-A";

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

    private void assertThatNavigationStackIsOnWellDataEditScreen() {
        Assert.assertFalse(navigationManager.isOnLastScreen());
        Assert.assertTrue(navigationManager.peek() instanceof WellDataEditScreen);
    }
}
