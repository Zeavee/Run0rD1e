package ch.epfl.sdp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sdp.MapsActivityTest.allowPermissionsIfNeeded;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

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
        allowPermissionsIfNeeded("ACCESS_FINE_LOCATION");
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    @Test
    public void onCreate() {
        onView(withId(R.id.mainGoButton)).perform(click());

        // This view is in a different Activity, no need to tell Espresso.
        onView(withId(R.id.username_text)).check(matches(withText("admin")));
    }

    @Test
    public void InventoryOpens() {
        onView(withId(R.id.inventory)).perform(click());
    }

   /* @Test
    public void leaderboardOpens() {
        onView(withId(R.id.leaderboard)).perform(click());
    }*/
}