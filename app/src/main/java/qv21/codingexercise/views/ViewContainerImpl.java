package qv21.codingexercise.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * This is the view container class in which all of the screens will be displayed
 */
public class ViewContainerImpl extends RelativeLayout implements ViewContainer {

    /**
     * CTOR
     * @param context
     */
    public ViewContainerImpl(final Context context) {
        super(context);
    }

    /**
     * CTOR
     * @param context
     * @param attrs
     */
    public ViewContainerImpl(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * CTOR
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public ViewContainerImpl(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * CTOR
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    public ViewContainerImpl(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void replaceView(final Screen view) {
        super.removeAllViews();
        addView((ScreenImpl)view);
    }

    @Override
    public void removeAllViewsFromContainer() {
        super.removeAllViews();
    }
}
