package qv21.codingexercise.unittests;

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
import qv21.codingexercise.models.databasemodels.WellDataDM;

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
        WellDataDM wellData = new WellDataDM();

        Assert.assertTrue(wellDataDAO.createWell(wellData));
    }

    @Test
    public void getAllWellDataTest() {
        List<WellDataDM> wellDataList = generateTestWellData(100);

        for (int i = 0; i < 100; i++) {
            WellDataDM wellData = wellDataList.get(i);

            Assert.assertTrue(wellDataDAO.createWell(wellData));
        }

        LazyList<WellDataDM> wellDataLazyList = (LazyList<WellDataDM>) wellDataDAO.getAllWellData();

        Assert.assertNotNull(wellDataLazyList);
        Assert.assertEquals(100, wellDataLazyList.size());

        for (int i = 0; i < 100; i++) {
            Assert.assertNotNull(wellDataList.get(i).getUuid());
        }
    }

    @Test
    public void getNumberOfWellDataItems() {
        List<WellDataDM> wellDataList = generateTestWellData(100);

        for (int i = 0; i < 100; i++) {
            WellDataDM wellData = wellDataList.get(i);

            Assert.assertTrue(wellDataDAO.createWell(wellData));
        }

        long numberOfItems = wellDataDAO.getNumberOfWellDataItems();

        Assert.assertEquals(100, numberOfItems);
    }

    @Test
    public void updateWellTest() {
        WellDataDM originalWellData = new WellDataDM();
        originalWellData.setOwnerName("Owner-A");

        Assert.assertTrue(wellDataDAO.createWell(originalWellData));
        Assert.assertEquals(1, wellDataDAO.getNumberOfWellDataItems());

        WellDataDM foundData = wellDataDAO.getWellDataByUuid(originalWellData.getUuid());
        Assert.assertNotNull(foundData);
        Assert.assertEquals("Owner-A", foundData.getOwnerName());

        foundData.setOwnerName("Owner-B");

        Assert.assertTrue(wellDataDAO.updateWell(foundData));

        WellDataDM updatedWellData = wellDataDAO.getWellDataByUuid(foundData.getUuid());

        Assert.assertNotNull(updatedWellData);

        Assert.assertEquals(foundData.getUuid(), updatedWellData.getUuid());
        Assert.assertNotEquals("Owner-A", updatedWellData.getOwnerName());
        Assert.assertEquals("Owner-B", updatedWellData.getOwnerName());
        Assert.assertEquals(1, wellDataDAO.getNumberOfWellDataItems());
    }

    @Test
    public void deleteAllWellDataTest() {
        List<WellDataDM> wellDataList = generateTestWellData(100);

        for (int i = 0; i < 100; i++) {
            WellDataDM wellData = wellDataList.get(i);

            Assert.assertTrue(wellDataDAO.createWell(wellData));
        }

        Assert.assertEquals(100, wellDataDAO.getNumberOfWellDataItems());

        wellDataDAO.deleteAllWellData();

        Assert.assertEquals(0, wellDataDAO.getNumberOfWellDataItems());
        Assert.assertTrue(wellDataDAO.getAllWellData().isEmpty());
    }

    @Test
    public void deleteWellDataTest() {
        List<WellDataDM> wellDataList = generateTestWellData(2);
        String serachUUID = wellDataList.get(1).getUuid();

        for (int i = 0; i < 2; i++) {
            WellDataDM wellData = wellDataList.get(i);

            Assert.assertTrue(wellDataDAO.createWell(wellData));
        }

        Assert.assertEquals(2, wellDataDAO.getNumberOfWellDataItems());
        wellDataDAO.deleteWellData(wellDataList.get(0));
        Assert.assertEquals(1, wellDataDAO.getNumberOfWellDataItems());

        WellDataDM foundWellData = wellDataDAO.getWellDataByUuid(serachUUID);

        Assert.assertNotNull(foundWellData);
        Assert.assertEquals(serachUUID, foundWellData.getUuid());
    }

    @Test
    public void getWellDataByUuidTest() {
        WellDataDM wellData = new WellDataDM();

        Assert.assertTrue(wellDataDAO.createWell(wellData));

        WellDataDM retrievedWellData = wellDataDAO.getWellDataByUuid(wellData.getUuid());

        Assert.assertNotNull(retrievedWellData);
        Assert.assertEquals(wellData.getUuid(), retrievedWellData.getUuid());
    }


    private List<WellDataDM> generateTestWellData(final int numerOfItems) {
        List<WellDataDM> wellDataList = new ArrayList<>();

        for (int i = 0; i < numerOfItems; i++) {
            WellDataDM wellData = new WellDataDM();

            wellDataList.add(wellData);
        }

        return wellDataList;
    }

}
