package qv21.codingexercise.views;

import android.content.Context;
import android.util.AttributeSet;

import qv21.codingexercise.R;
import qv21.codingexercise.databinding.SplashScreenBinding;
import qv21.codingexercise.models.viewmodels.SplashVM;

public class SplashScreen extends ScreenImpl<SplashVM, SplashScreenBinding> {
    public SplashScreen(final Context context) {
        super(context, R.layout.splash_screen);
    }

    public SplashScreen(final Context context, final AttributeSet attrs) {
        super(context, attrs, R.layout.splash_screen);
    }

    public SplashScreen(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr, R.layout.splash_screen);
    }
}
