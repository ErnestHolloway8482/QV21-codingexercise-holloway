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

    @Override
    public void onCreate() {
        super.onCreate();

        //Sets up the Dagger dependency injection graph for the entire application.
        appComponent = DaggerAppComponent.builder().application(this).build();
    }

    /**
     * @return a singleton reference of the Dagger dependency graph.
     */
    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
