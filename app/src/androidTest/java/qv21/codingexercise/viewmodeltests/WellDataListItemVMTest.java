package qv21.codingexercise.viewmodeltests;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import qv21.codingexercise.BaseAndroidUnitTest;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.MainActivityProviderManager;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.managers.ScreenManager;
import qv21.codingexercise.models.databasemodels.WellDataDM;
import qv21.codingexercise.models.viewmodels.WellDataListItemVM;
import qv21.codingexercise.views.WellDataDetailsScreen;

@RunWith(AndroidJUnit4.class)
public class WellDataListItemVMTest extends BaseAndroidUnitTest {
    @Inject
    NavigationManager navigationManager;

    @Inject
    WellDataFacade wellDataFacade;

    @Inject
    MainActivityProviderManager mainActivityProviderManager;

    @Inject
    ScreenManager screenManager;

    private WellDataListItemVM wellDataListItemVM;

    @Before
    public void setup() {
        getTestAppComponent().inject(this);
    }

    @After
    public void tearDown() {
        wellDataFacade.cleanUpWellData();
    }

    @Test
    public void navigateToWellDataDetailsScreenTest() {
        wellDataListItemVM = new WellDataListItemVM(wellDataFacade, navigationManager, mainActivityProviderManager, screenManager);

        WellDataDM wellData = new WellDataDM();

        wellDataListItemVM.wellData.set(wellData);

        wellDataListItemVM.navigateToWellDataDetailsScreen();

        sleep(2);

        Assert.assertEquals(wellData.getUuid(), wellDataFacade.getSelectedWellDataUuidFromMemoryCache());
        Assert.assertTrue(navigationManager.peek() instanceof WellDataDetailsScreen);
    }
}
