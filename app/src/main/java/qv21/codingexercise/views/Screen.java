package qv21.codingexercise.views;

import android.arch.lifecycle.ViewModel;

public interface Screen<VM extends ViewModel> {
    /**
     * Refreshes the view model and auto-binds to the updated model contents.
     *
     * @param model is the ViewModel.
     */
    void setViewModel(final VM model);

    void setupToolbar();
}
