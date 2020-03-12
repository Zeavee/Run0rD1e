package ch.epfl.sdp;

import android.view.View;

import androidx.test.espresso.ViewAction;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

// used to test for empty required fields in the login and registration forms
public class MissingFieldTestFactory {
    public static void testField(ViewAction action1, ViewAction action2, int filledView, int secondFilledView)
    {
        onView(withId(filledView)).perform(action1);
        closeSoftKeyboard();
        onView(withId(secondFilledView)).perform(action2);
    }
}
