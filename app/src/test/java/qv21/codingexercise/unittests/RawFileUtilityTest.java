package qv21.codingexercise.unittests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import qv21.codingexercise.BaseUnitTest;
import qv21.codingexercise.utilities.RawFileUtility;

@RunWith(JUnit4.class)
public class RawFileUtilityTest extends BaseUnitTest {

    @Test
    public void getFullNameAndPathFromResourceIdWithValidPackageNameTest() {
        Assert.assertNotNull(RawFileUtility.getFullNameAndPathFromResourceId("com.package.name", "well_data.csv"));

        Assert.assertEquals("android.resource://com.package.name/raw/well_data.csv",
                RawFileUtility.getFullNameAndPathFromResourceId("com.package.name", "well_data.csv"));
    }

    @Test
    public void getFullNameAndPathFromResourceIdWithInValidPackageNameTest() {
        Assert.assertNull(RawFileUtility.getFullNameAndPathFromResourceId("", "well_data.csv"));
        Assert.assertNull(RawFileUtility.getFullNameAndPathFromResourceId(null, "well_data.csv"));
    }
}
