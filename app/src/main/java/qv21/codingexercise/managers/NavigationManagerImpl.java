package qv21.codingexercise.managers;

import android.support.annotation.NonNull;

import java.util.Stack;

import javax.inject.Inject;
import javax.inject.Singleton;

import qv21.codingexercise.views.Screen;
import qv21.codingexercise.views.ViewContainer;

/**
 * This is a {@link Singleton} manager that allows for navigating between screens within the app. In order to reduce the dependency
 * on managing multiple life cycle events across {@link android.support.v7.app.AppCompatActivity} or {@link android.support.v4.app.Fragment} classes,
 * this project instead utilizes a simple stack of views that are swapped within a view container. This is more or less in line with current Android architecture decisions that
 * are beginning to move away from Activity and Fragment based navigation due to the issues that arise with having to manage way too many lifecylce and lack of flexibility that the
 * FragmentManager gives you when it comes to managing your back stack.
 */
@Singleton
public class NavigationManagerImpl implements NavigationManager {
    private final Stack<Screen> viewStack;
    private ViewContainer viewContainer;

    /**
     * CTOR
     */
    @Inject
    public NavigationManagerImpl() {
        viewStack = new Stack<>();
    }

    /**
     * Sets the main view container that all other views will be swapped into as navigation requests are made.
     *
     * @param viewContainer is the main view container provided by the {@link qv21.codingexercise.activities.MainActivity}
     */
    @Override
    public void setViewContainer(final ViewContainer viewContainer) {
        this.viewContainer = viewContainer;
    }

    /**
     * Pushes the next view to be displayed onto the view stack.
     *
     * @param view is {@link Screen} next up to be displayed.
     * @return returns the {@link Screen} that was just pushed onto the view stack.
     */
    @Override
    public Screen push(@NonNull final Screen view) {
        return viewStack.push(view);
    }

    /**
     * Pops(Removes) the current view that is being displayed off of the top of the view stack. If there are no more items to pop, then no operation will be performed.
     */
    @Override
    public void pop() {
        if (viewStack.size() < 1) {
            return;
        }

        viewStack.pop();
    }

    /**
     * @return the {@link Screen} that is at the top of the view stack.
     */
    @Override
    public Screen peek() {
        try {
            return viewStack.peek();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Removes all {@link Screen} from the view stack.
     */
    @Override
    public void clearAllViewsFromStack() {
        viewStack.clear();

        if (viewContainer != null) {
            viewContainer.removeAllViewsFromContainer();
        }
    }

    /**
     * This method will show the screen that is currently at the top of the view stack. The reason why this method exists, is to allow the developer
     * to setup the navigational view stack the way they want first before displaying anything to the user granting them more control in the event that
     * exceptional navigational cases need to be covered. This avoids the scenario as well as having a screen be automatically displayed when a screen is pushed/poped to/from the stack.
     */
    @Override
    public void showScreen() {
        if (viewContainer == null || viewStack.isEmpty()) {
            return;
        }

        //Gives us the opportunity to organize the view stack first before displaying anything.
        viewStack.peek().setupToolbar();

        //Whenever we show the current screen, we always show the screen at the top of the stack.
        viewContainer.replaceView(viewStack.peek());
    }

    /**
     * @return true if the user is on the last screen, false otherwise. This can be utilized to allow the app to exit when the user taps back on the last screen.
     */
    @Override
    public boolean isOnLastScreen() {
        return viewStack.size() == 1;
    }

    /**
     * Pop off the view stack until nothing is left before allowing the user to exit the app since we are running the app as a single activity multiple screen setup.
     */
    public void onBackPressed() {
        if (isOnLastScreen()) {
            pop();
        } else {
            pop();
            showScreen();
        }
    }
}
