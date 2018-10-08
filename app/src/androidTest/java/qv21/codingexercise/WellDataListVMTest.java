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
import qv21.codingexercise.models.viewmodels.WellDataListVM;

@RunWith(AndroidJUnit4.class)
public class WellDataListVMTest extends BaseAndroidUnitTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Inject
    WellDataFacade wellDataFacade;

    private WellDataListVM wellDataListVM;

    @Before
    public void setup() {
        getTestAppComponent().inject(this);

        wellDataListVM = new WellDataListVM(wellDataFacade);
    }

    @After
    public void tearDown() {
        wellDataFacade.cleanUpWellData();
    }

    @Test
    public void wellDataListIsEmptyTest() {
        sleep(2);

        Assert.assertTrue(wellDataListVM.isListEmpty.get());
        Assert.assertEquals(0, wellDataListVM.adapter.get().getItemCount());
    }

    @Test
    public void wellDataListIsNotEmptyTest() {
        wellDataFacade.seedWellDataIntoDatabase(TEST_DATABASE_FILENAME);

        do{

        } while(!wellDataFacade.doesWellDataExist());

        Assert.assertFalse(wellDataListVM.isListEmpty.get());
        Assert.assertTrue(wellDataListVM.adapter.get().getItemCount() > 0);
    }
}
