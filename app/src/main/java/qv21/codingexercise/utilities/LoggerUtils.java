/**
 */

package qv21.codingexercise.utilities;

import android.util.Log;

/**
 * This utility is used as a convenience method for logging debug data within the android console.
 */
public class LoggerUtils {
    private static final String LOG_TAG = "QV21:";

    public static void log(String message) {
        if (BuildConfigUtility.isLoggingEnabled()) {
            Log.v(LOG_TAG, message);
        }
    }

    public static void log(String logPrefix, String message) {
        if (BuildConfigUtility.isLoggingEnabled()) {
            StringBuilder builder = new StringBuilder();
            builder.append(LOG_TAG);
            builder.append("{");
            builder.append(logPrefix);
            builder.append("}:");

            Log.v(builder.toString(), message);
        }
    }

    public static void logError(String message) {
        if (BuildConfigUtility.isLoggingEnabled()) {
            Log.e(LOG_TAG, message);
        }
    }

    public static void logError(String logPrefix, String message) {
        if (BuildConfigUtility.isLoggingEnabled()) {
            StringBuilder builder = new StringBuilder();
            builder.append(LOG_TAG);
            builder.append("{");
            builder.append(logPrefix);
            builder.append("}:");

            Log.e(builder.toString(), message);
        }
    }
}
