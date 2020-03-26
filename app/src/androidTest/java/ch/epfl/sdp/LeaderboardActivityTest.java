package ch.epfl.sdp;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class LeaderboardActivityTest {
    @Rule
    public final ActivityTestRule<LeaderboardActivity> mActivityRule =
            new ActivityTestRule<>(LeaderboardActivity.class);

    @Test
    public void onCreate() {
        onView(withId(R.id.tv_username1)).check(matches(isDisplayed()));
    }
}