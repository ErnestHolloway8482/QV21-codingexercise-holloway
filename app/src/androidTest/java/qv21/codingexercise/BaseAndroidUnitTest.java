package qv21.codingexercise;

import qv21.codingexercise.utilities.LoggerUtils;

public class BaseAndroidUnitTest {
    private TestAppComponent testAppComponent;

    protected final String TEST_DATABASE_FILENAME = "well_data_android_test";

    public BaseAndroidUnitTest(){
        testAppComponent = DaggerTestAppComponent.builder().build();
    }

    protected TestAppComponent getTestAppComponent(){
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
