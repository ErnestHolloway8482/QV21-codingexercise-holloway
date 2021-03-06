package qv21.codingexercise.activities;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;

import com.android.databinding.library.baseAdapters.BR;

import javax.inject.Inject;
import javax.inject.Singleton;

import qv21.codingexercise.R;
import qv21.codingexercise.application.QV21Application;
import qv21.codingexercise.managers.MainActivityProviderManager;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.managers.ResourceManager;
import qv21.codingexercise.models.viewmodels.MainActivityVM;
import qv21.codingexercise.views.SplashScreen;

/**
 * This is {@link AppCompatActivity} that is responsible for setting up the initial screen, and initializing the {@link NavigationManager}
 * such that moving between different screens of the application is possible.
 */
@Singleton
public class MainActivity extends AppCompatActivity {
    @Inject
    NavigationManager navigationManager;

    @Inject
    MainActivityProviderManager mainActivityProviderManager;

    @Inject
    ResourceManager resourceManager;

    private static MainActivity instance;

    private boolean isRunning;

    private MainActivityVM viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;

        setup();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanup();
    }

    @Override
    protected void onResume() {
        super.onResume();

        isRunning = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        isRunning = false;
    }

    @Override
    public void onBackPressed() {
        //Pop off the view stack until nothing is left before allowing the user to exit the app since we are running the app as a single activity multiple screen setup.
        if (navigationManager.isOnLastScreen()) {
            navigationManager.onBackPressed();
            finish();
        } else {
            navigationManager.onBackPressed();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    /**
     * @return instance of the {@link MainActivityVM}. This allows any of the corresponding {@link android.arch.lifecycle.ViewModel} classes
     * to access and configure global screen elements such as the {@link android.widget.Toolbar}, loading spinner, and {@link android.support.v7.app.AppCompatDialog}.
     */
    public MainActivityVM getViewModel() {
        return viewModel;
    }

    /**
     * @return a singleton reference of the {@link AppCompatActivity}. This is useful for any Android based classes that needs a simple way to get an Activity's context.
     */
    public static MainActivity getInstance() {
        return instance;
    }

    /**
     * Initializes the main search article screen that is the first screen of the app. It also makes sure to provide the view container for the {@link NavigationManager}
     */
    private void setupMainScreen() {
        navigationManager.setViewContainer(findViewById(R.id.viewContainer));

        SplashScreen splashScreen = new SplashScreen(this);
        navigationManager.push(splashScreen);
        navigationManager.showScreen();
    }


    /**
     * Finalizes setup by adding this class to dependency graph and setup for image caching/databinding/screen transition animations/database.
     */
    private void setup() {
        QV21Application.getAppComponent().inject(this);

        viewModel = new MainActivityVM(mainActivityProviderManager, resourceManager);
        ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        binding.setVariable(BR.vm, viewModel);

        AutoTransition autoTransition = new AutoTransition();

        TransitionManager.beginDelayedTransition(findViewById(R.id.viewContainer), autoTransition);

        setupMainScreen();
    }

    private void cleanup() {
        if (instance != null) {
            instance = null;
        }
    }
}
