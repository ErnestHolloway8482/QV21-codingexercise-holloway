package qv21.codingexercise.unittests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.inject.Inject;

import qv21.codingexercise.BaseUnitTest;
import qv21.codingexercise.managers.ScreenManager;
import qv21.codingexercise.views.SplashScreen;
import qv21.codingexercise.views.ViewContainer;
import qv21.codingexercise.views.WellDataDetailsScreen;
import qv21.codingexercise.views.WellDataEditScreen;
import qv21.codingexercise.views.WellDataListScreen;

@RunWith(JUnit4.class)
public class ScreenManagerTest extends BaseUnitTest {
    @Inject
    ScreenManager screenManager;

    private ViewContainer viewContainer;

    @Before
    public void setup(){
        super.setup();

        getTestAppComponent().inject(this);
    }

    @Test
    public void getScreenFromClassTest(){
        Assert.assertTrue(screenManager.getScreenFromClass(SplashScreen.class) instanceof  SplashScreen);
        Assert.assertTrue(screenManager.getScreenFromClass(WellDataListScreen.class) instanceof  WellDataListScreen);
        Assert.assertTrue(screenManager.getScreenFromClass(WellDataDetailsScreen.class) instanceof  WellDataDetailsScreen);
        Assert.assertTrue(screenManager.getScreenFromClass(WellDataEditScreen.class) instanceof  WellDataEditScreen);
    }
}
