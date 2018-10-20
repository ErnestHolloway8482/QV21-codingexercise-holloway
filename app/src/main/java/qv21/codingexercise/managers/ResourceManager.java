package qv21.codingexercise.managers;

import java.io.InputStream;

public interface ResourceManager {
    String getString(final int resourceId);

    String getFullNameAndPathFromResourceId(final String packageName, final String fileName);

    InputStream getInputStreamFromResourceId(final int resourceId);
}
