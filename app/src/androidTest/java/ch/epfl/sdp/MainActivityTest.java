package ch.epfl.sdp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.utils.MockAuthenticationAPI;
import ch.epfl.sdp.utils.MockMapApi;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup(){
        Game.getInstance().setMapApi(new MockMapApi());
        String currentEmail = "test@gmail.com";
        PlayerManager.setCurrentUser(new Player("test", "test@gmail.com"));
        AppContainer appContainer = ((MyApplication) mActivityRule.getActivity().getApplication()).appContainer;
        appContainer.authenticationAPI = new MockAuthenticationAPI(null, currentEmail);
    }

    @After
    public void teardown(){
        PlayerManager.setCurrentUser(null);
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

    /*@Test
    public void mapsOpens() {
        onView(withId(R.id.mapButton)).perform(click());
        allowPermissionsIfNeeded("ACCESS_FINE_LOCATION");
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }*/

    @Test
    public void leaderboardOpens() {
        onView(withId(R.id.leaderboard)).perform(click());
    }
}