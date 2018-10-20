package qv21.codingexercise.managers;

import android.content.res.Resources;

import javax.inject.Singleton;

import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.application.QV21Application;

@Singleton
public class MainActivityProviderManagerImpl implements MainActivityProviderManager {
    @Override
    public MainActivity provideMainActivity() {
        return MainActivity.getInstance();
    }

    @Override
    public void runOnUiThread(final Runnable runnable) {
        if (runnable == null) {
            return;
        }

        MainActivity.getInstance().runOnUiThread(runnable);
    }

    @Override
    public Resources getResources() {
        return QV21Application.getInstance().getResources();
    }
}
