package qv21.codingexercise;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.query.LazyList;
import qv21.codingexercise.daos.WellDataDAO;
import qv21.codingexercise.daos.WellDataDAOImpl;
import qv21.codingexercise.managers.DatabaseManagerImpl;
import qv21.codingexercise.models.database.WellData;

@RunWith(JUnit4.class)
public class WellDataDAOImplTest {
    private DatabaseManagerImpl databaseManager;
    private WellDataDAO wellDataDAO;

    @Before
    public void setup() {
        databaseManager = new DatabaseManagerImpl("well_data", true);
        wellDataDAO = new WellDataDAOImpl(databaseManager);
    }

    @After
    public void tearDown() {
        Assert.assertTrue(databaseManager.closeDataBase());
        Assert.assertTrue(databaseManager.deleteDataBase());
    }

    @Test
    public void createWellTest() {
        WellData wellData = new WellData();

        Assert.assertTrue(wellDataDAO.createWell(wellData));
    }

    @Test
    public void getAllWellDataTest() {
        List<WellData> wellDataList = generateTestWellData(100);

        for (int i = 0; i < 100; i++) {
            WellData wellData = wellDataList.get(i);

            Assert.assertTrue(wellDataDAO.createWell(wellData));
        }

        LazyList<WellData> wellDataLazyList = (LazyList<WellData>) wellDataDAO.getAllWellData();

        Assert.assertNotNull(wellDataLazyList);
        Assert.assertEquals(100, wellDataLazyList.size());

        for (int i = 0; i < 100; i++) {
            Assert.assertNotNull(wellDataList.get(i).getUuid());
        }
    }

    @Test
    public void getNumberOfWellDataItems() {
        List<WellData> wellDataList = generateTestWellData(100);

        for (int i = 0; i < 100; i++) {
            WellData wellData = wellDataList.get(i);

            Assert.assertTrue(wellDataDAO.createWell(wellData));
        }

        long numberOfItems = wellDataDAO.getNumberOfWellDataItems();

        Assert.assertEquals(100, numberOfItems);
    }

    @Test
    public void updateWellTest() {
        WellData originalWellData = new WellData();
        originalWellData.setOwnerName("Owner-A");

        Assert.assertTrue(wellDataDAO.createWell(originalWellData));
        Assert.assertEquals(1, wellDataDAO.getNumberOfWellDataItems());

        WellData foundData = wellDataDAO.getWellDataByUuid(originalWellData.getUuid());
        Assert.assertNotNull(foundData);
        Assert.assertEquals("Owner-A", foundData.getOwnerName());

        foundData.setOwnerName("Owner-B");

        Assert.assertTrue(wellDataDAO.updateWell(foundData));

        WellData updatedWellData = wellDataDAO.getWellDataByUuid(foundData.getUuid());

        Assert.assertNotNull(updatedWellData);

        Assert.assertEquals(foundData.getUuid(), updatedWellData.getUuid());
        Assert.assertNotEquals("Owner-A", updatedWellData.getOwnerName());
        Assert.assertEquals("Owner-B", updatedWellData.getOwnerName());
        Assert.assertEquals(1, wellDataDAO.getNumberOfWellDataItems());
    }

    @Test
    public void deleteAllWellDataTest() {
        List<WellData> wellDataList = generateTestWellData(100);

        for (int i = 0; i < 100; i++) {
            WellData wellData = wellDataList.get(i);

            Assert.assertTrue(wellDataDAO.createWell(wellData));
        }

        Assert.assertEquals(100, wellDataDAO.getNumberOfWellDataItems());

        wellDataDAO.deleteAllWellData();

        Assert.assertEquals(0, wellDataDAO.getNumberOfWellDataItems());
        Assert.assertTrue(wellDataDAO.getAllWellData().isEmpty());
    }

    @Test
    public void deleteWellDataTest() {
        List<WellData> wellDataList = generateTestWellData(2);
        String serachUUID = wellDataList.get(1).getUuid();

        for (int i = 0; i < 2; i++) {
            WellData wellData = wellDataList.get(i);

            Assert.assertTrue(wellDataDAO.createWell(wellData));
        }

        Assert.assertEquals(2, wellDataDAO.getNumberOfWellDataItems());
        wellDataDAO.deleteWellData(wellDataList.get(0));
        Assert.assertEquals(1, wellDataDAO.getNumberOfWellDataItems());

        WellData foundWellData = wellDataDAO.getWellDataByUuid(serachUUID);

        Assert.assertNotNull(foundWellData);
        Assert.assertEquals(serachUUID, foundWellData.getUuid());
    }

    @Test
    public void getWellDataByUuidTest() {
        WellData wellData = new WellData();

        Assert.assertTrue(wellDataDAO.createWell(wellData));

        WellData retrievedWellData = wellDataDAO.getWellDataByUuid(wellData.getUuid());

        Assert.assertNotNull(retrievedWellData);
        Assert.assertEquals(wellData.getUuid(), retrievedWellData.getUuid());
    }


    private List<WellData> generateTestWellData(final int numerOfItems) {
        List<WellData> wellDataList = new ArrayList<>();

        for (int i = 0; i < numerOfItems; i++) {
            WellData wellData = new WellData();

            wellDataList.add(wellData);
        }

        return wellDataList;
    }

}
