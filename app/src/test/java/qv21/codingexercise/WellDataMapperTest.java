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
    public void mapWellDataWithValidStringTest(){
        /**
         * Owner,API #,Longitude,Latitude,Property #,Lease / Well Name,Tank MID,Tank Name,Tank Nbr,Tank Size,BBLS Per Inch,SEC,TWP,RNG,COUNTY
         * "Continental Resources, Inc.",2508321270,-104.32836,47.60448,200210,Sorenson 14-6H,1065,Sorenson - 247983,1,405.56,1.675867769,6,021N,058E,RICHLAND
         */

        String csvRowData = "Continental Resources, Inc.,2508321270,-104.32836,47.60448,200210,Sorenson 14-6H,1065,Sorenson - 247983,1,405.56,1.675867769,6,021N,058E,RICHLAND";

        WellData wellData = wellDataMapper.mapWellData(csvRowData);

        Assert.assertNotNull(wellData);
        Assert.assertNotNull(wellData.getUuid());
        Assert.assertEquals("Continental Resources, Inc.", wellData.getOwnerName());
        Assert.assertEquals("2508321270", wellData.getApiNumber());
    }
}
