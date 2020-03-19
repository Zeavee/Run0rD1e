package ch.epfl.sdp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.util.Pair;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RegisterTest {
    private String email;
    private String password;
    private Instrumentation.ActivityResult result;
    private UserDataController store;
    private List<ViewAction> testCases;
    private List<Integer> testCasesInt;


    @Rule
    public final ActivityTestRule <RegisterFormActivity> mActivityRule =
            new ActivityTestRule <>(RegisterFormActivity.class);

    @Before
    public void setUp(){
        testCases = new ArrayList<>();
        testCases.addAll(Arrays.asList(typeText("test"),typeText("password"), typeText("password"), click(),
                typeText("Username"), typeText("password"), typeText("password"), click(),
                typeText("a"), typeText("a"), typeText("password"),click(),
                typeText("a"), typeText("a"), typeText("password"),click()));

        testCasesInt = new ArrayList<>();
        testCasesInt.addAll(Arrays.asList(R.id.email, R.id.password, R.id.passwordconf, R.id.registerbutton,
                R.id.username, R.id.password, R.id.passwordconf, R.id.registerbutton,
                R.id.username, R.id.email, R.id.passwordconf,R.id.registerbutton,
                R.id.username, R.id.email, R.id.password,R.id.registerbutton));

        email = "amro.abdrabo@gmail.com";
        password = "password";

        // Mock
        Intents.init();
        Intent resultData = new Intent();
        resultData.putExtra("resultData", "fancyData");
        result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        store = new MockUserDataController();
        mActivityRule.getActivity().authenticationController = new MockAuthentication(store);
    }

    @After
    public void tearDown(){
        Intents.release();
    }

    @Test
    public void writingUsername_ShouldBeDisplayed(){
        onView(withId(R.id.username)).perform(typeText("Username")).check(matches(withText("Username")));

    }

    @Test
    public void writingEmail_ShouldBeDisplayed(){
        onView(withId(R.id.email)).perform(typeText("Email")).check(matches(withText("Email")));
    }

    @Test
    public void writingPassword_ShouldBeDisplayed(){
        onView(withId(R.id.password)).perform(typeText("password")).check(matches(withText("password")));
    }

    @Test
    public void writingPasswordConfiguration_ShouldBeDisplayed(){
        onView(withId(R.id.passwordconf)).perform(typeText("password")).check(matches(withText("password")));
    }

    @Test
    public void registering_ShouldFailOnEmptyTextFields(){
        List<ArrayList<Pair<ViewAction, Integer>>> iter = new ArrayList<>();
        for (int i = 0 ; i < 4; ++i)
        {
            MissingFieldTestFactory.testFieldFourActions(new Pair(testCases.get(i*4), testCasesInt.get(i*4)),
                    new Pair(testCases.get(i * 4 + 1), testCasesInt.get(i * 4 + 1)),
                    new Pair(testCases.get(i * 4 + 2), testCasesInt.get(i * 4 + 2)),
                    new Pair(testCases.get(i * 4 + 3), testCasesInt.get(i * 4 + 3)));

        }
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