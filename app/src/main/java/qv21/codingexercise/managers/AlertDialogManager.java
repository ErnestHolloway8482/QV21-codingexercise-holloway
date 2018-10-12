package qv21.codingexercise.managers;

import javax.inject.Singleton;

/**
 * {@link Singleton} manager that is used to display a modal pop-up dialog to the user for critical messages that need
 * to be conveyed.
 */
@Singleton
public interface AlertDialogManager {
    /**
     * Displays a modal pop-up dialog to the user.
     *
     * @param title is the main title of the pop-up dialog.
     * @param body  is the message body of the pop-up dialog.
     */
    void displayAlertMessage(final String title, final String body);

    /**
     * Displays a modal pop-up dialog to the user.
     *
     * @param title             is the main title of the pop-up dialog.
     * @param body              is the message body of the pop-up dialog.
     * @param actionButton1Text is the button text for the first action to appear on the dialog.
     * @param action1           is the corresponding {@link Runnable} action to execute when the first button is selected.
     */
    void displayAlertMessage(final String title, final String body, final String actionButton1Text, final Runnable action1);

    /**
     * Displays a modal pop-up dialog to the user.
     *
     * @param title             is the main title of the pop-up dialog.
     * @param body              is the message body of the pop-up dialog.
     * @param actionButton1Text is the button text for the first action to appear on the dialog.
     * @param action1           is the corresponding {@link Runnable} action to execute when the first button is selected.
     * @param actionButton2Text is the button text for the second action to appear on the dialog.
     * @param action2           is the corresponding {@link Runnable} action to execute when the second button is selected.
     */
    void displayAlertMessage(final String title,
                             final String body,
                             final String actionButton1Text,
                             final Runnable action1,
                             final String actionButton2Text,
                             final Runnable action2);
}
