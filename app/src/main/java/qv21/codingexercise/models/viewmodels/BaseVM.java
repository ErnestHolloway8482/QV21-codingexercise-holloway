package qv21.codingexercise.models.viewmodels;

import android.arch.lifecycle.ViewModel;

import qv21.codingexercise.activities.MainActivity;

public abstract class BaseVM extends ViewModel {
    /**
     * Configures the view model data for displaying the {@link android.support.v7.widget.Toolbar} as defined in {@link MainActivityVM}
     */
    public void setupToolBar() {
        MainActivity.getInstance().getViewModel().dismissToolbar();
    }
}
