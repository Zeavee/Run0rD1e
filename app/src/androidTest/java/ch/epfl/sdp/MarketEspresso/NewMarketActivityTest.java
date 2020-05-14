package ch.epfl.sdp.MarketEspresso;


import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

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
import ch.epfl.sdp.map.MapsActivity;
import ch.epfl.sdp.market.Market;
import ch.epfl.sdp.utils.MockMapApi;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
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
public class NewMarketActivityTest {

    @Rule
    public ActivityTestRule<MapsActivity> mActivityTestRule = new ActivityTestRule<MapsActivity>(MapsActivity.class) {

        @Override
        public void beforeActivityLaunched() {
            Player amro = new Player(6.14, 46.22, 100, "amroa", "amro@gmail.com");
            amro.addMoney(95000); // sufficiently high enough to be able to buy
            PlayerManager.getInstance().setCurrentUser(amro);
            MockMapApi mockMapApi = new MockMapApi();
            Game.getInstance().setMapApi(mockMapApi);
            ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer.commonDatabaseAPI = new CommonMockDatabaseAPI(new HashMap<>());
            ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer.serverDatabaseAPI = new ServerMockDatabaseAPI();
        }

        // start the game engine MANUALLY
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

    @Test
    public void newMarketActivityTest() throws InterruptedException {
        // wait a bit for MarketActivity to be intended
        Thread.sleep(5000);
        step1();
        step2();
        step3();
        step4();
        step5();
        step6();
    }
}
