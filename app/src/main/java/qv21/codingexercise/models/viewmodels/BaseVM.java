package qv21.codingexercise.models.viewmodels;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import qv21.codingexercise.managers.MainActivityProviderManager;

/**
 * A base {@link ViewModel} that all view models defined should extend.
 */
public abstract class BaseVM extends ViewModel {
    @Inject
    MainActivityProviderManager mainActivityProviderManager;
    
    /**
     * Configures the view model data for displaying the {@link android.support.v7.widget.Toolbar} as defined in {@link MainActivityVM}
     */
    public void setupToolBar() {
        mainActivityProviderManager.provideMainActivity().getViewModel().dismissToolbar();
    }
}
