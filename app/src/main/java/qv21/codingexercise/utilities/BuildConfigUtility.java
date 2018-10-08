package qv21.codingexercise.utilities;

import android.os.Build;

/**
 * Created by ernestholloway on 11/1/15.
 */
public class BuildConfigUtility {
    private static boolean isInTestMode = false;
    private static boolean isNetworkDisabledForTest;

    public static boolean isLoggingEnabled() {
        return Build.TYPE.equals("debug");
    }

    public static boolean isInTestMode() {
        return BuildConfigUtility.isInTestMode;
    }

    public static void setIsInTestMode(final boolean isInTestMode) {
        BuildConfigUtility.isInTestMode = isInTestMode;
    }
}
