package qv21.codingexercise.views;

import android.arch.lifecycle.ViewModel;

public interface Screen<VM extends ViewModel> {
    /**
     * Refreshes the view model and auto-binds to the updated model contents.
     *
     * @param model is the ViewModel.
     */
    void setViewModel(final VM model);

    /**
     * Configures the top level {@link android.support.v7.widget.Toolbar} for a given screen as defined by the {@link qv21.codingexercise.activities.MainActivity}
     */
    void setupToolbar();
}
