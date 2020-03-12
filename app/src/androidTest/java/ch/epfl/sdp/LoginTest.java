package ch.epfl.sdp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    private String email;
    private String password;
    private Instrumentation.ActivityResult result;
    private UserDataController store;

    @Rule
    public final ActivityTestRule<LoginFormActivity> mActivityRule =
            new ActivityTestRule<>(LoginFormActivity.class);

    @Before
    public void setUp() {
        email = "amro.abdrabo@gmail.com";
        password = "password";

        Intents.init();
        Intent resultData = new Intent();
        resultData.putExtra("resultData", "fancyData");

        result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        mActivityRule.getActivity().authenticationController = new MockAuthentication(new DefaultAuthenticationDisplay(mActivityRule.getActivity()), store);

    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void writingEmail_Works() {
        onView(withId(R.id.emaillog)).perform(typeText(email)).check(matches(withText(email)));
    }

    @Test
    public void writingPassword_Works() {
        onView(withId(R.id.passwordlog)).perform(typeText(password)).check(matches(withText(password)));
    }

    @Test
    public void loginRegisteredUser_AuthenticateTheUser_OpenMainScreen_Logout(){
        MissingFieldTestFactory.testFieldTwoActionsCloseKeyboard(typeText(email), typeText(password), R.id.emaillog, R.id.passwordlog);
        MissingFieldTestFactory.testFieldTwoActions(click(), click(), R.id.loginButton, R.id.logoutBt);
    }

    @Test
    public void login_shouldWorkWithRegisteredUser(){
        MissingFieldTestFactory.testFieldTwoActionsCloseKeyboard(typeText(email), typeText(password), R.id.emaillog, R.id.passwordlog);
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.logoutBt));
    }

    @Test
    public void loginUnregisteredUserGivesAnError(){
        MissingFieldTestFactory.testFieldTwoActionsCloseKeyboard(typeText("NotAUser@mail.com"), typeText("12345678"), R.id.emaillog, R.id.passwordlog);
        onView(withId(R.id.loginButton)).perform(click());
    }

    @Test
    public void loginWithAnEmptyEmailGivesAnError(){
        onView(withId(R.id.loginButton)).perform(click());
        String text = "Email is incorrect";
        onView(withId(R.id.emaillog)).check(matches(hasErrorText(text)));
    }

    @Test
    public void loginWithAnEmptyPasswordGivesAnError(){
        MissingFieldTestFactory.testFieldTwoActions(typeText("amro.abdrabo@gmail.com"), click(), R.id.emaillog, R.id.loginButton);
        String text = "Password is incorrect";
        onView(withId(R.id.passwordlog)).check(matches(hasErrorText(text)));
    }

    @Test
    public void loginOnPasswordSmallerThan8CharsGivesAnError(){
        MissingFieldTestFactory.testFieldTwoActionsCloseKeyboard(typeText("amro.abdrabo@gmail.com"),typeText("1234567"), R.id.emaillog, R.id.passwordlog);
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.passwordlog)).check(matches(hasErrorText("Password is incorrect")));
    }
}
