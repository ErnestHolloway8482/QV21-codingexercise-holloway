package qv21.codingexercise.views;

import android.content.Context;
import android.util.AttributeSet;

import qv21.codingexercise.R;
import qv21.codingexercise.databinding.WellDataListItemScreenBinding;
import qv21.codingexercise.models.viewmodels.WellDataListItemVM;

public class WellDataListItemScreen extends ScreenImpl<WellDataListItemVM, WellDataListItemScreenBinding> {
    public WellDataListItemScreen(final Context context) {
        super(context, R.layout.well_data_list_item_screen);
    }

    public WellDataListItemScreen(final Context context, final AttributeSet attrs) {
        super(context, attrs, R.layout.well_data_list_item_screen);
    }

    public WellDataListItemScreen(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr, R.layout.well_data_list_item_screen);
    }
}
