package qv21.codingexercise.models.viewmodels;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import qv21.codingexercise.activities.MainActivity;

public class MainActivityVM extends BaseVM {
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
    }

    public void displayProgressDialog(final String message) {
        progressDialogMessage.set(message);
        isProgressDialogVisible.set(true);
    }

    public void displayProgressDialog(final int resourceId) {
        progressDialogMessage.set(MainActivity.getInstance().getString(resourceId));
        isProgressDialogVisible.set(true);
    }

    public void dismissProgressDialog() {
        progressDialogMessage.set("");
        isProgressDialogVisible.set(false);
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
