package ch.epfl.sdp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.util.Log;
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

import ch.epfl.sdp.database.UserDataController;
import ch.epfl.sdp.dependency.injection.DependencyVisitor;
import ch.epfl.sdp.login.AuthenticationController;
import ch.epfl.sdp.login.LoginFormActivity;
import ch.epfl.sdp.login.RegisterFormActivity;
import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.social.RemoteToSQLiteAdapter;
import ch.epfl.sdp.social.friends_firestore.RemoteFriendFetcher;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
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
    private List<Integer> emptyFields;
    private List<String> errorTexts;
    private DependencyVisitor dv = new DependencyVisitor() {
        @Override
        public void setDependency(UserDataController dependency) {

        }

        @Override
        public void setDependency(AuthenticationController dependency) {
                LoginFormActivity.authenticationController = dependency;
                RegisterFormActivity.authenticationController = dependency;
        }

        @Override
        public void setDependency(MapApi dependency) {

        }

        @Override
        public void setDependency(RemoteToSQLiteAdapter dependency) {

        }

        @Override
        public void setDependency(RemoteFriendFetcher dataController) {

        }

        @Override
        public void inject() {
            setDependency(new MockAuthentication(new MockUserDataController()));
        }

    };


    @Rule
    public ActivityTestRule <RegisterFormActivity> mActivityRule =
            new ActivityTestRule<RegisterFormActivity>(RegisterFormActivity.class){
             @Override
             protected void beforeActivityLaunched()
            {
                dv.inject();
            }
    };


    @Before
    public void setUp(){


        testCases = new ArrayList<>();
        testCases.addAll(Arrays.asList(typeText("test"),typeText("password"), typeText("password"), click(),
                typeText("Username"), typeText("password"), typeText("password"), click(),
                typeText("a"), typeText("a@a"), typeText("password"),click(),
                typeText("a"), typeText("a@a"), typeText("password"),click()));

        testCasesInt = new ArrayList<>();
        testCasesInt.addAll(Arrays.asList(R.id.email, R.id.password, R.id.passwordconf, R.id.registerbutton,
                R.id.username, R.id.password, R.id.passwordconf, R.id.registerbutton,
                R.id.username, R.id.email, R.id.passwordconf,R.id.registerbutton,
                R.id.username, R.id.email, R.id.password,R.id.registerbutton));

        emptyFields = new ArrayList<>();
        emptyFields.addAll(Arrays.asList(R.id.username, R.id.email, R.id.password, R.id.password));

        errorTexts = new ArrayList<>();
        errorTexts.addAll(Arrays.asList("Username is incorrect", "Email is incorrect", "Password is incorrect", "Password is incorrect"));


        email = "amro.abdrabo@gmail.com";
        password = "password";
        
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

    /*@Test
    public void writingUsername_ShouldBeDisplayed(){
        closeSoftKeyboard();
        onView(withId(R.id.username)).perform(typeText("Username"));
    }*/

    @Test
    public void writingEmail_ShouldBeDisplayed(){
        closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("Email"));
    }

    @Test
    public void writingPassword_ShouldBeDisplayed(){
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("password"));
    }

    @Test
    public void writingPasswordConfiguration_ShouldBeDisplayed(){
        closeSoftKeyboard();
        onView(withId(R.id.passwordconf)).perform(typeText("password"));
    }

    /*@Test
    public void registering_ShouldFailOnEmptyTextFields(){
        List<ArrayList<Pair<ViewAction, Integer>>> iter = new ArrayList<>();
        for (int i = 0 ; i < 4; ++i)
        {
            MissingFieldTestFactory.testFieldFourActions(new Pair(testCases.get(i*4), testCasesInt.get(i*4)),
                    new Pair(testCases.get(i * 4 + 1), testCasesInt.get(i * 4 + 1)),
                    new Pair(testCases.get(i * 4 + 2), testCasesInt.get(i * 4 + 2)),
                    new Pair(testCases.get(i * 4 + 3), testCasesInt.get(i * 4 + 3)));
            if (i>0) {
                onView(withId(emptyFields.get(i))).check(matches(hasErrorText(errorTexts.get(i))));
            }
            Log.d("COUNTER", " "+i);
            onView(withId(R.id.backBtn)).perform(click());
            onView(withId(R.id.createAccountBtn)).perform(click());
            onView(withId(R.id.email)).check(matches(isDisplayed()));
        }
    }*/

<<<<<<< HEAD
    /*@Test
=======
    // for now
    @Test
>>>>>>> master
    public void registering_ShouldFailOnPasswordSmallerThan8(){
        MissingFieldTestFactory.testFieldFourActions(new Pair(typeText("a"), R.id.username),new Pair(typeText("a@a"), R.id.email), new Pair(typeText("passwor"), R.id.password), new Pair(click(), R.id.passwordconf));
        closeSoftKeyboard();
        onView(withId(R.id.registerbutton)).perform(click());
        onView(withId(R.id.password)).check(matches(hasErrorText("Password is incorrect")));
    }*/

    @Test
    public void registering_ShouldWorkOnNewCorrectInformation(){
        String newUsername = "Username";
        String newEmail = "Email@a";
        MissingFieldTestFactory.testFieldFourActions(new Pair(typeText(newUsername), R.id.username),new Pair(typeText(newEmail), R.id.email), new Pair(typeText(password), R.id.password), new Pair(typeText(password), R.id.passwordconf));
        closeSoftKeyboard();
        intending(toPackage(MainActivity.class.getName())).respondWith(result);
        onView(withId(R.id.registerbutton)).perform(click());
        onView(withId(R.id.rulesButton)).check(matches(isDisplayed()));
    }

    // for now

    @Test
    public void backButton_ShouldGoToLoginForm(){
        intending(toPackage(LoginFormActivity.class.getName())).respondWith(result);
        onView(withId(R.id.backBtn)).perform(click());
        onView(withId(R.id.createAccountBtn)).check(matches(isDisplayed()));
    }

    /*@Test
    public void registerWhenConnected_ShouldGoToMainScreen(){
        //intending(toPackage(LoginFormActivity.class.getName())).respondWith(result);
        onView(withId(R.id.backBtn)).perform(click());
        MissingFieldTestFactory.testFieldTwoActionsCloseKeyboard(typeText(email), typeText(password), R.id.emaillog, R.id.passwordlog);
        //intending(toPackage(RegisterFormActivity.class.getName())).respondWith(result);
        onView(withId(R.id.createAccountBtn)).perform(click());
    }*/
}