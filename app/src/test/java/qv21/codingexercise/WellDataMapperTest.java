package qv21.codingexercise;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import qv21.codingexercise.mapper.WellDataMapper;
import qv21.codingexercise.models.database.WellData;

@RunWith(JUnit4.class)
public class WellDataMapperTest {
    private WellDataMapper wellDataMapper;

    @Before
    public void setup() {
        wellDataMapper = new WellDataMapper();
    }

    @Test
    public void mapWellDataWithValidStringTest() {
        //Owner,API #,Longitude,Latitude,Property #,Lease / Well Name,Tank MID,Tank Name,Tank Nbr,Tank Size,BBLS Per Inch,SEC,TWP,RNG,COUNTY
        String csvRowData = "\"Continental Resources, Inc.\",2508321270,-104.32836,47.60448,200210,Sorenson 14-6H,1065,Sorenson - 247983,1,405.56,1.675867769,6,021N,058E,RICHLAND";

        WellData wellData = wellDataMapper.mapWellData(csvRowData);

        Assert.assertNotNull(wellData);
        Assert.assertNotNull(wellData.getUuid());
        Assert.assertEquals("Continental Resources, Inc.", wellData.getOwnerName());
        Assert.assertEquals("2508321270", wellData.getApiNumber());
        Assert.assertEquals(-104.32836, wellData.getLongitude(), .001);
        Assert.assertEquals(47.60448, wellData.getLatitude(), .001);
        Assert.assertEquals(200210, wellData.getPropertyNumber());
        Assert.assertEquals("Sorenson 14-6H", wellData.getWellName());
        Assert.assertEquals(1065, wellData.getMid());
        Assert.assertEquals("Sorenson - 247983", wellData.getTankName());
        Assert.assertEquals(1, wellData.getTankNumber());
        Assert.assertEquals(405.56, wellData.getTankSize(), .001);
        Assert.assertEquals(1.675867769, wellData.getBblsPerInch(), .001);
        Assert.assertEquals(6, wellData.getSec());
        Assert.assertEquals("021N", wellData.getTwp());
        Assert.assertEquals("058E", wellData.getRng());
        Assert.assertEquals("RICHLAND", wellData.getCounty());
    }

    @Test
    public void mapWellDataWithInValidStringTest() {
        //14 Items - Less than 15 items per line to parse
        String csvRowData = "\"Continental Resources, Inc.\",2508321270,-104.32836,47.60448,200210,Sorenson 14-6H,1065,Sorenson - 247983,1,405.56,1.675867769,6,021N,058E";
        WellData wellData = wellDataMapper.mapWellData(csvRowData);
        Assert.assertNull(wellData);

        //7 Items - Less than 15 items per line to parse
        csvRowData = "\"Continental Resources, Inc.\",2508321270,-104.32836,47.60448,200210,Sorenson 14-6H,1065";
        wellData = wellDataMapper.mapWellData(csvRowData);
        Assert.assertNull(wellData);

        //Null String
        csvRowData = null;
        wellData = wellDataMapper.mapWellData(csvRowData);
        Assert.assertNull(wellData);

        //Empty String
        csvRowData = "";
        wellData = wellDataMapper.mapWellData(csvRowData);
        Assert.assertNull(wellData);

        //All valid number columns have a value of -1, since they failed to parse due to NumberFormatException.
        csvRowData = "\"Continental Resources, Inc.\",2508321270,__,__,__,Sorenson 14-6H,__,Sorenson - 247983,__,__,__,_,021N,058E,RICHLAND";
        wellData = wellDataMapper.mapWellData(csvRowData);
        Assert.assertNotNull(wellData);
        Assert.assertNotNull(wellData.getUuid());
        Assert.assertEquals("Continental Resources, Inc.", wellData.getOwnerName());
        Assert.assertEquals("2508321270", wellData.getApiNumber());
        Assert.assertEquals(-1, wellData.getLongitude(), .001);
        Assert.assertEquals(-1, wellData.getLatitude(), .001);
        Assert.assertEquals(-1, wellData.getPropertyNumber());
        Assert.assertEquals("Sorenson 14-6H", wellData.getWellName());
        Assert.assertEquals(-1, wellData.getMid());
        Assert.assertEquals("Sorenson - 247983", wellData.getTankName());
        Assert.assertEquals(-1, wellData.getTankNumber());
        Assert.assertEquals(-1, wellData.getTankSize(), .001);
        Assert.assertEquals(-1, wellData.getBblsPerInch(), .001);
        Assert.assertEquals(-1, wellData.getSec());
        Assert.assertEquals("021N", wellData.getTwp());
        Assert.assertEquals("058E", wellData.getRng());
        Assert.assertEquals("RICHLAND", wellData.getCounty());
    }


}
