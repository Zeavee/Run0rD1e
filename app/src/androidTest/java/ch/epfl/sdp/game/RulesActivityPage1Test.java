package ch.epfl.sdp.game;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sdp.R;
import ch.epfl.sdp.ui.game.RulesActivityPage1;
import ch.epfl.sdp.ui.game.RulesActivityPage2;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RulesActivityPage1Test {
    @Rule
    public ActivityTestRule<RulesActivityPage1> mActivityRule = new IntentsTestRule<>(RulesActivityPage1.class);

    @Test
    public void btnPage2Click_OpensRulesActivityPage2() {
        onView(withId(R.id.btnPage2)).perform(click());
        intended(hasComponent(RulesActivityPage2.class.getName()));
    }
}
