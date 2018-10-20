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
import qv21.codingexercise.managers.MemoryCacheManager;
import qv21.codingexercise.managers.WellDataFileManager;
import qv21.codingexercise.mapper.WellDataMapper;
import qv21.codingexercise.models.databasemodels.WellDataDM;

@RunWith(JUnit4.class)
public class WellDataFacadeIntegrationTest {
    private WellDataFileManager wellDataFileManager;
    private WellDataMapper wellDataMapper;
    private DatabaseManager databaseManager;
    private WellDataDAO wellDataDAO;
    private WellDataFacade wellDataFacade;
    private MemoryCacheManager memoryCacheManager;

    @Before
    public void setup() {
        wellDataFileManager = new WellDataFileManager();
        wellDataMapper = new WellDataMapper();
        databaseManager = new DatabaseManagerImpl("well_data", true);
        wellDataDAO = new WellDataDAOImpl(databaseManager);
        memoryCacheManager = new MemoryCacheManager();

        wellDataFacade = new WellDataFacade(wellDataFileManager,
                wellDataMapper,
                databaseManager,
                wellDataDAO,
                memoryCacheManager);
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
    public void getAllWellDataItems() {
        LazyList<WellDataDM> wellDataLazyList = getWellDataFromFile();

        Assert.assertNotNull(wellDataLazyList);
        Assert.assertFalse(wellDataLazyList.isEmpty());
        Assert.assertEquals(1388, wellDataLazyList.size());
    }

    @Test
    public void updateWellDataTest() {
        LazyList<WellDataDM> wellDataLazyList = getWellDataFromFile();

        WellDataDM originaWellData = wellDataLazyList.get(0);
        originaWellData.setOwnerName("Owner-A");

        wellDataFacade.updateWellData(originaWellData);

        WellDataDM updatedWellData = wellDataFacade.getWellDataByUuid(originaWellData.getUuid());
        Assert.assertEquals("Owner-A", updatedWellData.getOwnerName());
    }

    @Test
    public void deleteWellDataTest() {
        LazyList<WellDataDM> wellDataLazyList = getWellDataFromFile();

        WellDataDM originaWellData = wellDataLazyList.get(0);
        String uuid = originaWellData.getUuid();

        wellDataFacade.deleteWellData(originaWellData);

        WellDataDM foundWellData = wellDataFacade.getWellDataByUuid(uuid);
        Assert.assertNull(foundWellData);
    }

    @Test
    public void getWellDataByUuidTest() {
        LazyList<WellDataDM> wellDataLazyList = getWellDataFromFile();

        String uuid = wellDataLazyList.get(5).getUuid();

        WellDataDM foundWellData = wellDataFacade.getWellDataByUuid(uuid);

        Assert.assertNotNull(foundWellData);
        Assert.assertEquals(uuid, foundWellData.getUuid());
    }

    private LazyList<WellDataDM> getWellDataFromFile() {
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
