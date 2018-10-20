package qv21.codingexercise;

import com.betterup.codingexercise.dimodules.DaggerTestAppComponent;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import qv21.codingexercise.dimodules.TestAppComponent;
import qv21.codingexercise.utilities.BuildConfigUtility;

@RunWith(JUnit4.class)
public class BaseUnitTest {
    private TestAppComponent testAppComponent;

    public BaseUnitTest() {
        testAppComponent = DaggerTestAppComponent.builder().build();

        testAppComponent.inject(this);

        BuildConfigUtility.setIsInAndroidTestMode(false);
        BuildConfigUtility.setIsInTestMode(true);
    }

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {

    }

    protected TestAppComponent getTestAppComponent() {
        return testAppComponent;
    }

    protected void sleep(final int numberOfSeconds) {
        try {
            int timeInSeconds = 1000 * numberOfSeconds;
            Thread.sleep(timeInSeconds);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
