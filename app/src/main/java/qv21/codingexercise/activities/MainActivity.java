package qv21.codingexercise.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.transition.Fade;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;
import javax.inject.Singleton;

import qv21.codingexercise.R;
import qv21.codingexercise.managers.DatabaseManager;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.views.ViewContainer;

@Singleton
public class MainActivity extends AppCompatActivity {
    @Inject
    NavigationManager navigationManager;

    @Inject
    DatabaseManager databaseManager;

    private static MainActivity instance;

    private boolean isRunning;

    private CardView cardView;
    private ProgressBar progressBar;
    private TextView progressBarMessage;


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
            navigationManager.pop();
            super.onBackPressed();
            finish();
        } else {
            navigationManager.pop();
            navigationManager.showScreen();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void displayProgressDialog(final boolean display, final int stringResourceId){
        displayProgressDialog(true, getString(stringResourceId));
    }

    public void displayProgressDialog(final boolean display, final String message) {
        if (display) {
            progressBarMessage.setText(message);
            cardView.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            progressBarMessage.setText("");
            cardView.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
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
        navigationManager.setViewContainer((ViewContainer) findViewById(R.id.viewContainer));

        //Add logic to display Splash Screen which is where the data content will initially be seeded.
        //navigationManager.push(view);

        navigationManager.showScreen();
    }


    /**
     * Finalizes setup by adding this class to dependency graph and setup for image caching/databinding/screen transition animations/database.
     */
    private void setup() {
        //TODO add code to inject the application component.

        DataBindingUtil.setContentView(this, R.layout.main_activity);

        cardView = findViewById(R.id.cardView);
        progressBar = findViewById(R.id.progressBar);
        progressBarMessage = findViewById(R.id.progressBarMessage);

        Fade fade = new Fade(Fade.IN);

        TransitionManager.beginDelayedTransition(findViewById(R.id.viewContainer), fade);

        setupMainScreen();
    }

    private void cleanup() {
        databaseManager.closeDataBase();

        if (instance != null) {
            instance = null;
        }
    }
}
