package qv21.codingexercise.integrationtests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URL;

import io.objectbox.query.LazyList;
import qv21.codingexercise.daos.WellDataDAO;
import qv21.codingexercise.daos.WellDataDAOImpl;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.DatabaseManager;
import qv21.codingexercise.managers.DatabaseManagerImpl;
import qv21.codingexercise.managers.WellDataFileManager;
import qv21.codingexercise.mapper.WellDataMapper;
import qv21.codingexercise.models.database.WellData;

@RunWith(JUnit4.class)
public class WellDataFacadeIntegrationTest {
    private WellDataFileManager wellDataFileManager;
    private WellDataMapper wellDataMapper;
    private DatabaseManager databaseManager;
    private WellDataDAO wellDataDAO;
    private WellDataFacade wellDataFacade;

    @Before
    public void setup() {
        wellDataFileManager = new WellDataFileManager();
        wellDataMapper = new WellDataMapper();
        databaseManager = new DatabaseManagerImpl("well_data", true);
        wellDataDAO = new WellDataDAOImpl(databaseManager);

        wellDataFacade = new WellDataFacade(wellDataFileManager, wellDataMapper, databaseManager, wellDataDAO);
    }

    @After
    public void tearDown() {
        wellDataFacade.cleanUpWellData();
    }

    @Test
    public void seedWellDataIntoDatabaseWithValidFileNameTest() {
        Assert.assertFalse(wellDataFacade.doesWellDataExist());

        String databaseFileNameAndPath = getFullPathNameFromResourcesDirectory("well_data.csv");
        Assert.assertTrue(wellDataFacade.seedWellDataIntoDatabase(databaseFileNameAndPath));
        Assert.assertTrue(wellDataFacade.doesWellDataExist());
    }

    @Test
    public void seedWellDataIntoDatabaseWithInValidFileNameTest() {
        Assert.assertFalse(wellDataFacade.doesWellDataExist());

        String databaseFileNameAndPath = getFullPathNameFromResourcesDirectory("does_not_exist.csv");
        Assert.assertFalse(wellDataFacade.seedWellDataIntoDatabase(databaseFileNameAndPath));
        Assert.assertFalse(wellDataFacade.doesWellDataExist());
    }

    @Test
    public void getAllWellDataItems(){
        LazyList<WellData> wellDataLazyList = getWellDataFromFile();

        Assert.assertNotNull(wellDataLazyList);
        Assert.assertFalse(wellDataLazyList.isEmpty());
        Assert.assertEquals(1388, wellDataLazyList.size());
    }

    @Test
    public void updateWellDataTest(){
        LazyList<WellData> wellDataLazyList = getWellDataFromFile();

        WellData originaWellData = wellDataLazyList.get(0);
        originaWellData.setOwnerName("Owner-A");

        wellDataFacade.updateWellData(originaWellData);

        WellData updatedWellData = wellDataFacade.getWellDataByUuid(originaWellData.getUuid());
        Assert.assertEquals("Owner-A", updatedWellData.getOwnerName());
    }

    @Test
    public void deleteWellDataTest(){
        LazyList<WellData> wellDataLazyList = getWellDataFromFile();

        WellData originaWellData = wellDataLazyList.get(0);
        String uuid = originaWellData.getUuid();

        wellDataFacade.deleteWellData(originaWellData);

        WellData foundWellData = wellDataFacade.getWellDataByUuid(uuid);
        Assert.assertNull(foundWellData);
    }

    @Test
    public void getWellDataByUuidTest(){
        LazyList<WellData> wellDataLazyList = getWellDataFromFile();

        String uuid = wellDataLazyList.get(5).getUuid();

        WellData foundWellData = wellDataFacade.getWellDataByUuid(uuid);

        Assert.assertNotNull(foundWellData);
        Assert.assertEquals(uuid, foundWellData.getUuid());
    }



    private LazyList<WellData> getWellDataFromFile() {
        Assert.assertFalse(wellDataFacade.doesWellDataExist());

        String databaseFileNameAndPath = getFullPathNameFromResourcesDirectory("well_data.csv");
        Assert.assertTrue(wellDataFacade.seedWellDataIntoDatabase(databaseFileNameAndPath));
        Assert.assertTrue(wellDataFacade.doesWellDataExist());

        return wellDataFacade.getAllWellDataItems();
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
