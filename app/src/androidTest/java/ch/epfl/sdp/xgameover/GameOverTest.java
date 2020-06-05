package ch.epfl.sdp.xgameover;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.firebase.CommonMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.ServerMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.entityForFirebase.UserForFirebase;
import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.Server;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;
import ch.epfl.sdp.utils.MockMap;

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
    public final ActivityTestRule<MapsActivity> mActivityTestRule = new ActivityTestRule<MapsActivity>(MapsActivity.class) {

        @Override
        public void beforeActivityLaunched() {
            // important to have player away from market otherwise it is the market that will open
            Player amro = new Player(6.14, 47.22, 100, "amroa", "amro@gmail.com", false);
            amro.setHealthPoints(100);
            PlayerManager.getInstance().setCurrentUser(amro);

            Player placeholder = new Player("placeholder", "placeholder@placeholder.com");
            PlayerManager.getInstance().addPlayer(placeholder);

            MockMap mockMap = new MockMap();
            Game.getInstance().setMapApi(mockMap);

            HashMap<String, UserForFirebase> userData = new HashMap<>();
            List<UserForFirebase> userForFirebaseList = new ArrayList<>();

            UserForFirebase amroForFirebase = new UserForFirebase(amro.getEmail(), amro.getUsername(), 100);

            userData.put(amro.getEmail(), amroForFirebase);
            userForFirebaseList.add(amroForFirebase);

            AppContainer appContainer = ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer;

            appContainer.commonDatabaseAPI = new CommonMockDatabaseAPI(userData, userForFirebaseList);
            appContainer.serverDatabaseAPI = new ServerMockDatabaseAPI();

            Game.getInstance().startGameController = new Server(appContainer.serverDatabaseAPI, appContainer.commonDatabaseAPI, () -> {mActivityTestRule.getActivity().endGame();});
        }

        // start the game engine MANUALLY
        @Override
        public void afterActivityLaunched() {
            getActivity().setLocationFinder(() -> new GeoPoint(6.14, 47.22));
            Game.getInstance().initGame();
        }

        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            intent.putExtra("playMode", "multi-player");
            return intent;
        }
    };

    // check "Game Ovr" is displayed
    @Test
    public void serverLosesIfDead() {
        checkIfTextIsDisplayedAfterGameOver(PlayerManager.getInstance().getCurrentUser());
    }

    private void checkIfTextIsDisplayedAfterGameOver(Player player) {
        player.setHealthPoints(0);
        // wait a moment for the splash screen to be intended
        while (!mActivityTestRule.getActivity().flagGameOver) {
            ((Server) Game.getInstance().startGameController).update();
        }
        ViewInteraction textView = onView(withId(R.id.gameOverText));
        textView.check(matches(withText("Game 0vr")));
        onView(withId(R.id.backFromGameOver)).perform(click());
        onView(withId(R.id.solo)).check(matches(isDisplayed()));
    }
}
