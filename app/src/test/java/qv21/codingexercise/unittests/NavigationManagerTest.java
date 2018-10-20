package qv21.codingexercise.unittests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import javax.inject.Inject;

import qv21.codingexercise.BaseUnitTest;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.views.Screen;
import qv21.codingexercise.views.ViewContainer;

@RunWith(JUnit4.class)
public class NavigationManagerTest extends BaseUnitTest {
    @Inject
    NavigationManager navigationManager;

    private ViewContainer viewContainer;

    @Before
    public void setup() {
        super.setup();

        getTestAppComponent().inject(this);

        viewContainer = getViewContainer();


        navigationManager.setViewContainer(viewContainer);
    }

    @After
    public void tearDown() {
        super.tearDown();

        navigationManager.clearAllViewsFromStack();
    }

    @Test
    public void initializationTest() {
        Assert.assertTrue(navigationManager.peek() == null);
    }

    @Test
    public void pushScreenTest() {
        Assert.assertNotNull(navigationManager.push(getScreen()));
        Assert.assertTrue(navigationManager.isOnLastScreen());
        Assert.assertNotNull(navigationManager.peek());
    }

    @Test
    public void popScreenTest() {
        Assert.assertNotNull(navigationManager.push(getScreen()));
        Assert.assertTrue(navigationManager.isOnLastScreen());

        //Do pop when 1 screen is on view stack
        navigationManager.pop();
        Assert.assertFalse(navigationManager.isOnLastScreen());
        Assert.assertNull(navigationManager.peek());

        //Confirm pop doesn't break things when the view stack is empty.
        navigationManager.pop();
        Assert.assertFalse(navigationManager.isOnLastScreen());
        Assert.assertNull(navigationManager.peek());
    }

    @Test
    public void peekScreenTest() {
        //Push one screen onto stack and confirm that peek works normally,
        Assert.assertNotNull(navigationManager.push(getScreen()));
        Assert.assertTrue(navigationManager.isOnLastScreen());
        Assert.assertNotNull(navigationManager.peek());

        //Confirm peek doesn't break things when view stack is empty
        navigationManager.pop();
        Assert.assertNull(navigationManager.peek());
    }

    @Test
    public void clearAllViewsFromStackTest() {
        //Normal Flow where View Container is set
        Assert.assertNotNull(navigationManager.push(getScreen()));
        Assert.assertTrue(navigationManager.isOnLastScreen());

        navigationManager.clearAllViewsFromStack();

        Assert.assertTrue(navigationManager.peek() == null);
        Assert.assertFalse(navigationManager.isOnLastScreen());

        Mockito.verify(viewContainer, Mockito.atMost(1)).removeAllViewsFromContainer();
    }

    @Test
    public void clearAllViewsFromStackWhenViewContainerIsNullTest() {
        //Error flow where view container is null
        navigationManager.setViewContainer(null);
        Assert.assertNotNull(navigationManager.push(getScreen()));
        Assert.assertTrue(navigationManager.isOnLastScreen());

        navigationManager.clearAllViewsFromStack();

        Assert.assertTrue(navigationManager.peek() == null);
        Assert.assertFalse(navigationManager.isOnLastScreen());

        Mockito.verify(viewContainer, Mockito.never()).removeAllViewsFromContainer();
    }

    @Test
    public void showScreenTest() {
        //Normal Flow where View Container is set and view stack is not empty.
        Screen screen = getScreen();
        Assert.assertNotNull(navigationManager.push(screen));
        Assert.assertTrue(navigationManager.isOnLastScreen());
        navigationManager.showScreen();
        Mockito.verify(screen, Mockito.atMost(1)).setupToolbar();
        Mockito.verify(viewContainer, Mockito.atMost(1)).replaceView(screen);


    }

    @Test
    public void showScreenViewContainerNullTest() {
        Screen screen = getScreen();

        //ViewContainer is null and ViewStack is not empty
        navigationManager.setViewContainer(null);
        navigationManager.showScreen();
        Mockito.verify(screen, Mockito.never()).setupToolbar();
        Mockito.verify(viewContainer, Mockito.never()).replaceView(screen);
    }

    @Test
    public void showScreenViewStackEmptyTest() {
        Screen screen = getScreen();

        //ViewContainer is not null and ViewStack is empty
        navigationManager.setViewContainer(viewContainer);
        navigationManager.pop();
        navigationManager.showScreen();
        Mockito.verify(screen, Mockito.never()).setupToolbar();
        Mockito.verify(viewContainer, Mockito.never()).replaceView(screen);
    }

    @Test
    public void showScreenViewContainerNullViewStackEmptyTest() {
        Screen screen = getScreen();

        //ViewContainer is null and ViewStack is empty
        navigationManager.setViewContainer(null);
        navigationManager.pop();
        navigationManager.showScreen();
        Mockito.verify(screen, Mockito.never()).setupToolbar();
        Mockito.verify(viewContainer, Mockito.never()).replaceView(screen);
    }

    @Test
    public void isOnLastScreenTest() {
        Assert.assertNotNull(navigationManager.push(getScreen()));
        Assert.assertTrue(navigationManager.isOnLastScreen());
    }

    @Test
    public void onBackPressedTest() {
        //1 screen on stack
        Assert.assertNotNull(navigationManager.push(getScreen()));
        navigationManager.onBackPressed();
        Assert.assertFalse(navigationManager.isOnLastScreen());
        Assert.assertNull(navigationManager.peek());

        //2 screens on stack
        Assert.assertNotNull(navigationManager.push(getScreen()));
        Assert.assertNotNull(navigationManager.push(getScreen()));
        navigationManager.onBackPressed();
        Assert.assertTrue(navigationManager.isOnLastScreen());
        Assert.assertNotNull(navigationManager.peek());

        Screen screen = navigationManager.peek();
        Mockito.verify(screen, Mockito.atMost(1)).setupToolbar();
        Mockito.verify(viewContainer, Mockito.atMost(1)).replaceView(navigationManager.peek());
    }

    private Screen getScreen() {
        return Mockito.mock(Screen.class);
    }

    private ViewContainer getViewContainer() {
        return Mockito.mock(ViewContainer.class);
    }
}
