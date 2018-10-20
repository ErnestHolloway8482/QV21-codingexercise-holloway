package qv21.codingexercise;

import android.app.AlertDialog;

import org.mockito.Mockito;

import javax.inject.Singleton;

import qv21.codingexercise.managers.AlertDialogManager;
import qv21.codingexercise.managers.MainActivityProviderManager;
import qv21.codingexercise.utilities.LoggerUtils;

/**
 * {@link Singleton} manager that is used to display a modal pop-up dialog to the user for critical messages that need
 * to be conveyed.
 */
@Singleton
public class AlertDialogManagerAndroidTestImpl implements AlertDialogManager {
    private final MainActivityProviderManager mainActivityProviderManager;

    public AlertDialogManagerAndroidTestImpl(final MainActivityProviderManager mainActivityProviderManager) {
        this.mainActivityProviderManager = mainActivityProviderManager;
    }

    @Override
    public void displayAlertMessage(final String title, final String body) {
        try {
            buildAlertDialog(title, body);
        } catch (Exception e) {
            LoggerUtils.logError(e.getMessage());
        }
    }

    @Override
    public void displayAlertMessage(final String title,
                                    final String body,
                                    final String actionButton1Text,
                                    final Runnable action1) {
        try {
            buildAlertDialog(title, body, actionButton1Text, action1);
        } catch (Exception e) {
            LoggerUtils.logError(e.getMessage());
        }
    }

    @Override
    public void displayAlertMessage(final String title,
                                    final String body,
                                    final String actionButton1Text,
                                    final Runnable action1,
                                    final String actionButton2Text,
                                    final Runnable action2) {
        try {
            buildAlertDialog(title, body, actionButton1Text, action1, actionButton2Text, action2);
        } catch (Exception e) {
            LoggerUtils.logError(e.getMessage());
        }
    }

    private void buildAlertDialog(final String title, final String body) {
        AlertDialog.Builder builder = getMockAlertDialogBuilder();
        builder.setTitle(title)
                .setMessage(body)
                .create()
                .show();
    }

    private void buildAlertDialog(final String title,
                                  final String body,
                                  final String actionButton1Text,
                                  final Runnable action1) {
        AlertDialog.Builder builder = getMockAlertDialogBuilder();
        builder.setTitle(title)
                .setMessage(body)
                .setPositiveButton(actionButton1Text, (dialog, which) -> action1.run())
                .create()
                .show();

        if (action1 != null) {
            action1.run();
        }
    }

    private void buildAlertDialog(final String title,
                                  final String body,
                                  final String actionButton1Text,
                                  final Runnable action1,
                                  final String actionButton2Text,
                                  final Runnable action2) {
        AlertDialog.Builder builder = getMockAlertDialogBuilder();
        builder.setTitle(title)
                .setMessage(body)
                .setPositiveButton(actionButton1Text, (dialog, which) -> action1.run())
                .setNegativeButton(actionButton2Text, (dialog, which) -> action2.run())
                .create()
                .show();

        if (action1 != null) {
            action1.run();
        }
    }

    private AlertDialog.Builder getMockAlertDialogBuilder(){
        AlertDialog.Builder builder = Mockito.mock(AlertDialog.Builder.class);
        AlertDialog alertDialog = Mockito.mock(AlertDialog.class);

        Mockito.when(builder.setTitle(Mockito.any())).thenReturn(builder);
        Mockito.when(builder.setMessage(Mockito.any())).thenReturn(builder);
        Mockito.when(builder.setPositiveButton(Mockito.any(), Mockito.any())).thenReturn(builder);
        Mockito.when(builder.setNegativeButton(Mockito.any(), Mockito.any())).thenReturn(builder);
        Mockito.when(builder.setPositiveButton(Mockito.any(), Mockito.any())).thenReturn(builder);
        Mockito.when(builder.create()).thenReturn(alertDialog);

        return builder;

    }
}
