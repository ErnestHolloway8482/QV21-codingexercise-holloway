package qv21.codingexercise.managers;

import android.content.res.Resources;

import javax.inject.Singleton;

import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.application.QV21Application;

/**
 * A {@link Singleton} class that is used to provide instances of the {@link android.app.Activity} and {@link Resources} from this class.
 * In addition to this class provides a convenience method for forcing runnable where needed to run on the UI thread.
 */
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
