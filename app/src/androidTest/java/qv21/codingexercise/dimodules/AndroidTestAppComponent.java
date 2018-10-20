package qv21.codingexercise.dimodules;

import javax.inject.Singleton;

import dagger.Component;
import qv21.codingexercise.integrationtests.SplashVMTest;
import qv21.codingexercise.integrationtests.WellDataDetailsVMTest;
import qv21.codingexercise.integrationtests.WellDataEditVMTest;
import qv21.codingexercise.integrationtests.WellDataListItemVMTest;
import qv21.codingexercise.integrationtests.WellDataListVMTest;
import qv21.codingexercise.models.viewmodels.BaseVM;

@Singleton
@Component(modules = {
        AndroidTestManagerModule.class,
        DAOModule.class,
        MapperModule.class,
        FacadeModule.class,
        ViewModelModule.class,
        AndroidTestContextModule.class
})
public interface AndroidTestAppComponent {
    void inject(WellDataListItemVMTest wellDataListItemVMTest);

    void inject(WellDataListVMTest wellDataListVMTest);

    void inject(SplashVMTest splashVMTest);

    void inject(WellDataDetailsVMTest wellDataDetailsVMTest);

    void inject(WellDataEditVMTest wellDataEditVMTest);

    void inject(BaseVM baseVM);
}
