package qv21.codingexercise.models.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import qv21.codingexercise.activities.MainActivity;

public class BaseVM extends ViewModel {
    public ObservableField<String> progressDialogMessage = new ObservableField<>();
    public ObservableBoolean displayProgressDialog = new ObservableBoolean();

    public ObservableBoolean isToolBarDisplayed = new ObservableBoolean();
    public ObservableField<String> toolBarTitle = new ObservableField<>();
    public ObservableBoolean isToolBarBackButtonDisplayed = new ObservableBoolean();
    public ObservableBoolean isToolBarSaveButtonDisplayed = new ObservableBoolean();
    public ObservableBoolean isToolBarDeleteButtonDisplayed = new ObservableBoolean();

    protected void displayProgressDialog() {
        progressDialogMessage.set("");
        displayProgressDialog.set(true);

        MainActivity.getInstance().runOnUiThread(() -> {
            MainActivity.getInstance().displayProgressDialog(true, null);
        });
    }

    protected void displayProgressDialog(final String message) {
        progressDialogMessage.set(message);
        displayProgressDialog.set(true);

        MainActivity.getInstance().runOnUiThread(() -> {
            MainActivity.getInstance().displayProgressDialog(true, message);
        });
    }

    protected void displayProgressDialog(final int resourceId) {
        progressDialogMessage.set(MainActivity.getInstance().getString(resourceId));
        displayProgressDialog.set(true);

        MainActivity.getInstance().runOnUiThread(() -> {
            MainActivity.getInstance().displayProgressDialog(true, resourceId);
        });
    }

    protected void dismissProgressDialog() {
        progressDialogMessage.set("");
        displayProgressDialog.set(false);

        MainActivity.getInstance().runOnUiThread(() -> {
            MainActivity.getInstance().displayProgressDialog(false, null);
        });
    }
}
