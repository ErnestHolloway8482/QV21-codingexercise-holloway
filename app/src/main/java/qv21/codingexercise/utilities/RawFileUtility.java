package qv21.codingexercise.utilities;

import android.content.res.Resources;

import java.io.InputStream;
import java.net.URI;
import java.util.Locale;

/**
 * This utility class is used to help convert raw file resources into fully qulified URL's or InputStream depending on the scenario in how a file is to be read.
 */
public class RawFileUtility {
    private static final String RAW_FILE_RESOURCE_URL_FORMAT = "android.resource://%s/raw/%s";

    public static String getFullNameAndPathFromResourceId(final String packageName, final String fileName) {
        if (isStringEmpty(packageName)) {
            return null;
        }

        URI uri = URI.create(String.format(Locale.ENGLISH, RAW_FILE_RESOURCE_URL_FORMAT, packageName, fileName));
        return uri.toString();
    }

    public static InputStream getInputStreamFromResourceId(final Resources resources, final int resourceId) {
        try {
            return resources.openRawResource(resourceId);
        } catch (Resources.NotFoundException e) {
            return null;
        }
    }

    private static boolean isStringEmpty(final String string) {
        if (string == null || string.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
