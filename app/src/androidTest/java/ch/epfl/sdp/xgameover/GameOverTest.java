package ch.epfl.sdp.xgameover;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.firebase.CommonMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.ServerMockDatabaseAPI;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;
import ch.epfl.sdp.market.Market;
import ch.epfl.sdp.utils.MockMapApi;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GameOverTest {
    @Rule
    public ActivityTestRule<MapsActivity> mActivityTestRule = new ActivityTestRule<MapsActivity>(MapsActivity.class) {

        @Override
        public void beforeActivityLaunched() {
            // important to have player away from market otherwise it is the market that will open
            Player amro = new Player(6.14, 47.22, 100, "amroa", "amro@gmail.com");
            amro.setHealthPoints(100);
            PlayerManager.getInstance().setCurrentUser(amro);
            MockMapApi mockMapApi = new MockMapApi();
            Game.getInstance().setMapApi(mockMapApi);
            ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer.commonDatabaseAPI = new CommonMockDatabaseAPI(new HashMap<>(), new ArrayList<>());
            ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer.serverDatabaseAPI = new ServerMockDatabaseAPI();
        }

        // start the game engine MANUALLY
        @Override
        public void afterActivityLaunched() {
            getActivity().setLocationFinder(() -> new GeoPoint(6.14, 47.22));
            Game.getInstance().addToDisplayList(new Market(new GeoPoint(6.14, 46.22)));
            Game.getInstance().initGame();
            PlayerManager.getInstance().getCurrentUser().setHealthPoints(0);
        }
    };

    // check "gameOvr" is displayed
    @Test
    public void test() throws InterruptedException {
        // wait a moment for the splash screen to be intended
        while (!mActivityTestRule.getActivity().flagGameOver) ;
        ViewInteraction textView = onView(withId(R.id.gameovr));
        textView.check(matches(withText("game0vr")));
        onView(withId(R.id.backFromGameOverButton)).perform(click());
        onView(withId(R.id.solo)).check(matches(isDisplayed()));
    }
}
