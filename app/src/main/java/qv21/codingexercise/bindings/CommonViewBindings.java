package qv21.codingexercise.bindings;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Defines a custom <strong>app:command</strong> attribute and binds the appropriate View event handlers to it.
 * This attribute can be used to bindViewModel a View's event (i.e. click) to an arbitrary method.
 * <p>
 * Example:
 * <code><Button app:command="@{viewModel.exampleCommand}"/></code>
 * <p>
 */
public class CommonViewBindings {
    /**
     * Binds <c>view</c>'s {@link View#setOnClickListener(View.OnClickListener)} to <strong>command</strong>.
     *
     * @param view     The {@link View} that'll invoke the command.
     * @param runnable The {@link Runnable} action to be executed.
     */
    @BindingAdapter("app:command")
    public static void bindOnClick(final View view, final Runnable runnable) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != runnable) {
                    runnable.run();
                }
            }
        });
    }

    /**
     * Binds <c>view</c>'s {@link View#setOnClickListener(View.OnClickListener)} to <strong>command</strong>.
     *
     * @param view     The {@link android.support.v7.widget.Toolbar} that'll invoke the command.
     * @param runnable The {@link Runnable} action to be executed.
     */
    @BindingAdapter("app:navigationOnClick")
    public static void bindOnToolbarNavigationClick(final android.support.v7.widget.Toolbar view, final Runnable runnable) {
        view.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (null != runnable) {
                    runnable.run();
                }
            }
        });
    }

    /**
     * Binds <c>{@link RecyclerView}</c>'s to <strong>command</strong>.
     * This allows the user to specify a layout manager for a {@link RecyclerView}
     *
     * @param view          The {@link RecyclerView} that'll invoke the command.
     * @param layoutManager The layout manager that the {@link RecyclerView} will use.
     */
    @BindingAdapter("app:layoutManager")
    public static void bindLayoutManager(final RecyclerView view, final RecyclerView.LayoutManager layoutManager) {
        view.setLayoutManager(layoutManager);
    }
}
