package ch.epfl.sdp;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class LoginTestWithFirebase {
    @Rule
    public final ActivityTestRule<LoginFormActivity> mActivityRule =
            new ActivityTestRule<>(LoginFormActivity.class);

    @Test
    public void loginButtonWithBlankTexts() {
        onView(withId(R.id.loginButton)).perform(click());
    }

    @Test
    public void createAnAccountButton() {
        onView(withId(R.id.createAccountBtn)).perform(click());
    }
}
