package ch.epfl.sdp.SocialTests;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.authentication.MockAuthenticationAPI;
import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.social.Conversation.SocialRepository;
import ch.epfl.sdp.social.FriendsListActivity;
import ch.epfl.sdp.social.socialDatabase.Chat;
import ch.epfl.sdp.social.socialDatabase.User;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sdp.SocialTests.ChildParentMatcher.childAtPosition;
import static ch.epfl.sdp.SocialTests.ChildParentMatcher.matchesChildWithTextAtDepth4;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


/**
 * @brief tests if message "thanks" sent to stupid2@gmail.com is displayed on the ChatActivity's view
 * for this test to work, we must first register them as friends inside the SQLite database
 */

public class ChatActivityTest {

    private String currentEmail;

    @Rule
    public ActivityTestRule<FriendsListActivity> mActivityTestRule = new ActivityTestRule<FriendsListActivity>(FriendsListActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            AppContainer appContainer = ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer;
            appContainer.authenticationAPI = new MockAuthenticationAPI(null, "amro.abdrabo@gmail.com");
            currentEmail = appContainer.authenticationAPI.getCurrentUserEmail();

            // IMPORTANT: social database must be pre-populated for this test to work, otherwise stupid3 will not show up as a friend in FriendsListActivity
            User cur_user = new User(currentEmail);
            User friend_user = new User("stupid3@gmail.com");

            // testRepo is the social database controller (router)
            SocialRepository testRepo = SocialRepository.getInstance();
            SocialRepository.currentEmail = currentEmail;
            testRepo.addUser(cur_user);
            testRepo.addUser(friend_user);
            testRepo.addChat(new Chat(cur_user.getEmail(), friend_user.getEmail()));
            testRepo.addChat(new Chat(friend_user.getEmail(), cur_user.getEmail()));
            testRepo.addFriends(friend_user, cur_user);
        }
    };

    // click on the chat button
    public void step1() {
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
    public void step2() {
        ViewInteraction appCompatEditText3 = matchesChildAtDepthFour(R.id.messageField, 0);
        appCompatEditText3.perform(replaceText("thanks"), closeSoftKeyboard());
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
    public void step3() {
        ViewInteraction appCompatImageButton = matchesChildAtDepthFour(R.id.sendMessageButton, 1);
        appCompatImageButton.perform(click());
    }

    // check that the message "thanks" is actually displayed on the screen
    public void step4() {
        ViewInteraction textView = matchesChildWithTextAtDepth4("thanks", R.id.message_body, R.id.messages_view, 0);
        textView.check(matches(withText("thanks")));
    }

    @Test
    public void chatActivityTest() {
        step1();
        step2();
        step3();
        step4();
    }

}