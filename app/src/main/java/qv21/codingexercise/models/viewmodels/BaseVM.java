package qv21.codingexercise.models.viewmodels;

import android.arch.lifecycle.ViewModel;

import qv21.codingexercise.activities.MainActivity;

public class BaseVM extends ViewModel {
    protected void displayProgressDialog() {
        MainActivity.getInstance().runOnUiThread(() -> {
            MainActivity.getInstance().displayProgressDialog(true, null);
        });
    }

    protected void displayProgressDialog(final String message) {
        MainActivity.getInstance().runOnUiThread(() -> {
            MainActivity.getInstance().displayProgressDialog(true, message);
        });
    }

    protected void displayProgressDialog(final int resourceId) {
        MainActivity.getInstance().runOnUiThread(() -> {
            MainActivity.getInstance().displayProgressDialog(true, resourceId);
        });
    }

    protected void dismissProgressDialog() {
        MainActivity.getInstance().runOnUiThread(() -> {
            MainActivity.getInstance().displayProgressDialog(false, null);
        });
    }
}
