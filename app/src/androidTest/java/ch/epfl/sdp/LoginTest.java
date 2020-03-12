package ch.epfl.sdp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.widget.Toast;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.firestore.auth.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.core.content.ContextCompat.startActivity;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
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
        email = "test@mail.com";
        password = "12345678";

        Intents.init();
        Intent resultData = new Intent();
        resultData.putExtra("resultData", "fancyData");

        result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        mActivityRule.getActivity().authenticationController = new MockAuthentication(new AuthenticationOutcomeDisplayVisitor() {
            @Override
            public void onSuccessfulAuthentication() {
                Toast.makeText(mActivityRule.getActivity(), "Success!", Toast.LENGTH_SHORT);
                Intent myIntent = new Intent(mActivityRule.getActivity(), MainActivity.class);
                mActivityRule.getActivity().startActivity(myIntent);
                mActivityRule.finishActivity();
            }

            @Override
            public void onFailedAuthentication() {
                Toast.makeText(mActivityRule.getActivity(), "Failed!", Toast.LENGTH_SHORT);
            }
        }, store);
        //LoginFormActivity.authenticationController = new MockAuthentication();
        //LoginFormActivity.authenticationController = new MockAuthController(mActivityRule.getActivity());
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
        onView(withId(R.id.emaillog)).perform(typeText(email));
        closeSoftKeyboard();
        onView(withId(R.id.passwordlog)).perform(typeText(password));
        closeSoftKeyboard();
        intending(toPackage(MainActivity.class.getName())).respondWith(result);
        onView(withId(R.id.loginButton)).perform(click());
        intending(toPackage(LoginFormActivity.class.getName())).respondWith(result);
        onView(withId(R.id.logoutBt)).perform(click());
    }

    @Test
    public void login_shouldWorkWithRegisteredUser(){
        onView(withId(R.id.emaillog)).perform(typeText(email));
        closeSoftKeyboard();
        onView(withId(R.id.passwordlog)).perform(typeText(password));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.logoutBt));
    }

    @Test
    public void loginUnregisteredUserGivesAnError(){
        onView(withId(R.id.emaillog)).perform(typeText("NotAUser@mail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordlog)).perform(typeText("12345678"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
    }

    @Test
    public void loginWithAnEmptyEmailGivesAnError(){
        onView(withId(R.id.loginButton)).perform(click());
        String text = "email is required";
        onView(withId(R.id.emaillog)).check(matches(hasErrorText(text)));
    }

    @Test
    public void loginWithAnEmptyPasswordGivesAnError(){
        onView(withId(R.id.emaillog)).perform(typeText("NotAUser"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
        String text = "Password is incorrect";
        onView(withId(R.id.passwordlog)).check(matches(hasErrorText(text)));
    }

    @Test
    public void loginOnPasswordSmallerThan8CharsGivesAnError(){
        onView(withId(R.id.emaillog)).perform(typeText("NotAUser"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordlog)).perform(typeText("1234567"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
        String text = "Password is incorrect";
        onView(withId(R.id.passwordlog)).check(matches(hasErrorText(text)));
    }
}
