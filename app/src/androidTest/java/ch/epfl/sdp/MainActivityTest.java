package ch.epfl.sdp;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.database.authentication.MockAuthenticationAPI;
import ch.epfl.sdp.database.firebase.ClientMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.CommonMockDatabaseAPI;
import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.map.MapsActivity;
import ch.epfl.sdp.map.MapsActivityTest;
import ch.epfl.sdp.utils.MockMapApi;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static ch.epfl.sdp.map.MapsActivityTest.GRANT_BUTTON_INDEX;
import static ch.epfl.sdp.map.MapsActivityTest.permissionsIfNeeded;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    String currentEmail = "test@gmail.com";
                    AppContainer appContainer = ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer;
                    appContainer.authenticationAPI = new MockAuthenticationAPI(null, currentEmail);
                    appContainer.clientDatabaseAPI = new ClientMockDatabaseAPI();
                    appContainer.commonDatabaseAPI = new CommonMockDatabaseAPI(new HashMap<>(), new ArrayList<>());
                    PlayerManager.getInstance().setCurrentUser(new Player(40, 50, 100, "test", "test@test.com"));
                }
            };

    @Before
    public void setup() {
        Game.getInstance().setMapApi(new MockMapApi());
        PlayerManager.getInstance().setCurrentUser(new Player("test", "test@gmail.com"));
    }

    @After
    public void teardown() {
    }

    @Test
    public void rulesOpens() {
        onView(withId(R.id.rulesButton)).perform(click());
        onView(withId(R.id.titleRules)).check(matches(isDisplayed()));
    }

    @Test
    public void friendsListOpens() {
        onView(withId(R.id.friendsButton)).perform(click());
        onView(withId(R.id.app_bar)).check(matches(isDisplayed()));
    }

    @Test
    public void mapsOpens() {
        onView(withId(R.id.mapButton)).perform(click());
        permissionsIfNeeded("ACCESS_FINE_LOCATION", GRANT_BUTTON_INDEX);
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    @Test
    public void soloOpens() {
        onView(withId(R.id.solo)).perform(click());
        permissionsIfNeeded("ACCESS_FINE_LOCATION", GRANT_BUTTON_INDEX);
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    @Test
    public void leaderboardOpens() {
        onView(withId(R.id.leaderboard)).perform(click());
        onView(withId(R.id.layout_champion)).check(matches(isDisplayed()));
    }
}