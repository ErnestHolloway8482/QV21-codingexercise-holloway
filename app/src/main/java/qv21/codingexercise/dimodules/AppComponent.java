package qv21.codingexercise.dimodules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.application.QV21Application;
import qv21.codingexercise.views.SplashScreen;
import qv21.codingexercise.views.WellDataDetailsScreen;
import qv21.codingexercise.views.WellDataListScreen;

@Singleton
@Component(modules = {
        ManagerModule.class,
        DAOModule.class,
        MapperModule.class,
        FacadeModule.class,
        ViewModelModule.class
})
public interface AppComponent {
    void inject(QV21Application qv21Application);

    void inject(MainActivity mainActivity);

    void inject(SplashScreen splashScreen);
    void inject(WellDataListScreen wellDataListSCreen);
    void inject(WellDataDetailsScreen wellDataDetailsScreen);

    @Component.Builder
    public interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
