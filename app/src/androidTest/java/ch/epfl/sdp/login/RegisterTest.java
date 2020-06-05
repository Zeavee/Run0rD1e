package ch.epfl.sdp.login;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;

import androidx.test.core.app.ApplicationProvider;
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
import java.util.HashMap;
import java.util.List;

import ch.epfl.sdp.MainMenuActivity;
import ch.epfl.sdp.R;
import ch.epfl.sdp.database.authentication.MockAuthenticationAPI;
import ch.epfl.sdp.database.firebase.CommonMockDatabaseAPI;
import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.utils.MissingFieldTestFactory;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RegisterTest {
    private String password;
    private Instrumentation.ActivityResult result;
    private List<ViewAction> testCases;
    private List<Integer> testCasesInt;
    private List<Integer> emptyFields;
    private List<String> errorTexts;

    @Rule
    public ActivityTestRule<RegisterFormActivity> mActivityRule =
            new ActivityTestRule<RegisterFormActivity>(RegisterFormActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    AppContainer appContainer = ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer;
                    appContainer.authenticationAPI = new MockAuthenticationAPI(new HashMap<>(), null);
                    appContainer.commonDatabaseAPI = new CommonMockDatabaseAPI(new HashMap<>(), new ArrayList<>());
                }
            };


    @Before
    public void setUp() {
        testCases = new ArrayList<>();
        testCases.addAll(Arrays.asList(typeText("test"), typeText("password"), typeText("password"), click(),
                typeText("Username"), typeText("password"), typeText("password"), click(),
                typeText("a"), typeText("a@a"), typeText("password"), click()));

        testCasesInt = new ArrayList<>();
        testCasesInt.addAll(Arrays.asList(R.id.email, R.id.txtRegisterPassword, R.id.passwordconf, R.id.registerbutton,
                R.id.username, R.id.txtRegisterPassword, R.id.passwordconf, R.id.registerbutton,
                R.id.username, R.id.email, R.id.passwordconf, R.id.registerbutton));

        emptyFields = new ArrayList<>();
        emptyFields.addAll(Arrays.asList(R.id.username, R.id.email, R.id.txtRegisterPassword, R.id.passwordconf));

        errorTexts = new ArrayList<>();
        errorTexts.addAll(Arrays.asList("Username is incorrect", "Email is incorrect", "Password is incorrect"));


        password = "password";

        Intents.init();
        Intent resultData = new Intent();
        resultData.putExtra("resultData", "fancyData");
        result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void registering_ShouldFailOnEmptyTextFields() {
        for (int i = 0; i < 3; ++i) {
            MissingFieldTestFactory.testFieldFourActions(new Pair<>(testCases.get(i * 4), testCasesInt.get(i * 4)),
                    new Pair<>(testCases.get(i * 4 + 1), testCasesInt.get(i * 4 + 1)),
                    new Pair<>(testCases.get(i * 4 + 2), testCasesInt.get(i * 4 + 2)),
                    new Pair<>(testCases.get(i * 4 + 3), testCasesInt.get(i * 4 + 3)));
            if (i > 0) {
                onView(withId(emptyFields.get(i))).check(matches(hasErrorText(errorTexts.get(i))));
            }
            Log.d("COUNTER", " " + i);
            onView(withId(R.id.registerbutton)).perform(click());
            onView(withId(R.id.email)).check(matches(isDisplayed()));

            onView(withId(R.id.username)).perform(clearText());
            onView(withId(R.id.txtRegisterPassword)).perform(clearText());
            onView(withId(R.id.passwordconf)).perform(clearText());
            onView(withId(R.id.email)).perform(clearText());
        }
    }

    @Test
    public void registering_ShouldFailOnPasswordSmallerThan8() {
        MissingFieldTestFactory.testFieldFourActions(new Pair<>(typeText("a"), R.id.username), new Pair<>(typeText("a@a"), R.id.email), new Pair<>(typeText("passwor"), R.id.txtRegisterPassword), new Pair<>(click(), R.id.passwordconf));
        closeSoftKeyboard();
        onView(withId(R.id.registerbutton)).perform(click());
        onView(withId(R.id.txtRegisterPassword)).check(matches(hasErrorText("Password is incorrect")));
    }

    @Test
    public void registering_ShouldWorkOnNewCorrectInformation() {
        String newUsername = "Username";
        String newEmail = "Email@a";
        MissingFieldTestFactory.testFieldFourActions(new Pair<>(typeText(newUsername), R.id.username), new Pair<>(typeText(newEmail), R.id.email), new Pair<>(typeText(password), R.id.txtRegisterPassword), new Pair<>(typeText(password), R.id.passwordconf));
        closeSoftKeyboard();
        intending(toPackage(MainMenuActivity.class.getName())).respondWith(result);
        onView(withId(R.id.registerbutton)).perform(click());
        onView(withId(R.id.rulesButton)).check(matches(isDisplayed()));
    }
}