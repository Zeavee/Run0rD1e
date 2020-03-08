package ch.epfl.sdp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RegisterTest {
    private String email;
    private String password;
    private Instrumentation.ActivityResult result;

    @Rule
    public final ActivityTestRule <RegisterFormActivity> mActivityRule =
            new ActivityTestRule <>(RegisterFormActivity.class);

    @Before
    public void setUp(){
        email = "test@mail.com";
        password = "12345678";

        // Mock<

        Intents.init();
        Intent resultData = new Intent();
        resultData.putExtra("resultData", "fancyData");
        result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        RegisterFormActivity.authenticationController = new MockAuthController(mActivityRule.getActivity());
    }

    @After
    public void tearDown(){
        Intents.release();
    }

    @Test
    public void writingUsername_ShouldBeDisplayed(){
        onView(withId(R.id.username)).perform(typeText("Username"));
    }

    @Test
    public void writingEmail_ShouldBeDisplayed(){
        onView(withId(R.id.email)).perform(typeText("Email"));
    }

    @Test
    public void writingPassword_ShouldBeDisplayed(){
        onView(withId(R.id.password)).perform(typeText("password"));
    }

    @Test
    public void writingPasswordConfiguration_ShouldBeDisplayed(){
        onView(withId(R.id.passwordconf)).perform(typeText("password"));
    }

    @Test
    public void registering_ShouldFailOnEmptyEmail(){
        onView(withId(R.id.username)).perform(typeText("Username"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("password"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordconf)).perform(typeText("password"));
        closeSoftKeyboard();
        onView(withId(R.id.registerbutton)).perform(click());
    }

    @Test
    public void registering_ShouldFailOnEmptyUsername(){
        onView(withId(R.id.email)).perform(typeText("test"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("password"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordconf)).perform(typeText("password"));
        closeSoftKeyboard();
        onView(withId(R.id.registerbutton)).perform(click());
    }

    @Test
    public void registering_ShouldFailOnEmptyPassword(){
        onView(withId(R.id.username)).perform(typeText("a"));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("a"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordconf)).perform(typeText("password"));
        closeSoftKeyboard();
        onView(withId(R.id.registerbutton)).perform(click());
    }

    @Test
    public void registering_ShouldFailOnEmptyPasswordConfirmation(){
        onView(withId(R.id.username)).perform(typeText("a"));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("a"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("password"));
        closeSoftKeyboard();
        onView(withId(R.id.registerbutton)).perform(click());
    }

    @Test
    public void registering_ShouldFailOnPasswordSmallerThan8(){
        onView(withId(R.id.username)).perform(typeText("a"));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("a"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("passwor"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordconf)).perform(typeText("passwor"));
        closeSoftKeyboard();
        onView(withId(R.id.registerbutton)).perform(click());
    }

    @Test
    public void registering_ShouldWorkOnNewCorrectInformation(){
        String newUsername = "Username";
        String newEmail = "Email";

        onView(withId(R.id.username)).perform(typeText(newUsername));
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText(newEmail));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText(password));
        closeSoftKeyboard();
        onView(withId(R.id.passwordconf)).perform(typeText(password));
        closeSoftKeyboard();
        intending(toPackage(MainActivity.class.getName())).respondWith(result);
        onView(withId(R.id.registerbutton)).perform(click());
    }

    @Test
    public void backButton_ShouldGoToLoginForm(){
        intending(toPackage(LoginFormActivity.class.getName())).respondWith(result);
        onView(withId(R.id.backBtn)).perform(click());
    }

    @Test
    public void registerWhenConnected_ShouldGoToMainScreen(){
        intending(toPackage(LoginFormActivity.class.getName())).respondWith(result);
        onView(withId(R.id.backBtn)).perform(click());
        onView(withId(R.id.emaillog)).perform(typeText(email));
        closeSoftKeyboard();
        onView(withId(R.id.passwordlog)).perform(typeText(password));
        closeSoftKeyboard();
        intending(toPackage(RegisterFormActivity.class.getName())).respondWith(result);
        onView(withId(R.id.createAccountBtn)).perform(click());
    }
}