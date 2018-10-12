package qv21.codingexercise.managers;

import android.app.AlertDialog;

import javax.inject.Singleton;

import qv21.codingexercise.activities.MainActivity;

/**
 * {@link Singleton} manager that is used to display a modal pop-up dialog to the user for critical messages that need
 * to be conveyed.
 */
@Singleton
public class AlertDialogManagerImpl implements AlertDialogManager {

    @Override
    public void displayAlertMessage(final String title, final String body) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.getInstance());
        builder.setTitle(title)
                .setMessage(body)
                .create()
                .show();
    }

    @Override
    public void displayAlertMessage(final String title, final String body, final String actionButton1Text, final Runnable action1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.getInstance());
        builder.setTitle(title)
                .setMessage(body)
                .setPositiveButton(actionButton1Text, (dialog, which) -> action1.run())
                .create()
                .show();
    }

    @Override
    public void displayAlertMessage(final String title, final String body, final String actionButton1Text, final Runnable action1, final String actionButton2Text, final Runnable action2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.getInstance());
        builder.setTitle(title)
                .setMessage(body)
                .setPositiveButton(actionButton1Text, (dialog, which) -> action1.run())
                .setNegativeButton(actionButton2Text, (dialog, which) -> action2.run())
                .create()
                .show();

    }
}
