package qv21.codingexercise.application;

import android.app.Application;

import qv21.codingexercise.dimodules.AppComponent;
import qv21.codingexercise.dimodules.DaggerAppComponent;

/**
 * Main application entry point. The primary goal of this class is to setup up the {@link DaggerAppComponent}
 * so that dependency injection can be utilized.
 */
public class QV21Application extends Application {
    private static AppComponent appComponent;
    private static QV21Application instance;

    @Override
    public void onCreate() {
        super.onCreate();

        //Sets up the Dagger dependency injection graph for the entire application.
        appComponent = DaggerAppComponent.builder().application(this).build();

        instance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        cleanUp();
    }

    /**
     * @return a singleton reference of the Dagger dependency graph.
     */
    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static QV21Application getInstance() {
        return instance;
    }

    private void cleanUp() {

        instance = null;
    }
}
