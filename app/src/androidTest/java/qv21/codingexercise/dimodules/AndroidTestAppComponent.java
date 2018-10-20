package qv21.codingexercise.dimodules;

import javax.inject.Singleton;

import dagger.Component;
import qv21.codingexercise.viewmodeltests.MainActivityVMTest;
import qv21.codingexercise.viewmodeltests.SplashVMTest;
import qv21.codingexercise.viewmodeltests.WellDataDetailsVMTest;
import qv21.codingexercise.viewmodeltests.WellDataEditVMTest;
import qv21.codingexercise.viewmodeltests.WellDataListItemVMTest;
import qv21.codingexercise.viewmodeltests.WellDataListVMTest;
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
    void inject(MainActivityVMTest mainActivityVMTest);
    
    void inject(WellDataListItemVMTest wellDataListItemVMTest);

    void inject(WellDataListVMTest wellDataListVMTest);

    void inject(SplashVMTest splashVMTest);

    void inject(WellDataDetailsVMTest wellDataDetailsVMTest);

    void inject(WellDataEditVMTest wellDataEditVMTest);

    void inject(BaseVM baseVM);
}
