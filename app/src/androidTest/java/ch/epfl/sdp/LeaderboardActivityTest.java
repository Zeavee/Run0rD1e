package ch.epfl.sdp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sdp.leaderboard.LeaderboardActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LeaderboardActivityTest {
    @Rule
    public final ActivityTestRule<LeaderboardActivity> mActivityRule =
            new ActivityTestRule<>(LeaderboardActivity.class);

    @Test
    public void onCreate() {
        onView(withId(R.id.recycler_view)).perform();
    }
}