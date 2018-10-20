package qv21.codingexercise.dimodules;

import javax.inject.Singleton;

import dagger.Component;
import qv21.codingexercise.BaseUnitTest;
import qv21.codingexercise.models.viewmodels.BaseVM;
import qv21.codingexercise.unittests.NavigationManagerTest;
import qv21.codingexercise.unittests.ScreenManagerTest;

@Singleton
@Component(modules = {
        TestManagerModule.class,
        DAOModule.class,
        MapperModule.class,
        FacadeModule.class,
        ViewModelModule.class,
        TestContextModule.class
})
public interface TestAppComponent {
    void inject(BaseVM baseVM);

    void inject(ScreenManagerTest screenManagerTest);
    void inject(NavigationManagerTest navigationManagerTest);

    void inject(BaseUnitTest unitTest);
}
