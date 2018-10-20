package qv21.codingexercise.managers;

import android.content.res.Resources;

import java.io.InputStream;
import java.net.URI;
import java.util.Locale;

import javax.inject.Singleton;

import qv21.codingexercise.utilities.StringUtility;

/**
 * A {@link Singleton} class that handles converting resource identifies into {@link String} or {@link InputStream} objects.
 */
@Singleton
public class ResourceManagerImpl implements ResourceManager {
    private static final String RAW_FILE_RESOURCE_URL_FORMAT = "android.resource://%s/raw/%s";

    private final MainActivityProviderManager mainActivityProviderManager;

    public ResourceManagerImpl(final MainActivityProviderManager mainActivityProviderManager) {
        this.mainActivityProviderManager = mainActivityProviderManager;
    }

    @Override
    public String getString(final int resourceId) {
        return mainActivityProviderManager.provideMainActivity().getString(resourceId);
    }

    @Override
    public String getFullNameAndPathFromResourceId(final String packageName, final String fileName) {

        if (StringUtility.isEmpty(packageName)) {
            return null;
        }

        URI uri = URI.create(String.format(Locale.ENGLISH, RAW_FILE_RESOURCE_URL_FORMAT, packageName, fileName));
        return uri.toString();
    }

    @Override
    public InputStream getInputStreamFromResourceId(final int resourceId) {
        try {
            return mainActivityProviderManager.getResources().openRawResource(resourceId);
        } catch (Resources.NotFoundException e) {
            return null;
        }
    }
}
