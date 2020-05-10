package ch.epfl.sdp.MarketEspresso;


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

import java.util.HashMap;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.firebase.CommonMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.ServerMockDatabaseAPI;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.login.LoginFormActivity;
import ch.epfl.sdp.map.MapsActivity;
import ch.epfl.sdp.market.Market;
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
public class NewMarketActivityTest {

    @Rule
    public ActivityTestRule<MapsActivity> mActivityTestRule = new ActivityTestRule<MapsActivity>(MapsActivity.class){

        @Override
        public void beforeActivityLaunched(){
            Player amro = new Player(6.14, 46.22, 100, "amroa", "amro@gmail.com" );
            amro.addMoney(95000); // sufficiently high enough to be able to buy
            PlayerManager.getInstance().setCurrentUser(amro);
            MockMapApi mockMapApi = new MockMapApi();
            Game.getInstance().setMapApi(mockMapApi);
            ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer.testing = true;
            ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer.commonDatabaseAPI = new CommonMockDatabaseAPI(new HashMap<>());
            ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer.serverDatabaseAPI = new ServerMockDatabaseAPI();
        }

        // start the game engine MANUALLY
        @Override
        public void afterActivityLaunched(){
            // always return this for current location
            getActivity().setLocationFinder(() -> new GeoPoint(6.14, 46.22));
            Game.getInstance().addToDisplayList(new Market(new GeoPoint(6.14, 46.22)));
            Game.getInstance().initGame();
        }
    };

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void newMarketActivityTest() {

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.scanButton),
                        childAtPosition(
                                allOf(withId(R.id.scanCard),
                                        childAtPosition(
                                                childAtPosition(
                                                        allOf(withId(android.R.id.content),
                                                                childAtPosition(
                                                                        withId(R.id.action_bar_root),
                                                                        1)),
                                                        0),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.shrinkButton),
                        childAtPosition(
                                allOf(withId(R.id.shrinkerCard),
                                        childAtPosition(
                                                childAtPosition(
                                                        allOf(withId(android.R.id.content),
                                                                childAtPosition(
                                                                        withId(R.id.action_bar_root),
                                                                        1)),
                                                        0),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.emsButton),
                        childAtPosition(
                                allOf(withId(R.id.emsCard),
                                        childAtPosition(
                                                childAtPosition(
                                                        allOf(withId(android.R.id.content),
                                                                childAtPosition(
                                                                        withId(R.id.action_bar_root),
                                                                        1)),
                                                        0),
                                                4)),
                                0),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.shieldButton),
                        childAtPosition(
                                allOf(withId(R.id.shieldCard),
                                        childAtPosition(
                                                childAtPosition(
                                                        allOf(withId(android.R.id.content),
                                                                childAtPosition(
                                                                        withId(R.id.action_bar_root),
                                                                        1)),
                                                        0),
                                                3)),
                                0),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.BuyButton), withText("Buy Items"),
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
                                5),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction textView = onView(withId(R.id.marketLabel));
        textView.check(matches(withText("MARKET")));
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
