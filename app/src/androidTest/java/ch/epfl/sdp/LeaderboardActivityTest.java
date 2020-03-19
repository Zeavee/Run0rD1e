package ch.epfl.sdp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class LeaderboardActivityTest {
    private static final int DELAY = 3000;

    @Rule
    public final ActivityTestRule<LeaderboardActivity> mActivityRule =
            new ActivityTestRule<>(LeaderboardActivity.class);

    @Before
    public void setUp() {
        Intents.init();
        Intent resultData = new Intent();
        resultData.putExtra("resultData", "fancyData");

        mActivityRule.getActivity().userDataController = new MockUserDataController();
        UserForFirebase user1 = new UserForFirebase("aaaaa", "email");
        user1.setHealthPoints(100);

        UserForFirebase user2 = new UserForFirebase("bbbbb", "email");
        user1.setHealthPoints(90);

        UserForFirebase user3 = new UserForFirebase("ccccc", "email");
        user1.setHealthPoints(80);

        UserForFirebase user4 = new UserForFirebase("ddddd", "email");
        user1.setHealthPoints(70);

        mActivityRule.getActivity().userDataController.storeUser(user1);
        mActivityRule.getActivity().userDataController.storeUser(user2);
        mActivityRule.getActivity().userDataController.storeUser(user3);
        mActivityRule.getActivity().userDataController.storeUser(user4);
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testRecyclerview() {
        sleep();
        onView(withId(R.id.tv_username1)).check(matches(withText("aaaaaa")));
        System.out.println(withId(R.id.tv_username1));
        onView(withId(R.id.tv_username2)).check(matches(withText("bbbbbb")));
        onView(withId(R.id.tv_username3)).check(matches(withText("cccccc")));
    }


    private static void sleep() {
        try {
            Thread.sleep(LeaderboardActivityTest.DELAY);
        } catch (InterruptedException e) {
            throw new RuntimeException("Cannot execute Thread.sleep()");
        }
    }
}
