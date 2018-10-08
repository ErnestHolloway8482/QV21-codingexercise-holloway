package qv21.codingexercise.views;

import android.content.Context;
import android.util.AttributeSet;

import qv21.codingexercise.R;
import qv21.codingexercise.application.QV21Application;
import qv21.codingexercise.databinding.WellDataListScreenBinding;
import qv21.codingexercise.models.viewmodels.WellDataListVM;

public class WellDataListScreen extends ScreenImpl<WellDataListVM, WellDataListScreenBinding> {
    public WellDataListScreen(final Context context) {
        super(context, R.layout.well_data_list_screen);

        QV21Application.getAppComponent().inject(this);
    }

    public WellDataListScreen(final Context context, final AttributeSet attrs) {
        super(context, attrs, R.layout.well_data_list_screen);

        QV21Application.getAppComponent().inject(this);
    }

    public WellDataListScreen(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr, R.layout.well_data_list_screen);

        QV21Application.getAppComponent().inject(this);
    }
}
