package qv21.codingexercise.unittests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import qv21.codingexercise.utilities.RawFileUtility;

@RunWith(JUnit4.class)
public class RawFileUtilityTest {

    @Test
    public void getFullNameAndPathFromResourceIdWithValidPackageNameTest() {
        Assert.assertNotNull(RawFileUtility.getFullNameAndPathFromResourceId("com.package.name", 1000));

        Assert.assertEquals("android.resource://com.package.name/1000",
                RawFileUtility.getFullNameAndPathFromResourceId("com.package.name", 1000));
    }

    @Test
    public void getFullNameAndPathFromResourceIdWithInValidPackageNameTest() {
        Assert.assertNull(RawFileUtility.getFullNameAndPathFromResourceId("", 1000));
        Assert.assertNull(RawFileUtility.getFullNameAndPathFromResourceId(null, 1000));
    }
}
