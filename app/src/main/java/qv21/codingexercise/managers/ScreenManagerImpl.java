package qv21.codingexercise.managers;

import javax.inject.Singleton;

import qv21.codingexercise.views.Screen;
import qv21.codingexercise.views.SplashScreen;
import qv21.codingexercise.views.WellDataDetailsScreen;
import qv21.codingexercise.views.WellDataEditScreen;
import qv21.codingexercise.views.WellDataListScreen;

/**
 * A {@link Singleton} class that will provide an instance of a {@link Screen} based on the given class type.
 */
@Singleton
public class ScreenManagerImpl implements ScreenManager {
    final MainActivityProviderManager mainActivityProviderManager;

    public ScreenManagerImpl(final MainActivityProviderManager mainActivityProviderManager) {
        this.mainActivityProviderManager = mainActivityProviderManager;
    }

    @Override
    public <T extends Screen> Screen getScreenFromClass(final Class<T> screenClass) {
        if (screenClass == SplashScreen.class) {
            return new SplashScreen(mainActivityProviderManager.provideMainActivity());
        } else if (screenClass == WellDataListScreen.class) {
            return new WellDataListScreen(mainActivityProviderManager.provideMainActivity());
        } else if (screenClass == WellDataDetailsScreen.class) {
            return new WellDataDetailsScreen(mainActivityProviderManager.provideMainActivity());
        } else if (screenClass == WellDataEditScreen.class) {
            return new WellDataEditScreen(mainActivityProviderManager.provideMainActivity());
        } else {
            return null;
        }
    }
}
