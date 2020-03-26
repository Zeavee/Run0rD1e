package ch.epfl.sdp;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import ch.epfl.sdp.social.ChatActivity;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.startsWith;

public class ChatActivityTest {

    @Rule
    public final ActivityTestRule<ChatActivity> mActivityRule =
            new ActivityTestRule<>(ChatActivity.class);

    @Test
    public void messageIsSent() {
        onView(withId(R.id.messageField)).perform(typeText("Hello"));
        onView(withId(R.id.sendMessageButton)).perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.messages_view))
                .atPosition(0)
                .check(matches(withText(startsWith("Hello"))));
    }
}
