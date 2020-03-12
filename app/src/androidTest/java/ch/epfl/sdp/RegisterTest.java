package ch.epfl.sdp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.util.Pair;
import android.widget.Toast;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.firestore.auth.User;

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
    private UserDataController store;

    @Rule
    public final ActivityTestRule <RegisterFormActivity> mActivityRule =
            new ActivityTestRule <>(RegisterFormActivity.class);

    @Before
    public void setUp(){
        email = "amro.abdrabo@gmail.com";
        password = "password";

        // Mock
        Intents.init();
        Intent resultData = new Intent();
        resultData.putExtra("resultData", "fancyData");
        result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        store = new MockUserDataController();

        RegisterFormActivity.authenticationController = new MockAuthentication(new DefaultAuthenticationDisplay(mActivityRule.getActivity()), store);
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

        MissingFieldTestFactory.testFieldFourActions(new Pair(typeText("Username"), R.id.username),new Pair(typeText("password"), R.id.password), new Pair(typeText("password"), R.id.passwordconf), new Pair(click(), R.id.registerbutton));
    }

    @Test
    public void registering_ShouldFailOnEmptyUsername(){
        MissingFieldTestFactory.testFieldFourActions(new Pair(typeText("test"), R.id.email),new Pair(typeText("password"), R.id.password), new Pair(typeText("password"), R.id.passwordconf), new Pair(click(), R.id.registerbutton));
    }

    @Test
    public void registering_ShouldFailOnEmptyPassword(){
        MissingFieldTestFactory.testFieldFourActions(new Pair(typeText("a"), R.id.username),new Pair(typeText("a"), R.id.email), new Pair(typeText("password"), R.id.passwordconf), new Pair(click(), R.id.registerbutton));
    }

    @Test
    public void registering_ShouldFailOnEmptyPasswordConfirmation(){
        MissingFieldTestFactory.testFieldFourActions(new Pair(typeText("a"), R.id.username),new Pair(typeText("a"), R.id.email), new Pair(typeText("password"), R.id.password), new Pair(click(), R.id.registerbutton));
    }

    @Test
    public void registering_ShouldFailOnPasswordSmallerThan8(){
        MissingFieldTestFactory.testFieldFourActions(new Pair(typeText("a"), R.id.username),new Pair(typeText("passwor"), R.id.email), new Pair(typeText("passwor"), R.id.password), new Pair(click(), R.id.passwordconf));
        closeSoftKeyboard();
        onView(withId(R.id.registerbutton)).perform(click());
    }

    @Test
    public void registering_ShouldWorkOnNewCorrectInformation(){
        String newUsername = "Username";
        String newEmail = "Email";
        MissingFieldTestFactory.testFieldFourActions(new Pair(typeText(newUsername), R.id.username),new Pair(typeText(newEmail), R.id.email), new Pair(typeText(password), R.id.password), new Pair(typeText(password), R.id.passwordconf));
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
        MissingFieldTestFactory.testFieldTwoActionsCloseKeyboard(typeText(email), typeText(password), R.id.emaillog, R.id.passwordlog);
        intending(toPackage(RegisterFormActivity.class.getName())).respondWith(result);
        onView(withId(R.id.createAccountBtn)).perform(click());
    }
}