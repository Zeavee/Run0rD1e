package ch.epfl.sdp;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import ch.epfl.sdp.database.firebase.CommonMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.leaderboard.LeaderboardActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LeaderboardActivityTest {
    @Rule
    public final ActivityTestRule<LeaderboardActivity> mActivityRule =
            new ActivityTestRule<LeaderboardActivity>(LeaderboardActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    HashMap<String, UserForFirebase> map = new HashMap<>();
                    map.put("testMap@gmail.com", new UserForFirebase("testMap@gmail.com", "testMap", 0));
                    AppContainer appContainer = ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer;
                    appContainer.commonDatabaseAPI = new CommonMockDatabaseAPI(map);

                }
            };

    @Test
    public void onCreateTest() {
        onView(withId(R.id.iv_champion1)).check(matches(isDisplayed()));
    }
}
