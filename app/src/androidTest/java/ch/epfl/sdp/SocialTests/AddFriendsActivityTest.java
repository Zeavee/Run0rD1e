package ch.epfl.sdp.SocialTests;

import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sdp.R;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.social.AddFriendsActivity;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sdp.SocialTests.ChildParentMatcher.childAtPosition;
import static ch.epfl.sdp.SocialTests.ChildParentMatcher.matchesChildWithTextAtDepth4;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddFriendsActivityTest {

    @Rule
    public ActivityTestRule<AddFriendsActivity> mActivityTestRule = new ActivityTestRule<AddFriendsActivity>(AddFriendsActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            Log.d("addFriendTest", " beforeActivityLaunched");
            ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer.remoteUserFetch = new MockFriendsFetcher();
        }
    };

    @Before
    public void setup() {
        PlayerManager.getInstance().setCurrentUser(new Player("stupid0", "stupid0@gmail.com"));
    }

    public void step1() {
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

    public void step2() {
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

    public void step3() {
        // ***************** Check that first search result, stupid0@gmail, appears on the search result list *************** //
        ViewInteraction viewGroup = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.recyclerQueryFriends)), 0),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

    }

    public void step4() {
        // *********************** Check that the first search result has text stupid0@gmail.com *************************** //
        ViewInteraction textView = matchesChildWithTextAtDepth4("stupid0@gmail.com", R.id.textViewEmail, R.id.recyclerQueryFriends, 2);
        textView.check(matches(withText("stupid0@gmail.com")));
    }

    @Test
    public void addFriendsActivityTest() throws InterruptedException {

        step1();
        closeSoftKeyboard();
        Thread.sleep(1000); // for travis absolutely NECESSARY
        step2();
        step3();
        step4();
    }


}
