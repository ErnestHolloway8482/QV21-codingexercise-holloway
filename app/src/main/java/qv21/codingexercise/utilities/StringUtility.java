package qv21.codingexercise.utilities;

/**
 * A simple utiliy class that provides a conveniece method to determine if a string is empty or not and handles null condition check.
 */
public class StringUtility {
    public static boolean isEmpty(final String string) {
        if (string == null || string.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
