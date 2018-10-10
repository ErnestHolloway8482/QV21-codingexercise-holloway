package qv21.codingexercise.views;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.android.databinding.library.baseAdapters.BR;

import javax.inject.Inject;

import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.models.viewmodels.BaseVM;

/**
 * This base class represents the content for any screen that the user is able to navigate to.
 *
 * @param <VM> is the binding data that the screen will use to display contents and extends {@link ViewModel}
 * @param <DB> is the data binding mechanism provided by the data binding library to link a given data structure to the view. This extends {@link ViewDataBinding}.
 */
public abstract class ScreenImpl<VM extends BaseVM, DB extends ViewDataBinding> extends RelativeLayout implements Screen<VM> {
    @Inject
    protected VM mViewModel;

    protected DB mViewDataBinding;

    /**
     * CTOR
     *
     * @param context
     * @param layoutId
     */
    public ScreenImpl(final Context context, final int layoutId) {
        super(context);
        initView(context, layoutId);
    }

    /**
     * CTOR
     *
     * @param context
     * @param attrs
     * @param layoutId
     */
    public ScreenImpl(final Context context, final AttributeSet attrs, final int layoutId) {
        super(context, attrs);
        initView(context, layoutId);
    }

    /**
     * CTOR
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param layoutId
     */
    public ScreenImpl(final Context context, final AttributeSet attrs, final int defStyleAttr, final int layoutId) {
        super(context, attrs, defStyleAttr);
        initView(context, layoutId);
    }

    /**
     * Refreshes the view model and auto-binds to the updated model contents.
     *
     * @param model is the ViewModel.
     */
    @Override
    public void setViewModel(final VM model) {
        mViewModel = model;
        mViewDataBinding.setVariable(BR.vm, mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    @Override
    public void setupToolbar() {
        mViewModel.setupToolBar();
    }

    /**
     * Inflates a binding layout and returns the newly-created binding for that layout.
     * The screen's view model is also bound to the layout.
     *
     * @param context  the {@link android.app.Activity} context that the view belongs to.
     * @param layoutId The layout resource ID of the layout to inflate.
     * @return the outermost View in the layout file associated with the Binding.
     */
    protected void initView(final Context context, final int layoutId) {
        LayoutInflater inflater = MainActivity.getInstance().getLayoutInflater();
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId, this, true);
        mViewDataBinding.setVariable(BR.vm, mViewModel);
        mViewDataBinding.executePendingBindings();
    }
}
