package qv21.codingexercise.models.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import qv21.codingexercise.activities.MainActivity;

public class MainActivityVM extends ViewModel {
    public final ObservableField<String> progressDialogMessage = new ObservableField<>();
    public final ObservableBoolean isProgressDialogVisible = new ObservableBoolean();

    public final ObservableBoolean isToolBarVisible = new ObservableBoolean();
    public final ObservableField<String> toolBarTitle = new ObservableField<>();
    public final ObservableBoolean isToolBarBackButtonVisible = new ObservableBoolean();

    public MainActivityVM() {
        isToolBarVisible.set(false);
        isToolBarBackButtonVisible.set(false);
        toolBarTitle.set(null);
    }

    public void onNavigationBackButtonPressed() {
        MainActivity.getInstance().onBackPressed();
    }

    public void displayProgressDialog() {
        progressDialogMessage.set("");
        isProgressDialogVisible.set(true);

//        MainActivity.getInstance().runOnUiThread(() -> {
//            MainActivity.getInstance().isProgressDialogVisible(true, null);
//        });
    }

    public void displayProgressDialog(final String message) {
        progressDialogMessage.set(message);
        isProgressDialogVisible.set(true);

//        MainActivity.getInstance().runOnUiThread(() -> {
//            MainActivity.getInstance().isProgressDialogVisible(true, message);
//        });
    }

    public void displayProgressDialog(final int resourceId) {
        progressDialogMessage.set(MainActivity.getInstance().getString(resourceId));
        isProgressDialogVisible.set(true);

//        MainActivity.getInstance().runOnUiThread(() -> {
//            MainActivity.getInstance().isProgressDialogVisible(true, resourceId);
//        });
    }

    public void dismissProgressDialog() {
        progressDialogMessage.set("");
        isProgressDialogVisible.set(false);

//        MainActivity.getInstance().runOnUiThread(() -> {
//            MainActivity.getInstance().isProgressDialogVisible(false, null);
//        });
    }

    public void displayToolBar(final boolean displayBackButton, final String screenTitle) {
        isToolBarVisible.set(true);
        isToolBarBackButtonVisible.set(displayBackButton);
        toolBarTitle.set(screenTitle);
    }

    public void dismissToolbar() {
        isToolBarVisible.set(false);
        isToolBarBackButtonVisible.set(false);
    }
}
