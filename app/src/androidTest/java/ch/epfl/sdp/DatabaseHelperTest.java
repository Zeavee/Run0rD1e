package ch.epfl.sdp;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import ch.epfl.sdp.game.DatabaseHelper;

@RunWith(AndroidJUnit4.class)
public class DatabaseHelperTest {
    private DatabaseHelper databaseHelper;

    @Before
    public void setUp() {
        databaseHelper = new DatabaseHelper(InstrumentationRegistry.getTargetContext());
    }

    @After
    public void finish() {
        databaseHelper.close();
    }

    @Test
    public void saveLoggedUser_shouldSaveToDatabase() {
        databaseHelper.saveLoggedUser("user@example.com", "abcdefgh");
        DatabaseHelper.UserData loggedUser = databaseHelper.getLoggedUser();
        Assert.assertEquals("user@example.com", loggedUser.email);
        Assert.assertEquals("abcdefgh", loggedUser.password);
        databaseHelper.deleteAllUsers();
    }

    @Test
    public void getLoggedUser_noUser_ReturnsNull() {
        DatabaseHelper.UserData loggedUser = databaseHelper.getLoggedUser();
        Assert.assertNull(loggedUser);
    }

    @Test
    public void deleteUsers_deletesUsers() {
        databaseHelper.saveLoggedUser("user@example.com", "abcdefgh");
        databaseHelper.deleteAllUsers();
        DatabaseHelper.UserData loggedUser = databaseHelper.getLoggedUser();
        Assert.assertNull(loggedUser);
    }
}
