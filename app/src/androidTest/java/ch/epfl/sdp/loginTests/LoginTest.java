package ch.epfl.sdp.loginTests;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import ch.epfl.sdp.R;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.login.LoginFormActivity;
import ch.epfl.sdp.utils.MissingFieldTestFactory;
import ch.epfl.sdp.utils.MockAuthenticationAPI;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    private String email;
    private String password;
    private HashMap<String, String> registeredUsers;

    @Rule
    public final ActivityTestRule<LoginFormActivity> mActivityRule =
            new ActivityTestRule<>(LoginFormActivity.class);

    @Before
    public void setUp() {
        registeredUsers = new HashMap<>();
        registeredUsers.put("amro.abdrabo@gmail.com", "password");

        email = "amro.abdrabo@gmail.com";
        password = "password";

        ((MyApplication) mActivityRule.getActivity().getApplication()).appContainer.authenticationAPI = new MockAuthenticationAPI(registeredUsers, null);

        try {
            onView(withId(R.id.logoutBt)).perform(click());
        } catch (Exception ignored) {
            //We ignore the exceptions here because we just want to get back if a user was already logged in (can happen in the first test)
        }
    }

    @Test
    public void writingEmail_Works() {
        onView(ViewMatchers.withId(R.id.emaillog)).perform(typeText(email)).check(matches(withText(email)));
    }

    @Test
    public void writingPassword_Works() {
        onView(withId(R.id.passwordlog)).perform(typeText(password)).check(matches(withText(password)));
    }

    @Test
    public void login_shouldWorkWithRegisteredUser() {
        MissingFieldTestFactory.testFieldTwoActionsCloseKeyboard(typeText(email), typeText(password), R.id.emaillog, R.id.passwordlog);
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.logoutBt)).perform(click());
    }

    @Test
    public void loginUnregisteredUserGivesAnError() {
        MissingFieldTestFactory.testFieldTwoActionsCloseKeyboard(typeText("NotAUser@mail.com"), typeText("12345678"), R.id.emaillog, R.id.passwordlog);
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
    }

    @Test
    public void loginWithAnEmptyEmailGivesAnError() {
        onView(withId(R.id.loginButton)).perform(click());
        String text = "Email can't be empty";
        onView(withId(R.id.emaillog)).check(matches(hasErrorText(text)));
    }

    @Test
    public void loginWithAnEmptyPasswordGivesAnError() {
        MissingFieldTestFactory.testFieldTwoActions(typeText("amro.abdrabo@gmail.com"), click(), R.id.emaillog, R.id.loginButton);
        String text = "Password can't be empty";
        onView(withId(R.id.passwordlog)).check(matches(hasErrorText(text)));
    }

    @Test
    public void registerOpens() {
        onView(withId(R.id.createAccountBtn)).perform(click());
        onView(withId(R.id.username)).check(matches(isDisplayed()));
    }
}