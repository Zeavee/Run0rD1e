package ch.epfl.sdp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.core.content.ContextCompat;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import java.util.HashMap;

import ch.epfl.sdp.database.authentication.MockAuthenticationAPI;
import ch.epfl.sdp.database.firebase.api.CommonMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.map.MapsActivity;
import ch.epfl.sdp.utils.DependencyFactory;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class MapsActivityTest {

    private static final int PERMISSIONS_DIALOG_DELAY = 3000;
    private static final int GRANT_BUTTON_INDEX = 0;
    private static final int DENY_BUTTON_INDEX = 1;

    @Before
    public void setup() {
        PlayerManager.getInstance().setCurrentUser(new Player("testMap", "testMap@gmail.com"));
        PlayerManager.getInstance().getCurrentUser().getInventory().addItem(new Healthpack(10).getName());
    }

    public static void allowPermissionsIfNeeded(String permissionNeeded) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasNeededPermission(permissionNeeded)) {
                sleep();
                UiDevice device = UiDevice.getInstance(getInstrumentation());
                UiObject allowPermissions = device.findObject(new UiSelector()
                        .clickable(true)
                        .checkable(false)
                        .index(GRANT_BUTTON_INDEX));
                if (allowPermissions.exists()) {
                    allowPermissions.click();
                }
            }
        } catch (UiObjectNotFoundException e) {
            System.out.println("There is no permissions dialog to interact with");
        }
    }

    public static void denyPermissionsIfNeeded(String permissionNeeded) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasNeededPermission(permissionNeeded)) {
                sleep();
                UiDevice device = UiDevice.getInstance(getInstrumentation());
                UiObject allowPermissions = device.findObject(new UiSelector()
                        .clickable(true)
                        .checkable(false)
                        .index(DENY_BUTTON_INDEX));
                if (allowPermissions.exists()) {
                    allowPermissions.click();
                }
            }
        } catch (UiObjectNotFoundException e) {
            System.out.println("There is no permissions dialog to interact with");
        }
    }

    private static boolean hasNeededPermission(String permissionNeeded) {
        Context context = getInstrumentation().getContext();
        int permissionStatus = ContextCompat.checkSelfPermission(context, permissionNeeded);
        return permissionStatus == PackageManager.PERMISSION_GRANTED;
    }

    private static void sleep() {
        try {
            Thread.sleep(MapsActivityTest.PERMISSIONS_DIALOG_DELAY);
        } catch (InterruptedException e) {
            throw new RuntimeException("Cannot execute Thread.sleep()");
        }
    }

    @Rule
    public final ActivityTestRule<MapsActivity> mActivityRule =
            new ActivityTestRule<MapsActivity>(MapsActivity.class){
                @Override
                protected void beforeActivityLaunched() {
                    DependencyFactory.setTestMode(true);
                    DependencyFactory.setAuthenticationAPI(new MockAuthenticationAPI(new HashMap<>(), "testMap@gmail.com"));
                    HashMap<String, UserForFirebase> map = new HashMap<>();
                    map.put("testMap@gmail.com", new UserForFirebase("testMap@gmail.com", "testMap", 0));
                    DependencyFactory.setCommonDatabaseAPI(new CommonMockDatabaseAPI(map));                }
            };

    @After
    public void tearDown(){
        DependencyFactory.setTestMode(false);
        DependencyFactory.setAuthenticationAPI(null);
        DependencyFactory.setCommonDatabaseAPI(null);
    }

    @Test
    public void denyRequestPermissionWorks() {
        denyPermissionsIfNeeded("ACCESS_FINE_LOCATION");
        onView(withId(R.id.recenter)).perform(click());
        sleep();
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    @Test
    public void myPositionButtonWorks() {
        PlayerManager.getInstance().removeAll(); // To remove
        allowPermissionsIfNeeded("ACCESS_FINE_LOCATION");
        onView(withId(R.id.recenter)).perform(click());
        sleep();
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    @Test
    public void inventoryOpens() {
        onView(withId(R.id.button_inventory)).perform(click());
        onView(withId(R.id.fragment_inventory_container)).check(matches(isDisplayed()));
    }

    @Test
    public void leaderboardOpens() throws InterruptedException {
        onView(withId(R.id.button_leaderboard)).perform(click());
        Thread.sleep(2000);
    }

    @Test
    public void moveCameraWorks() {
        allowPermissionsIfNeeded("ACCESS_FINE_LOCATION");
        onView(withId(R.id.recenter)).perform(click());
        allowPermissionsIfNeeded("ACCESS_FINE_LOCATION");
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }
}