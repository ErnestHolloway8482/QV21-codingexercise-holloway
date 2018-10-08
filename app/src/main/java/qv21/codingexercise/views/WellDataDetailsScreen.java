package qv21.codingexercise.views;

import android.content.Context;
import android.util.AttributeSet;

import qv21.codingexercise.R;
import qv21.codingexercise.application.QV21Application;
import qv21.codingexercise.databinding.WellDataDetailsScreenBinding;
import qv21.codingexercise.models.viewmodels.WellDataDetailsVM;

public class WellDataDetailsScreen extends ScreenImpl<WellDataDetailsVM, WellDataDetailsScreenBinding> {
    public WellDataDetailsScreen(final Context context) {
        super(context, R.layout.well_data_details_screen);

        QV21Application.getAppComponent().inject(this);
    }

    public WellDataDetailsScreen(final Context context, final AttributeSet attrs) {
        super(context, attrs, R.layout.well_data_details_screen);

        QV21Application.getAppComponent().inject(this);
    }

    public WellDataDetailsScreen(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr, R.layout.well_data_details_screen);

        QV21Application.getAppComponent().inject(this);
    }
}
