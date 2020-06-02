package ch.epfl.sdp;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sdp.database.firebase.CommonMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.entityForFirebase.UserForFirebase;
import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.leaderBoard.GeneralLeaderBoardActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class GeneralLeaderBoardActivityTest {
    @Rule
    public final ActivityTestRule<GeneralLeaderBoardActivity> mActivityRule =
            new ActivityTestRule<GeneralLeaderBoardActivity>(GeneralLeaderBoardActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    HashMap<String, UserForFirebase> map = new HashMap<>();
                    map.put("testMap@gmail.com", new UserForFirebase("testMap@gmail.com", "testMap", 0));

                    List<UserForFirebase> userForFirebaseList = new ArrayList<>();
                    userForFirebaseList.add(new UserForFirebase("leader0@gmail.com", "leader0", 100));
                    userForFirebaseList.add(new UserForFirebase("leader1@gmail.com", "leader1", 90));
                    userForFirebaseList.add(new UserForFirebase("leader2@gmail.com", "leader2", 80));
                    userForFirebaseList.add(new UserForFirebase("leader3@gmail.com", "leader3", 70));
                    AppContainer appContainer = ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer;
                    appContainer.commonDatabaseAPI = new CommonMockDatabaseAPI(map, userForFirebaseList);
                }
            };

    @Test
    public void onCreateTest() {
        onView(withId(R.id.recycler_view)).perform();
        onView(withId(R.id.iv_champion1)).check(matches(isDisplayed()));
    }
}
