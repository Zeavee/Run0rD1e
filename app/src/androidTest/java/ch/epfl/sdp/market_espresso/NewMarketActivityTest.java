package ch.epfl.sdp.market_espresso;


import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.firebase.CommonMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.ServerMockDatabaseAPI;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;
import ch.epfl.sdp.market.Market;
import ch.epfl.sdp.utils.MockMap;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sdp.SocialTests.ChildParentMatcher.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NewMarketActivityTest {

    @Rule
    public ActivityTestRule<MapsActivity> mActivityTestRule = new ActivityTestRule<MapsActivity>(MapsActivity.class) {

        @Override
        public void beforeActivityLaunched() {
            Player amro = new Player(6.14, 46.22, 100, "amroa", "amro@gmail.com");
            amro.setHealthPoints(100);
            amro.removeMoney(amro.getMoney());
            amro.addMoney(100000);
            PlayerManager.getInstance().setCurrentUser(amro);
            MockMap mockMap = new MockMap();
            Game.getInstance().setMapApi(mockMap);
            ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer.commonDatabaseAPI = new CommonMockDatabaseAPI(new HashMap<>(), new ArrayList<>());
            ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer.serverDatabaseAPI = new ServerMockDatabaseAPI();
        }

        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            intent.putExtra("playMode", "multi-player");
            return intent;
        }

        // start the game engine manually
        @Override
        public void afterActivityLaunched() {
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

    private void clickOnItem(int buttonId, int cardId, int position) {
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(buttonId),
                        childAtPosition(
                                allOf(withId(cardId),
                                        childAtPosition(
                                                childAtPosition(
                                                        allOf(withId(android.R.id.content),
                                                                childAtPosition(
                                                                        withId(R.id.action_bar_root),
                                                                        1)),
                                                        0),
                                                position)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

    }

    // click on scan button
    public void step1() {
        clickOnItem(R.id.scanButton, R.id.scanCard, 2);
    }

    // click on shrinker button
    public void step2() {
        clickOnItem(R.id.shrinkButton, R.id.shrinkerCard, 1);
    }

    // click on the health pack button
    public void step3() {
        clickOnItem(R.id.emsButton, R.id.emsCard, 4);
    }

    // click on the shield button
    public void step4() {
        clickOnItem(R.id.shieldButton, R.id.shieldCard, 3);
    }

    // click on "buy items"
    public void step5() {
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
    }

    // check "Market" is displayed
    public void step6() {
        ViewInteraction textView = onView(withId(R.id.marketLabel));
        textView.check(matches(withText("MARKET")));
    }

    // check that the money displayed has changed
    public void step7() {
        ViewInteraction textView = onView(withId(R.id.textMoney));
        textView.check(matches(not(withText("Money: 100000"))));
    }

    // check that a toast is displayed describing the outcome of the transaction
    public void step8() {
        onView(withText(containsString("purchase"))).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void newMarketActivityTest() throws InterruptedException {
        // wait a bit for MarketActivity to be intended
        Thread.sleep(4000);
        step1();
        step2();
        step3();
        step4();
        step5();
        step6();
        Thread.sleep(500); // wait for money to subtracted
        step7();
        step8();
    }
}
