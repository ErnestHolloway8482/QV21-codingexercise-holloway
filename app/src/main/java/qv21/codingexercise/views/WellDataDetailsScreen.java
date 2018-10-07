package qv21.codingexercise.views;

import android.content.Context;
import android.util.AttributeSet;

import qv21.codingexercise.R;
import qv21.codingexercise.databinding.WellDataDetailsBinding;
import qv21.codingexercise.models.viewmodels.WellDataItemVM;

public class WellDataDetailsScreen extends ScreenImpl<WellDataItemVM, WellDataDetailsBinding> {
    public WellDataDetailsScreen(final Context context) {
        super(context, R.layout.well_data_details);
    }

    public WellDataDetailsScreen(final Context context, final AttributeSet attrs) {
        super(context, attrs, R.layout.well_data_details);
    }

    public WellDataDetailsScreen(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr, R.layout.well_data_details);
    }
}
