package qv21.codingexercise.views;

/**
 * This is the main interface that needs to be implemented for defining a main container that will display any of the {@link Screen}
 * that the application can navigate to. This makes it simple to swap out screens and works with the {@link qv21.codingexercise.managers.NavigationManager}
 * to make app screen navigation simpler.
 */
public interface ViewContainer {
    void replaceView(final Screen view);

    void removeAllViewsFromContainer();
}
