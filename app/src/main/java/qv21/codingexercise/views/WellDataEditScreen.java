package qv21.codingexercise.views;

import android.content.Context;
import android.util.AttributeSet;

import qv21.codingexercise.R;
import qv21.codingexercise.application.QV21Application;
import qv21.codingexercise.databinding.WellDataEditScreenBinding;
import qv21.codingexercise.models.viewmodels.WellDataEditVM;

/**
 * {@link Screen} object contents for the well data edit screen that inflates the provided layout resources and binds the associated {@link android.arch.lifecycle.ViewModel}
 */
public class WellDataEditScreen extends ScreenImpl<WellDataEditVM, WellDataEditScreenBinding> {
    public WellDataEditScreen(final Context context) {
        super(context, R.layout.well_data_edit_screen);

        QV21Application.getAppComponent().inject(this);

        setViewModel(mViewModel);
    }

    public WellDataEditScreen(final Context context, final AttributeSet attrs) {
        super(context, attrs, R.layout.well_data_edit_screen);

        QV21Application.getAppComponent().inject(this);

        setViewModel(mViewModel);
    }

    public WellDataEditScreen(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr, R.layout.well_data_edit_screen);

        QV21Application.getAppComponent().inject(this);

        setViewModel(mViewModel);
    }
}
