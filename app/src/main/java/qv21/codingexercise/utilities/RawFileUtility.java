package qv21.codingexercise.utilities;

import java.net.URI;
import java.util.Locale;

public class RawFileUtility {
    private static final String RAW_FILE_RESOURCE_URL_FORMAT = "android.resource://%s/%d";

    public static String getFullNameAndPathFromResourceId(final String packageName, final int rawFileResourceId) {
        if (isStringEmpty(packageName)) {
            return null;
        }

        URI uri = URI.create(String.format(Locale.ENGLISH, RAW_FILE_RESOURCE_URL_FORMAT, packageName, rawFileResourceId));
        return uri.toString();
    }

    private static boolean isStringEmpty(final String string) {
        if (string == null || string.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
