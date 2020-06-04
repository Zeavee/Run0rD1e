package ch.epfl.sdp.logic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import ch.epfl.sdp.MainMenuActivity;
import ch.epfl.sdp.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RulesActivityPage2Test {
    @Rule
    public ActivityTestRule<RulesActivityPage2> mActivityRule = new IntentsTestRule<>(RulesActivityPage2.class);

    @Test
    public void btnPage1Click_OpensRulesActivityPage1() {
        onView(withId(R.id.btnPage1)).perform(click());
        intended(hasComponent(RulesActivityPage1.class.getName()));
    }

    @Test
    public void btnStartSiteClick_OpensMainActivity() {
        onView(withId(R.id.btnStartSite)).perform(click());
        intended(hasComponent(MainMenuActivity.class.getName()));
    }
}
