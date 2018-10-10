package qv21.codingexercise.models.viewmodels;

import android.arch.lifecycle.ViewModel;

import qv21.codingexercise.activities.MainActivity;

public abstract class BaseVM extends ViewModel {

    public void setupToolBar() {
        MainActivity.getInstance().getViewModel().dismissToolbar();
    }
}
