package ch.epfl.sdp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MapsTest {
    @Rule
    public final ActivityTestRule<MapsActivity> mActivityRule =
            new ActivityTestRule<>(MapsActivity.class);
    @Test
    public void testCanGreetUsers() {

        // onView(withId(R.id.greetingMessage)).check(matches(withText("Hello from my unit test!")));
    }
}