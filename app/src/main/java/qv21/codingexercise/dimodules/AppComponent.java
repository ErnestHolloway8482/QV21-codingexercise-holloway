package qv21.codingexercise.dimodules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.adapters.WellDataListRecyclerAdapter;
import qv21.codingexercise.application.QV21Application;
import qv21.codingexercise.models.viewmodels.BaseVM;
import qv21.codingexercise.views.SplashScreen;
import qv21.codingexercise.views.WellDataDetailsScreen;
import qv21.codingexercise.views.WellDataEditScreen;
import qv21.codingexercise.views.WellDataListScreen;

/**
 * Main dependency graph that will utilize factory methods to provide objects from the registered {@link dagger.Module} classes from Dagger that contain the provider methods annonated with {@link dagger.Provides}
 * <p>
 * -The inject methods are denoted for any class where objects need to be placed into them via field injecction denoted with the {@link javax.inject.Inject} annotation.
 * <p>
 * -The {@link dagger.Component.Builder} class is utilized to allow different locations of the app to gain access to the dependency graph to allow for use of injected objects.
 * <p>
 * -Note that the majority of the object injection is handled via constructor injection vs field injection. This is cleaner than field injection and also allows for the associated classes to be
 * easily tested for unit and integration tests.
 */
@Singleton
@Component(modules = {
        ManagerModule.class,
        DAOModule.class,
        MapperModule.class,
        FacadeModule.class,
        ViewModelModule.class,
        ContextModule.class
})
public interface AppComponent {
    void inject(QV21Application qv21Application);

    void inject(MainActivity mainActivity);

    void inject(SplashScreen splashScreen);

    void inject(WellDataListScreen wellDataListSCreen);

    void inject(WellDataDetailsScreen wellDataDetailsScreen);

    void inject(WellDataEditScreen wellDataEditScreen);

    void inject(WellDataListRecyclerAdapter wellDataListRecyclerAdapter);

    void inject(BaseVM baseVM);

    @Component.Builder
    public interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
