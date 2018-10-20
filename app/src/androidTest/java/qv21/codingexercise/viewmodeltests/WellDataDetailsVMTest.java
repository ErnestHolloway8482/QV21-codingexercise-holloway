package qv21.codingexercise.viewmodeltests;

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
import qv21.codingexercise.managers.MainActivityProviderManager;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.managers.ScreenManager;
import qv21.codingexercise.models.databasemodels.WellDataDM;
import qv21.codingexercise.models.viewmodels.WellDataDetailsVM;
import qv21.codingexercise.views.Screen;
import qv21.codingexercise.views.WellDataDetailsScreen;
import qv21.codingexercise.views.WellDataEditScreen;
import qv21.codingexercise.views.WellDataListScreen;

@RunWith(AndroidJUnit4.class)
public class WellDataDetailsVMTest extends BaseAndroidUnitTest {
    @Inject
    NavigationManager navigationManager;

    @Inject
    WellDataFacade wellDataFacade;

    @Inject
    WellDataDAO wellDataDAO;

    @Inject
    ScreenManager screenManager;

    @Inject
    MainActivityProviderManager mainActivityProviderManager;

    private WellDataDetailsVM wellDataDetailsVM;

    @Before
    public void setup() {
        getTestAppComponent().inject(this);

        setupSelectedWellDataItem();

        setupNavigationStackForWellDataDetailsScreen();

        assertThatWellDataDetailsScreenIsAtTopOfNavigationStack();

        wellDataDetailsVM = new WellDataDetailsVM(wellDataFacade, navigationManager, mainActivityProviderManager, screenManager);
    }


    @After
    public void tearDown() {
        wellDataFacade.cleanUpWellData();
        navigationManager.clearAllViewsFromStack();
    }

    @Test
    public void wellDataIsLoadedTest() {
        sleep(10);

        Assert.assertNotNull(wellDataDetailsVM.wellData.get());
    }

    @Test
    public void navigateToWellDataListScreenTest() {
        wellDataDetailsVM.navigateToWellDataListScreen();

        sleep(10);

        Assert.assertTrue(navigationManager.peek() instanceof WellDataListScreen);
        Assert.assertTrue(navigationManager.isOnLastScreen());
        Assert.assertNull(wellDataFacade.getSelectedWellDataUuidFromMemoryCache());
    }

    @Test
    public void navigateToWellDataEditScreenTest() {
        wellDataDetailsVM.navigateToWellDataEditScreen();

        sleep(10);

        Assert.assertTrue(navigationManager.peek() instanceof WellDataEditScreen);
        Assert.assertNotNull(wellDataFacade.getSelectedWellDataUuidFromMemoryCache());
    }

    private void setupNavigationStackForWellDataDetailsScreen() {
        Screen wellDataListScreen = screenManager.getScreenFromClass(WellDataListScreen.class);
        navigationManager.push(wellDataListScreen);

        Screen wellDataDetailsScreen = screenManager.getScreenFromClass(WellDataDetailsScreen.class);
        navigationManager.push(wellDataDetailsScreen);
    }

    private void setupSelectedWellDataItem() {
        WellDataDM wellDataDM = new WellDataDM();

        wellDataDAO.createWell(wellDataDM);

        wellDataFacade.storeSelectedWellDataUuidToMemoryCache(wellDataDM);
    }

    private void assertThatWellDataDetailsScreenIsAtTopOfNavigationStack() {
        Assert.assertFalse(navigationManager.isOnLastScreen());
        Assert.assertTrue(navigationManager.peek() instanceof WellDataDetailsScreen);
    }
}
