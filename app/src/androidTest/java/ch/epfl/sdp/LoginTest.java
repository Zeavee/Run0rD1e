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

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    private String email;
    private String password;
    private Instrumentation.ActivityResult result;
    private static final int DELAY = 3000;

    private static void sleep() {
        try {
            Thread.sleep(LoginTest.DELAY);
        } catch (InterruptedException e) {
            throw new RuntimeException("Cannot execute Thread.sleep()");
        }
    }

    @Rule
    public final ActivityTestRule <LoginFormActivity> mActivityRule =
            new ActivityTestRule <>(LoginFormActivity.class);

    @Before
    public void setUp(){
        Intents.init();

        // Registered user
        email = "runorapp@gmail.com";
        password = "test1233";

        // Result_OK
        Intent resultData = new Intent();
        resultData.putExtra("resultData", "fancyData");
        result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
    }

    @After
    public void tearDown(){
        Intents.release();
    }

    @Test
    public void writingEmail_Works(){
        onView(withId(R.id.emaillog)).perform(typeText(email)).check(matches(withText(email)));
        closeSoftKeyboard();
    }

    @Test
    public void writingPassword_Works(){
        onView(withId(R.id.passwordlog)).perform(typeText(password)).check(matches(withText(password)));
        closeSoftKeyboard();
    }

    @Test
    public void loginRegisteredUser_AuthenticateTheUser_OpenMainScreen_Logout(){
        onView(withId(R.id.emaillog)).perform(typeText(email));
        closeSoftKeyboard();
        onView(withId(R.id.passwordlog)).perform(typeText(password));
        closeSoftKeyboard();
        intending(toPackage(MainActivity.class.getName())).respondWith(result);
        onView(withId(R.id.loginButton)).perform(click());
        sleep();
        String toast_text = "Logged in successfully";
        onView(withText(toast_text)).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
        onView(withId(R.id.logoutBt)).perform(click());
        intending(toPackage(LoginFormActivity.class.getName())).respondWith(result);
    }

    @Test
    public void loginUnregisteredUserGivesAnError(){
        onView(withId(R.id.emaillog)).perform(typeText("NotAUser@mail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordlog)).perform(typeText("12345678"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
        String toast_text = "Error";
        onView(withSubstring(toast_text)).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
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
        String text = "password is required";
        onView(withId(R.id.passwordlog)).check(matches(hasErrorText(text)));
    }

    @Test
    public void loginOnPasswordSmallerThan8CharsGivesAnError(){
        onView(withId(R.id.emaillog)).perform(typeText("NotAUser"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordlog)).perform(typeText("1234567"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
        String text = "password length has to be greater than 8 Characters";
        onView(withId(R.id.passwordlog)).check(matches(hasErrorText(text)));
    }
}
