package qv21.codingexercise;

import javax.inject.Singleton;

import dagger.Component;
import qv21.codingexercise.dimodules.DAOModule;
import qv21.codingexercise.dimodules.FacadeModule;
import qv21.codingexercise.dimodules.ManagerModule;
import qv21.codingexercise.dimodules.MapperModule;
import qv21.codingexercise.dimodules.ViewModelModule;
import qv21.codingexercise.integrationtests.SplashVMTest;
import qv21.codingexercise.integrationtests.WellDataDetailsVMTest;
import qv21.codingexercise.integrationtests.WellDataEditVMTest;
import qv21.codingexercise.integrationtests.WellDataListItemVMTest;
import qv21.codingexercise.integrationtests.WellDataListVMTest;

@Singleton
@Component(modules = {
        ManagerModule.class,
        DAOModule.class,
        MapperModule.class,
        FacadeModule.class,
        ViewModelModule.class
})
public interface TestAppComponent {
    void inject(WellDataListItemVMTest wellDataListItemVMTest);
    void inject(WellDataListVMTest wellDataListVMTest);
    void inject(SplashVMTest splashVMTest);
    void inject(WellDataDetailsVMTest wellDataDetailsVMTest);
    void inject(WellDataEditVMTest wellDataEditVMTest);
}
