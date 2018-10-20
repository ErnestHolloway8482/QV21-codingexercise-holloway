package qv21.codingexercise.integrationtests;

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
import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.MainActivityProviderManager;
import qv21.codingexercise.models.viewmodels.WellDataListVM;
import qv21.codingexercise.utilities.RawFileUtility;

@RunWith(AndroidJUnit4.class)
public class WellDataListVMTest extends BaseAndroidUnitTest {
    @Inject
    WellDataFacade wellDataFacade;

    @Inject
    MainActivityProviderManager mainActivityProviderManager;

    private WellDataListVM wellDataListVM;


    @Before
    public void setup() {
        getTestAppComponent().inject(this);
    }

    @After
    public void tearDown() {
//        String parentPath = MainActivity.getInstance().getFileStreamPath("well_data_android_test").getPath();
//        String fileName = "well_data_android_test";
//
//        if (wellDataFacade.doesWellDataFileExist(parentPath, fileName)) {
//            wellDataFacade.cleanUpWellData();
//        }

        wellDataFacade.cleanUpWellData();
    }

    @Test
    public void wellDataListIsEmptyTest() {
        wellDataListVM = new WellDataListVM(wellDataFacade, mainActivityProviderManager);

        sleep(2);

        Assert.assertTrue(wellDataListVM.isListEmpty.get());
        Assert.assertEquals(0, wellDataListVM.recylcerViewAdapter.get().getItemCount());
    }

    @Test
    public void wellDataListIsNotEmptyTest() {
        InputStream inputStream = RawFileUtility.getInputStreamFromResourceId(MainActivity.getInstance().getResources(), R.raw.well_data);

        Assert.assertTrue(wellDataFacade.seedWellDataIntoDatabase(inputStream));

        sleep(10);

        Assert.assertTrue(wellDataFacade.doesWellDataExist());

        wellDataListVM = new WellDataListVM(wellDataFacade, mainActivityProviderManager);

        Assert.assertFalse(wellDataListVM.isListEmpty.get());

        int totalItemCount = wellDataListVM.recylcerViewAdapter.get().getItemCount();

        Assert.assertTrue(totalItemCount > 0);
    }
}
