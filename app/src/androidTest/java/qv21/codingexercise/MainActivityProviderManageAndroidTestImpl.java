package qv21.codingexercise;

import android.content.res.Resources;

import org.mockito.Mockito;

import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.application.QV21Application;
import qv21.codingexercise.managers.MainActivityProviderManager;
import qv21.codingexercise.models.viewmodels.MainActivityVM;

public class MainActivityProviderManageAndroidTestImpl implements MainActivityProviderManager {
    @Override
    public MainActivity provideMainActivity() {
        MainActivityVM mainActivityVM = Mockito.mock(MainActivityVM.class);

        MainActivity mainActivity = Mockito.mock(MainActivity.class);
        Mockito.when(mainActivity.getViewModel()).thenReturn(mainActivityVM);

        return mainActivity;
    }

    @Override
    public void runOnUiThread(final Runnable runnable) {
        if (runnable == null) {
            return;
        }

        runnable.run();
    }

    @Override
    public Resources getResources() {
        return QV21Application.getInstance().getResources();
    }
}
