package qv21.codingexercise.views;

import android.content.Context;
import android.util.AttributeSet;

import qv21.codingexercise.R;
import qv21.codingexercise.databinding.WellDataEditScreenBinding;
import qv21.codingexercise.models.viewmodels.WellDataEditVM;

public class WellDataEditScreen extends ScreenImpl<WellDataEditVM, WellDataEditScreenBinding> {
    public WellDataEditScreen(final Context context) {
        super(context, R.layout.well_data_edit_screen);
    }

    public WellDataEditScreen(final Context context, final AttributeSet attrs) {
        super(context, attrs, R.layout.well_data_edit_screen);
    }

    public WellDataEditScreen(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr, R.layout.well_data_edit_screen);
    }
}
