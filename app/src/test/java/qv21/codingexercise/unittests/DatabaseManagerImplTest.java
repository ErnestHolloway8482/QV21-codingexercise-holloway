package qv21.codingexercise.unittests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import qv21.codingexercise.BaseUnitTest;
import qv21.codingexercise.managers.DatabaseManagerImpl;

@RunWith(JUnit4.class)
public class DatabaseManagerImplTest extends BaseUnitTest {
    private DatabaseManagerImpl databaseManager;

    @Before
    public void setup() {
        databaseManager = new DatabaseManagerImpl("well_data_test", true);
    }

    @After
    public void tearDown() {
        databaseManager.closeDataBase();
        databaseManager.deleteDataBase();
    }

    @Test
    public void closeDataBaseTest() {
        Assert.assertTrue(databaseManager.closeDataBase());
    }

    @Test
    public void deleteDatabaseTest() {
        Assert.assertTrue(databaseManager.deleteDataBase());
    }
}
