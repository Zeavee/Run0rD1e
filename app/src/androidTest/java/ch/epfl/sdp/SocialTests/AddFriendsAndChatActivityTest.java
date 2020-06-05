package ch.epfl.sdp.SocialTests;

import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.authentication.MockAuthenticationAPI;
import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.social.FriendsListActivity;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sdp.SocialTests.ChildParentMatcher.childAtPosition;
import static ch.epfl.sdp.SocialTests.ChildParentMatcher.matchesChildWithTextAtDepth4;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddFriendsAndChatActivityTest {

    @Rule
    public ActivityTestRule<FriendsListActivity> mActivityTestRule = new ActivityTestRule<FriendsListActivity>(FriendsListActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            Log.d("addFriendTest", " beforeActivityLaunched");
            ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer.remoteUserFetch = new MockFriendsFetcher();

            AppContainer appContainer = ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer;
            appContainer.authenticationAPI = new MockAuthenticationAPI(null, "stupid0@gmail.com");
        }
    };

    @Before
    public void setup() {
        PlayerManager.getInstance().setCurrentUser(new Player("stupid0", "stupid0@gmail.com"));
    }

    public void friendsStep1() {
        // *********************************** Clicks on the search icon ******************************************* //
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
    }

    public void friendsStep2() {
        // *********************** Clicks on the third search result to add stupid3 as friend *************************** //
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
                        3),
                        isDisplayed()));
        constraintLayout.perform(click());
    }

    public void friendsStep3() {
        // ***************** Check that first search result, stupid0@gmail, appears on the search result list *************** //
        ViewInteraction viewGroup = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.recyclerQueryFriends)), 0),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

    }

    public void friendsStep4() {
        // *********************** Check that the first search result has text stupid0@gmail.com *************************** //
        ViewInteraction textView = matchesChildWithTextAtDepth4("stupid0@gmail.com", R.id.textViewEmail, R.id.recyclerQueryFriends, 2);
        textView.check(matches(withText("stupid0@gmail.com")));
    }

    @Test
    public void addFriendsAndChatActivityTest() throws InterruptedException {
        onView(withId(R.id.button_add_friends)).perform(click());
        friendsStep1();
        closeSoftKeyboard();
        Thread.sleep(2000); // for travis absolutely NECESSARY
        friendsStep2();
        friendsStep2();
        friendsStep4();
        onView(withId(R.id.addFriendsBackButton)).perform(click());
        Thread.sleep(2000);
        chatStep1();
        chatStep2();
        chatStep3();
        chatStep4();
    }

    // click on the chat button
    public void chatStep1() {
        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.chat_button), withText("chat"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.friends_recyclerview),
                                                childAtPosition(
                                                        allOf(withId(R.id.nestedScrollView),
                                                                childAtPosition(
                                                                        withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                                        1)),
                                                        0)),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton4.perform(click());
    }

    // type text "thanks"
    public void chatStep2() {
        ViewInteraction appCompatEditText3 = matchesChildAtDepthFour(R.id.messageField, 0);
        appCompatEditText3.perform(replaceText("thanks"), ViewActions.closeSoftKeyboard());
    }

    private ViewInteraction matchesChildAtDepthFour(int childId, int position) {
        return onView(
                allOf(withId(childId),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                allOf(withId(android.R.id.content),
                                                        childAtPosition(
                                                                withId(R.id.decor_content_parent),
                                                                0)),
                                                0),
                                        1),
                                position),
                        isDisplayed()));
    }

    // click on the send button to send "thanks" message
    public void chatStep3() {
        ViewInteraction appCompatImageButton = matchesChildAtDepthFour(R.id.sendMessageButton, 1);
        appCompatImageButton.perform(click());
    }

    // check that the message "thanks" is actually displayed on the screen
    public void chatStep4() {
        ViewInteraction textView = matchesChildWithTextAtDepth4("thanks", R.id.message_body, R.id.messages_view, 0);
        textView.check(matches(withText("thanks")));
    }
}
