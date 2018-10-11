package qv21.codingexercise.utilities;

import android.os.Build;

/**
 * This utility class is used to indicate certain states of the current build type/flavor and can be used
 * for automated testing to configure test mode behavior.
 */
public class BuildConfigUtility {
    private static boolean isInTestMode = false;
    private static boolean isInAndroidTestMode = false;
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

    public static boolean isIsInAndroidTestMode() {
        return isInAndroidTestMode;
    }

    public static void setIsInAndroidTestMode(final boolean isInAndroidTestMode) {
        BuildConfigUtility.isInAndroidTestMode = isInAndroidTestMode;
    }
}
