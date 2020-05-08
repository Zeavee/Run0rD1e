package ch.epfl.sdp.MarketTests;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sdp.R;
import ch.epfl.sdp.SocialTests.MockFriendsFetcher;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.Server;
import ch.epfl.sdp.login.LoginFormActivity;
import ch.epfl.sdp.map.GoogleMapApi;
import ch.epfl.sdp.map.MapsActivity;
import ch.epfl.sdp.utils.MockMapApi;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MarketActivityTest {

    @Rule
    public ActivityTestRule<MapsActivity> mActivityTestRule = new ActivityTestRule<MapsActivity>(MapsActivity.class){

        @Override
        public void beforeActivityLaunched(){

            Player amro = new Player(6.14, 46.22, 100, "amroa", "amro@gmail.com" );
            amro.addMoney(95000); // what I have in real life (...if you're dumb enough to believe)
            PlayerManager.setCurrentUser(amro);
            ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer.testing = true;
            ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer.commonDatabaseAPI = new CommonMockDatabaseAPI();
        }

        // start the game engine
        @Override
        public void afterActivityLaunched(){
            MockMapApi mockMapApi = new MockMapApi();
            Game.getInstance().setMapApi(mockMapApi);
            Game.getInstance().setRenderer(mockMapApi);
            Server.initEnvironment();
        }
    };

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    public void step1(){
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.shieldImg),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.shieldCard),
                                                childAtPosition(
                                                        allOf(withId(R.id.mainGrid),
                                                                childAtPosition(
                                                                        withClassName(is("android.widget.LinearLayout")),
                                                                        1)),
                                                        0)),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView.perform(click());
    }

    public void step2(){
        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.scanImg),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.scanCard),
                                                childAtPosition(
                                                        allOf(withId(R.id.mainGrid),
                                                                childAtPosition(
                                                                        withClassName(is("android.widget.LinearLayout")),
                                                                        1)),
                                                        2)),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView2.perform(click());
    }

    public void step3(){
        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.aeoImg),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.shrinkCard),
                                                childAtPosition(
                                                        allOf(withId(R.id.mainGrid),
                                                                childAtPosition(
                                                                        withClassName(is("android.widget.LinearLayout")),
                                                                        1)),
                                                        3)),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView3.perform(click());
    }

    public void step4(){
        ViewInteraction textView = onView(
                allOf(withText("Health"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.emsCard),
                                                childAtPosition(
                                                        allOf(withId(R.id.mainGrid),
                                                                childAtPosition(
                                                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                                        1)),
                                                        1)),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Health")));
    }

    public void step5(){
        ViewInteraction textView2 = onView(
                allOf(withText("Shield"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.shieldCard),
                                                childAtPosition(
                                                        allOf(withId(R.id.mainGrid),
                                                                childAtPosition(
                                                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                                        1)),
                                                        0)),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Shield")));
    }
    @Test
    public void marketActivityTest() {

        // click on the shield (checking it is displayed)
        step1();

        // click on the scanner (checking it is displayed)
        step2();

        // click on the shrinker (checking it is displayed)
        step3();

        // check the health pack has label health displayed underneath it
        step4();

        // check the shield has label shield displayed underneath it
        step5();



        ViewInteraction textView3 = onView(
                allOf(withText("Scan"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.scanCard),
                                                childAtPosition(
                                                        allOf(withId(R.id.mainGrid),
                                                                childAtPosition(
                                                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                                        1)),
                                                        2)),
                                        0),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("Scan")));

        ViewInteraction textView4 = onView(
                allOf(withText("Shrinker"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.shrinkCard),
                                                childAtPosition(
                                                        allOf(withId(R.id.mainGrid),
                                                                childAtPosition(
                                                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                                        1)),
                                                        3)),
                                        0),
                                1),
                        isDisplayed()));
        textView4.check(matches(withText("Shrinker")));



        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.buyButton), withText("BUY"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(android.R.id.content),
                                                childAtPosition(
                                                        allOf(withId(R.id.action_bar_root),
                                                                childAtPosition(
                                                                        withClassName(is("android.widget.FrameLayout")),
                                                                        0)),
                                                        1)),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());



        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textGrid), withText("MARKET"),
                        isDisplayed()));
        textView5.check(matches(withText("MARKET")));
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
