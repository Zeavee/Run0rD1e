package ch.epfl.sdp.SocialTests;

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
import ch.epfl.sdp.dependencies.DependencyProvider;
import ch.epfl.sdp.social.AddFriendsActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddFriendsActivityTest {

    @Rule
    public ActivityTestRule<AddFriendsActivity> mActivityTestRule =  new ActivityTestRule<AddFriendsActivity>(AddFriendsActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            DependencyProvider.remoteUserFetch = new MockFriendsFetcher();
        }
    };

    @Test
    public void addFriendsActivityTest() {

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

        // ***************** Check that first search result, stupid0@gmail, appears on the search result list *************** //
        ViewInteraction viewGroup = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.recyclerQueryFriends)), 0),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));


        // *********************** Check that the first search result has text stupid0@gmail.com *************************** //
        ViewInteraction textView = onView(
                allOf(withId(R.id.textViewEmail), withText("stupid0@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.recyclerQueryFriends),
                                                childAtPosition(
                                                        childAtPosition(
                                                                withId(android.R.id.content),
                                                                0),
                                                        0)),
                                        0),
                                2),
                        isDisplayed()));
        textView.check(matches(withText("stupid0@gmail.com")));
    }

    // auto-generated by espresso test recorder
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
