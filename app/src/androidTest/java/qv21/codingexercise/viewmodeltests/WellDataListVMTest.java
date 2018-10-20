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
import qv21.codingexercise.models.viewmodels.WellDataListVM;
import qv21.codingexercise.utilities.RawFileUtility;

@RunWith(AndroidJUnit4.class)
public class WellDataListVMTest extends BaseAndroidUnitTest {
    @Inject
    WellDataFacade wellDataFacade;

    @Inject
    NavigationManager navigationManager;

    @Inject
    MainActivityProviderManager mainActivityProviderManager;

    @Inject
    ScreenManager screenManager;



    private WellDataListVM wellDataListVM;

    @Before
    public void setup() {
        getTestAppComponent().inject(this);

        wellDataListVM = new WellDataListVM(wellDataFacade, navigationManager, mainActivityProviderManager, screenManager);
    }

    @After
    public void tearDown() {
        wellDataFacade.cleanUpWellData();
    }

    @Test
    public void wellDataListIsEmptyTest() {
        sleep(2);

        Assert.assertTrue(wellDataListVM.isListEmpty.get());
        Assert.assertEquals(0, wellDataListVM.recyclerViewAdapter.get().getItemCount());
    }

    @Test
    public void wellDataListIsNotEmptyTest() {
        InputStream inputStream = RawFileUtility.getInputStreamFromResourceId(mainActivityProviderManager.getResources(), R.raw.well_data);

        Assert.assertTrue(wellDataFacade.seedWellDataIntoDatabase(inputStream));

        sleep(10);

        Assert.assertTrue(wellDataFacade.doesWellDataExist());

        Assert.assertFalse(wellDataListVM.isListEmpty.get());

        int totalItemCount = wellDataListVM.recyclerViewAdapter.get().getItemCount();

        Assert.assertTrue(totalItemCount > 0);
    }
}
