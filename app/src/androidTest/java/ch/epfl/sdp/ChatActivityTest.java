package ch.epfl.sdp;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewAssertion;
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
import ch.epfl.sdp.login.LoginFormActivity;

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
public class ChatActivityTest {

    @Rule
    public ActivityTestRule<LoginFormActivity> mActivityTestRule = new ActivityTestRule<>(LoginFormActivity.class);

    public void testPhase1Login()
    {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.emaillog), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("amro.abdrabo@gmail.com"), closeSoftKeyboard());
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.passwordlog), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("password"), closeSoftKeyboard());
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.loginButton), withText("Login"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 0),
                        isDisplayed()));
        appCompatButton.perform(click());
    }

    public void testPhase2FriendList()
    {
        try{Thread.sleep(3000);} catch (Exception e){}
        ViewInteraction button = onView(withId(R.id.friendsButton));
        button.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.item_number), withText("shaima@abc.com"), isDisplayed()));
        textView.check(matches(withText("shaima@abc.com")));

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.toolbar_layout), withContentDescription("FriendsListActivity"), isDisplayed()));
        frameLayout.check(matches(isDisplayed()));
    }

    public void chatWithShaima()
    {
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.chat_button), withText("chat"), childAtPosition(childAtPosition(withId(R.id.friends_recyclerview),
                        0), 2), isDisplayed()));
        appCompatButton3.perform(click());
        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.messageField), childAtPosition(childAtPosition(withClassName(is("android.widget.LinearLayout")),
                        1), 0), isDisplayed()));
        appCompatEditText3.perform(replaceText("hi"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.sendMessageButton), childAtPosition(childAtPosition(withClassName(is("android.widget.LinearLayout")),
                                        1), 1), isDisplayed()));
        appCompatImageButton.perform(click());
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.message_body), withText("hi"), isDisplayed()));
        textView2.check(matches(withText("hi")));
    }

    public void chatWithShaimaAgain() {
        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.chat_button), withText("chat"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.friends_recyclerview),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton4.perform(click());
        pressBack();
    }

    @Test
    public void chatActivityTest() {
        testPhase1Login();
       /* ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.emaillog),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("amro.abdrabo@gmail.com"), closeSoftKeyboard());*/

        /*ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.passwordlog),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("password"), closeSoftKeyboard());*/

        /*ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.loginButton), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());*/

        //Phase 1 done

       /* try{Thread.sleep(3000);} catch (Exception e){}
        ViewInteraction button = onView(withId(R.id.friendsButton));
        button.check(matches(isDisplayed()));
        //button.check(matches(withText("Friends")));

        button.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.item_number), withText("shaima@abc.com"), isDisplayed()));
        textView.check(matches(withText("shaima@abc.com")));

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.toolbar_layout), withContentDescription("FriendsListActivity"), isDisplayed()));
        frameLayout.check(matches(isDisplayed()));*/

       testPhase2FriendList();

        /*ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.chat_button), withText("chat"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.friends_recyclerview),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.messageField),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("hi"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.sendMessageButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.message_body), withText("hi"), isDisplayed()));
        textView2.check(matches(withText("hi")));
         */
        chatWithShaima();
        pressBack();

        /*ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.chat_button), withText("chat"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.friends_recyclerview),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton4.perform(click());

        pressBack();*/
        chatWithShaimaAgain();

        pressBack();

        /*ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.friendsButton), withText("Friends"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.chat_button), withText("chat"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.friends_recyclerview),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.message_body), withText("hi"), isDisplayed()));
        textView3.check(matches(withText("hi")));*/
        checkForPersistence();
    }

    public void checkForPersistence() {
        ViewInteraction appCompatButton5 = onView(withId(R.id.friendsButton));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.chat_button), withText("chat"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.friends_recyclerview),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton6.perform(click());
        ViewInteraction textView3 = onView(
                allOf(withId(R.id.message_body), withText("hi"), isDisplayed()));
        textView3.check(matches(withText("hi")));
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
