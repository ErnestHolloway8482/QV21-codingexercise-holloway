package qv21.codingexercise;

import qv21.codingexercise.dimodules.AndroidTestAppComponent;
import qv21.codingexercise.dimodules.DaggerAndroidTestAppComponent;
import qv21.codingexercise.utilities.BuildConfigUtility;
import qv21.codingexercise.utilities.LoggerUtils;

public class BaseAndroidUnitTest {
    private AndroidTestAppComponent testAppComponent;

    protected final String TEST_DATABASE_FILENAME = "well_data_android_test";

    public BaseAndroidUnitTest() {
        testAppComponent = DaggerAndroidTestAppComponent.builder().build();
        BuildConfigUtility.setIsInAndroidTestMode(true);
    }

    protected AndroidTestAppComponent getTestAppComponent() {
        return testAppComponent;
    }

    protected void sleep(final int numberOfSeconds) {
        try {
            int timeInSecobds = 1000 * numberOfSeconds;
            Thread.sleep(timeInSecobds);
        } catch (InterruptedException e) {
            LoggerUtils.logError(e.getMessage());
        }
    }
}
