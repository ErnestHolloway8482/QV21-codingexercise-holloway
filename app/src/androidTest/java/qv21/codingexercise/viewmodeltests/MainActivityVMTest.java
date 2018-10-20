package qv21.codingexercise.viewmodeltests;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import javax.inject.Inject;

import qv21.codingexercise.BaseAndroidUnitTest;
import qv21.codingexercise.R;
import qv21.codingexercise.managers.MainActivityProviderManager;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.managers.ResourceManager;
import qv21.codingexercise.models.viewmodels.MainActivityVM;


@RunWith(AndroidJUnit4.class)
public class MainActivityVMTest extends BaseAndroidUnitTest {
    @Inject
    MainActivityProviderManager mainActivityProviderManager;

    @Inject
    ResourceManager resourceManager;

    @Inject
    NavigationManager navigationManager;

    private MainActivityVM mainActivityVM;

    @Before
    public void setup() {
        getTestAppComponent().inject(this);

        mainActivityVM = new MainActivityVM(mainActivityProviderManager, resourceManager);
    }

    @After
    public void tearDown() {
        navigationManager.clearAllViewsFromStack();
    }

    @Test
    public void initialValuesTest() {
        Assert.assertFalse(mainActivityVM.isToolBarVisible.get());
        Assert.assertFalse(mainActivityVM.isToolBarBackButtonVisible.get());
        Assert.assertNull(mainActivityVM.toolBarTitle.get());
    }

    @Test
    public void displayProgressDialogWithStringMessageTest() {
        String message = "message";

        mainActivityVM.displayProgressDialog(message);

        Assert.assertEquals(message, mainActivityVM.progressDialogMessage.get());
        Assert.assertTrue(mainActivityVM.isProgressDialogVisible.get());
    }

    @Test
    public void displayProgressDialogWithNoMessageTest() {
        mainActivityVM.displayProgressDialog();

        Assert.assertEquals("", mainActivityVM.progressDialogMessage.get());
        Assert.assertTrue(mainActivityVM.isProgressDialogVisible.get());
    }

    @Test
    public void displayProgressDialogWithResourceIdTest() {
        Mockito.when(resourceManager.getString(R.string.loading_well_data)).thenReturn("Loading Well Data");
        mainActivityVM.displayProgressDialog(R.string.loading_well_data);

        Assert.assertEquals("Loading Well Data", mainActivityVM.progressDialogMessage.get());
        Assert.assertTrue(mainActivityVM.isProgressDialogVisible.get());
        Mockito.verify(resourceManager, Mockito.atMost(1)).getString(R.string.loading_well_data);
    }

    @Test
    public void dismissProgressDialogTest() {
        mainActivityVM.dismissProgressDialog();

        Assert.assertEquals("", mainActivityVM.progressDialogMessage.get());
        Assert.assertFalse(mainActivityVM.isProgressDialogVisible.get());
    }

    @Test
    public void displayToolBarTest() {
        mainActivityVM.displayToolBar(true, "Screen Title");

        Assert.assertTrue(mainActivityVM.isToolBarVisible.get());
        Assert.assertTrue(mainActivityVM.isToolBarBackButtonVisible.get());
        Assert.assertEquals("Screen Title", mainActivityVM.toolBarTitle.get());
    }

    @Test
    public void dismissToolbarTest() {
        mainActivityVM.dismissToolbar();

        Assert.assertFalse(mainActivityVM.isToolBarVisible.get());
        Assert.assertFalse(mainActivityVM.isToolBarBackButtonVisible.get());
        Assert.assertNull(mainActivityVM.toolBarTitle.get());
    }
}
