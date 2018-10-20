package qv21.codingexercise.managers;

import android.content.res.Resources;

import qv21.codingexercise.activities.MainActivity;

public interface MainActivityProviderManager {
    MainActivity provideMainActivity();

    void runOnUiThread(final Runnable runnable);

    Resources getResources();
}
