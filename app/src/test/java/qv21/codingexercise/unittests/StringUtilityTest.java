package qv21.codingexercise.unittests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import qv21.codingexercise.BaseUnitTest;
import qv21.codingexercise.utilities.StringUtility;

@RunWith(JUnit4.class)
public class StringUtilityTest extends BaseUnitTest {

    @Test
    public void stringIsEmptyTest() {
        //Null
        String string = null;
        Assert.assertTrue(StringUtility.isEmpty(string));

        //Empty
        string = "";
        Assert.assertTrue(StringUtility.isEmpty(string));
    }

    @Test
    public void stringIsNotEmptyTest() {
        String string = " ";
        Assert.assertFalse(StringUtility.isEmpty(string));

        //Empty
        string = "123";
        Assert.assertFalse(StringUtility.isEmpty(string));
    }
}
