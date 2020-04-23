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

import ch.epfl.sdp.MainActivity;
import ch.epfl.sdp.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sdp.SocialTests.ChildParentMatcher.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FriendsListActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    public void step1(){
        //************************************ Click on "Friends" to go the social/chat feature *******************************//
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.friendsButton), withText("Friends"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(android.R.id.content),
                                                childAtPosition(
                                                        allOf(withId(R.id.decor_content_parent),
                                                                childAtPosition(
                                                                        withClassName(is("android.widget.FrameLayout")),
                                                                        0)),
                                                        0)),
                                        0),
                                11),
                        isDisplayed()));
        appCompatButton2.perform(click());
    }
    public void step2(){
        //********************************* Check that "Add Friends" (to search for users) is displayed ************************//
        ViewInteraction button = onView(
                allOf(withId(R.id.button_add_friends),
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
        button.check(matches(isDisplayed()));
    }
    public void step3(){

        // ******************************* Check that the scroll view showing list of friends is displayed ********************//
        ViewInteraction scrollView = onView(withId(R.id.nestedScrollView));
        scrollView.check(matches(isDisplayed()));
    }
    // auto-generated by test recorder
    @Test
    public void friendsListActivityTest() {
        step1();
        step2();
        step3();
    }

}
