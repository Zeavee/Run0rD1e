package ch.epfl.sdp;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import ch.epfl.sdp.social.FriendsListActivity;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.startsWith;

public class FriendsListActivityTest {
    @Rule
    public final ActivityTestRule<FriendsListActivity> mActivityRule =
            new ActivityTestRule<>(FriendsListActivity.class);

    @Test
    public void chatOpensAndMessageIsSent() {
        onView(withId(R.id.friends_recyclerview)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.chat_button)));
        onView(withId(R.id.sendMessageButton)).check(matches(isDisplayed()));
        onView(withId(R.id.messageField)).perform(typeText("Hello"));
        onView(withId(R.id.sendMessageButton)).perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.messages_view))
                .atPosition(0)
                .onChildView(withId(R.id.message_body))
                .check(matches(withText(startsWith("Hello"))));
    }


    public static class MyViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }
}
