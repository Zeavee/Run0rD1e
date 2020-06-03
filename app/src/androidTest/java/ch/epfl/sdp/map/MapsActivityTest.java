package ch.epfl.sdp.map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.authentication.MockAuthenticationAPI;
import ch.epfl.sdp.database.firebase.ClientMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.CommonMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.ServerMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.AreaShrinker;
import ch.epfl.sdp.geometry.CircleArea;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.Healthpack;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
public class MapsActivityTest {
    private static final int PERMISSIONS_DIALOG_DELAY = 3000;
    public static final int GRANT_BUTTON_INDEX = 0;
    public static final int DENY_BUTTON_INDEX = 1;

    private final HashMap<String, UserForFirebase> map = new HashMap<>();

    public static void permissionsIfNeeded(String permissionNeeded, int button) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasNeededPermission(permissionNeeded)) {
                sleep();
                UiDevice device = UiDevice.getInstance(getInstrumentation());
                UiObject allowPermissions = device.findObject(new UiSelector()
                        .clickable(true)
                        .checkable(false)
                        .index(button));
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
            new ActivityTestRule<MapsActivity>(MapsActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    Game.getInstance().destroyGame();
                    Game.getInstance().clearGame();
                    PlayerManager.getInstance().clear();
                    PlayerManager.getInstance().setCurrentUser(new Player(40, 50, 10, "testMap", "testMap@gmail.com"));

                    map.put("testMap@gmail.com", new UserForFirebase("testMap@gmail.com", "testMap", 0));
                    AppContainer appContainer = ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer;
                    appContainer.authenticationAPI = new MockAuthenticationAPI(new HashMap<>(), "testMap@gmail.com");

                    List<UserForFirebase> userForFirebaseList = new ArrayList<>();
                    userForFirebaseList.add(new UserForFirebase("leader0@gmail.com", "leader0", 100));
                    userForFirebaseList.add(new UserForFirebase("leader1@gmail.com", "leader1", 90));
                    userForFirebaseList.add(new UserForFirebase("leader2@gmail.com", "leader2", 80));

                    appContainer.commonDatabaseAPI = new CommonMockDatabaseAPI(map, userForFirebaseList);
                    appContainer.serverDatabaseAPI = new ServerMockDatabaseAPI();
                    appContainer.clientDatabaseAPI = new ClientMockDatabaseAPI();
                    Game.getInstance().areaShrinker = new AreaShrinker(5000, 5000, 0.75);
                }

                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra("playMode", "multi-player");
                    return intent;
                }
            };

    @Before
    public void setup() {
        mActivityRule.getActivity().setLocationFinder(() -> new GeoPoint(40, 50));
    }

    @Test
    public void denyRequestPermissionWorks() {
        permissionsIfNeeded("ACCESS_FINE_LOCATION", DENY_BUTTON_INDEX);
        onView(ViewMatchers.withId(R.id.recenter)).perform(click());
        sleep();
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }


    @Test
    public void inventoryOpensAndCloses() {
        PlayerManager.getInstance().getCurrentUser().getInventory().addItem(new Healthpack(10).getName());
        testFragmentOpensAndCloses(R.id.button_inventory, R.id.items_recyclerview);
    }

    @Test
    public void weatherOpensAndCloses() {
        testFragmentOpensAndCloses(R.id.button_weather, R.id.temp);
    }

    @Test
    public void leaderboardOpensAndCloses() {
        testFragmentOpensAndCloses(R.id.button_leaderboard, R.id.ingame_leaderboard_recyclerview);
    }

    private void testFragmentOpensAndCloses(int button, int view) {
        permissionsIfNeeded("ACCESS_FINE_LOCATION", GRANT_BUTTON_INDEX);
        onView(withId(view)).check(doesNotExist());
        testButtonWorks(button, view);
        onView(withId(button)).perform(click());
        onView(withId(view)).check(doesNotExist());
    }

    private void testButtonWorks(int button, int view) {
        permissionsIfNeeded("ACCESS_FINE_LOCATION", GRANT_BUTTON_INDEX);
        onView(withId(button)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(view)).check(matches(isDisplayed()));
    }

    @Test
    public void areaShrinkerWorks() throws InterruptedException {
        permissionsIfNeeded("ACCESS_FINE_LOCATION", GRANT_BUTTON_INDEX);
        GeoPoint center = new GeoPoint(40, 50);
        CircleArea gameArea = new CircleArea(1000, center);
        Game.getInstance().areaShrinker.setGameArea(gameArea);
        Thread.sleep(10000);
        onView(withId(R.id.timerShrinking)).check(matches(withText(containsString("0:"))));
    }
}