package ch.epfl.sdp.socialTest;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sdp.R;
import ch.epfl.sdp.social.AddFriendsActivity;
import ch.epfl.sdp.social.ChatActivity;
import ch.epfl.sdp.social.FriendsListActivity;
import ch.epfl.sdp.social.RecyclerQueryAdapter;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
// WARNING: Espresso Test Recorder was paused during recording.
// The generated test may be missing actions which might lead to unexpected behavior.
public class ChatActivityTest {

    @Rule
    public ActivityTestRule<FriendsListActivity> mActivityTestRule = new ActivityTestRule<>(FriendsListActivity.class);

    @Test
    public void chatActivityTest2() throws InterruptedException {



        AddFriendsActivity.setAdapter(new RecyclerQueryAdapter(new MockFriendsFetcher()));
        ChatActivity.setRemoteToSQLiteAdapter(new MockServerToSQLiteAdapter().getInstance());
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button_add_friends), withText("Add Friends"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar_layout), withContentDescription("FriendsListActivity"),
                                        childAtPosition(
                                                allOf(withId(R.id.app_bar),
                                                        childAtPosition(
                                                                childAtPosition(
                                                                        withId(android.R.id.content),
                                                                        0),
                                                                0)),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_search), withContentDescription("Search"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.action_bar),
                                                childAtPosition(
                                                        allOf(withId(R.id.action_bar_container),
                                                                childAtPosition(
                                                                        withId(R.id.decor_content_parent),
                                                                        1)),
                                                        0)),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction searchAutoComplete = onView(
                allOf(withId(R.id.search_src_text),
                        childAtPosition(
                                allOf(withId(R.id.search_plate),
                                        childAtPosition(
                                                allOf(withId(R.id.search_edit_frame),
                                                        childAtPosition(
                                                                allOf(withId(R.id.search_bar),
                                                                        childAtPosition(
                                                                                withId(R.id.action_search),
                                                                                0)),
                                                                2)),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("stupid2"), closeSoftKeyboard());

        Thread.sleep(3000);

        ViewInteraction constraintLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.recyclerQueryFriends),
                                childAtPosition(
                                        childAtPosition(
                                                allOf(withId(android.R.id.content),
                                                        childAtPosition(
                                                                withId(R.id.decor_content_parent),
                                                                0)),
                                                0),
                                        0)),
                        0),
                        isDisplayed()));
        constraintLayout.perform(click());

        pressBack();
        pressBack();
        pressBack();

        Thread.sleep(5000);
        /*ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.chat_button), withText("chat"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.friends_recyclerview),
                                                childAtPosition(
                                                        childAtPosition(
                                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                                1),
                                                        0)),
                                        0),
                                2),
                        isDisplayed()));*/
        ViewInteraction appCompatButton4 = onView(withId(R.id.chat_button));
        appCompatButton4.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.messageField),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                allOf(withId(android.R.id.content),
                                                        childAtPosition(
                                                                withId(R.id.decor_content_parent),
                                                                0)),
                                                0),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("hi"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.sendMessageButton),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                allOf(withId(android.R.id.content),
                                                        childAtPosition(
                                                                withId(R.id.decor_content_parent),
                                                                0)),
                                                0),
                                        1),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        pressBack();

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.chat_button), withText("chat"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.friends_recyclerview),
                                                childAtPosition(
                                                        childAtPosition(
                                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                                1),
                                                        0)),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.message_body), withText("hi"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.messages_view),
                                                childAtPosition(
                                                        childAtPosition(
                                                                withId(android.R.id.content),
                                                                0),
                                                        0)),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("hi")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
