package qv21.codingexercise.unittests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import qv21.codingexercise.BaseUnitTest;
import qv21.codingexercise.bindings.Converter;

@RunWith(JUnit4.class)
public class ConverterTest extends BaseUnitTest {

    @Test
    public void convertStringToIntegerTest() {
        Assert.assertEquals(10, Converter.convertStringToInteger("10"));
        Assert.assertEquals(0, Converter.convertStringToInteger("ee"));
    }

    @Test
    public void convertIntegerToStringTest() {
        Assert.assertEquals("10", Converter.convertIntegerToString(10));
    }

    @Test
    public void convertStringToFloatTest() {
        Assert.assertEquals(10.1, Converter.convertStringToFloat("10.1"), .001f);
        Assert.assertEquals(0, Converter.convertStringToFloat("ee"), .001f);
    }

    @Test
    public void convertFloatToString() {
        Assert.assertEquals("10.1", Converter.convertFloatToString(10.1f));
    }

    @Test
    public void convertStringToDoubleTest() {
        Assert.assertEquals(10.1123, Converter.convertStringToDouble("10.1123"), .00001d);
        Assert.assertEquals(0, Converter.convertStringToDouble("ee"), .00001d);
    }

    @Test
    public void convertDoubleToStringTest() {
        Assert.assertEquals("10.1123", Converter.convertDoubleToString(10.1123));
    }
}
