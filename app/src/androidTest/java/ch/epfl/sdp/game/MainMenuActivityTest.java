package ch.epfl.sdp.game;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.authentication.MockAuthenticationAPI;
import ch.epfl.sdp.database.firebase.ClientMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.CommonMockDatabaseAPI;
import ch.epfl.sdp.utils.AppContainer;
import ch.epfl.sdp.utils.MyApplication;
import ch.epfl.sdp.entities.player.Player;
import ch.epfl.sdp.entities.player.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.ui.game.MainMenuActivity;
import ch.epfl.sdp.utils.MockMap;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static ch.epfl.sdp.map.MapsActivityTest.GRANT_BUTTON_INDEX;
import static ch.epfl.sdp.map.MapsActivityTest.permissionsIfNeeded;

@RunWith(AndroidJUnit4.class)
public class MainMenuActivityTest {
    @Rule
    public final ActivityTestRule<MainMenuActivity> mActivityRule =
            new ActivityTestRule<MainMenuActivity>(MainMenuActivity.class) {
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
        Game.getInstance().setMapApi(new MockMap());
        PlayerManager.getInstance().setCurrentUser(new Player("test", "test@gmail.com"));
    }

    @Test
    public void rulesOpens() {
        onView(ViewMatchers.withId(R.id.rulesButton)).perform(click());
        onView(withId(R.id.titleRules)).check(matches(isDisplayed()));
    }

    @Test
    public void friendsListOpens() {
        onView(withId(R.id.friendsButton)).perform(click());
        onView(withId(R.id.app_bar)).check(matches(isDisplayed()));
    }

    @Test
    public void mapsOpens() {
        testButtonWorks(R.id.multi);
    }

    @Test
    public void soloOpens() {
        testButtonWorks(R.id.solo);
    }

    private void testButtonWorks(int button) {
        onView(withId(button)).perform(click());
        permissionsIfNeeded("ACCESS_FINE_LOCATION", GRANT_BUTTON_INDEX);
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    @Test
    public void leaderboardOpens() {
        onView(withId(R.id.generalLeaderBoard)).perform(click());
        onView(withId(R.id.layout_champion)).check(matches(isDisplayed()));
    }

    @Test
    public void rulesPageOneOpens() {
        onView(withId(R.id.rulesButton)).perform(click());
        onView(withId(R.id.titleRules)).check(matches(isDisplayed()));
        onView(withId(R.id.enemyRule)).check(matches(isDisplayed()));
    }

    @Test
    public void logoutWorks() {
        onView(withId(R.id.logoutBt)).perform(click());
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
    }
}