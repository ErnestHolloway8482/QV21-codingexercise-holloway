package qv21.codingexercise.managers;

import javax.inject.Singleton;

@Singleton
public interface AlertDialogManager {
    void displayAlertMessage(final String title, final String body);

    void displayAlertMessage(final String title, final String body, final String actionButton1Text, final Runnable action1);

    void displayAlertMessage(final String title,
                             final String body,
                             final String actionButton1Text,
                             final Runnable action1,
                             final String actionButton2Text,
                             final Runnable action2);
}
