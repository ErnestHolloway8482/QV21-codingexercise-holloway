package qv21.codingexercise;

public class BaseAndroidUnitTest {
    private TestAppComponent testAppComponent;

    public BaseAndroidUnitTest(){
        testAppComponent = DaggerTestAppComponent.builder().build();
    }

    public TestAppComponent getTestAppComponent(){
        return testAppComponent;
    }
}
