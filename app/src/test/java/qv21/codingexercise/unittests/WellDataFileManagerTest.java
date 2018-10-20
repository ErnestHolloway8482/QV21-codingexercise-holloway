package qv21.codingexercise.unittests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URL;
import java.util.List;

import qv21.codingexercise.BaseUnitTest;
import qv21.codingexercise.managers.WellDataFileManager;

@RunWith(JUnit4.class)
public class WellDataFileManagerTest extends BaseUnitTest {
    private WellDataFileManager wellDataFileManager;

    @Before
    public void setup() {
        wellDataFileManager = new WellDataFileManager();
    }

    @Test
    public void readWellDataValidFileNameTest() {
        String wellDataFileNameAndPath = getFullPathNameFromResourcesDirectory("well_data.csv");
        List<String> wellData = wellDataFileManager.readWellData(wellDataFileNameAndPath);

        Assert.assertNotNull(wellData);
        Assert.assertTrue(wellData.size() == 1388);
    }

    @Test
    public void readWellDataInValidFileNameTest() {
        String wellDataFileNameAndPath = getFullPathNameFromResourcesDirectory("file_does_not_exist.csv");
        List<String> wellData = wellDataFileManager.readWellData(wellDataFileNameAndPath);

        Assert.assertNull(wellData);
    }

    private String getFullPathNameFromResourcesDirectory(final String fileNameAndPath) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(fileNameAndPath);

        if (url != null) {
            return url.getPath();
        } else {
            return "";
        }
    }
}
