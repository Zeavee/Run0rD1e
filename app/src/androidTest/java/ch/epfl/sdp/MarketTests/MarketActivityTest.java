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
import static ch.epfl.sdp.SocialTests.ChildParentMatcher.childAtPosition;
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
            amro.addMoney(95000); // sufficiently high enough to be able to buy
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

    private void checkImgLabelExistsWithTextAndDepth(String text, int Id, int position){
        ViewInteraction textView = onView(
                allOf(withText(text),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(position),
                                                childAtPosition(
                                                        allOf(withId(R.id.mainGrid),
                                                                childAtPosition(
                                                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                                        1)),
                                                        position)),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText(text)));
    }


    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    // click on the shield image (checking it is displayed)
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

    // click on the scanner image (checking it is displayed)
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

    // click on the shrinker image (checking it is displayed)
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

    // check the health pack image has label "health" displayed underneath it
    public void step4(){
        checkImgLabelExistsWithTextAndDepth("Health", R.id.emsCard, 1);
    }

    // check the shield has label "shield" displayed underneath it
    public void step5(){
        checkImgLabelExistsWithTextAndDepth("Shield", R.id.shieldCard, 0);
    }

    // check the scan image has label "scan" displayed below it
    public void step6(){
        checkImgLabelExistsWithTextAndDepth("Scan", R.id.scanCard, 2);
    }

    // check the shrinker image has label "shrinker" displayed underneath it
    public void step7(){
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
    }

    // click on button "buy" making sure it is displayed
    public void step8(){
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
    }

    // check that the work "Market" is displayed
    public void step9(){
        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textGrid), withText("MARKET"),
                        isDisplayed()));
        textView5.check(matches(withText("MARKET")));
    }

    // Auto-generated by test recorder
    @Test
    public void marketActivityTest() {
        step1();
        step2();
        step3();
        step4();
        step5();
        step6();
        step7();
        step8();
        step9();
    }
}
