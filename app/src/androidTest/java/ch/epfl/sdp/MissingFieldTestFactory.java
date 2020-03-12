package ch.epfl.sdp;

import android.view.View;

import androidx.test.espresso.ViewAction;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

// used to test for empty required fields in the login and registration forms
public class MissingFieldTestFactory {
    public static void testFieldTwoActions(ViewAction action1, ViewAction action2, int filledView, int secondFilledView)
    {
        onView(withId(filledView)).perform(action1);
        closeSoftKeyboard();
        onView(withId(secondFilledView)).perform(action2);
    }
    public static void testFieldTwoActionsCloseKeyboard(ViewAction action1, ViewAction action2, int filledView, int secondFilledView)
    {
        onView(withId(filledView)).perform(action1);
        closeSoftKeyboard();
        onView(withId(secondFilledView)).perform(action2);
        closeSoftKeyboard();
    }
    public static void testFieldFourActions(ViewAction action1, ViewAction action2, ViewAction action3, ViewAction action4, int view1, int view2, int view3, int view4)
    {
        onView(withId(view1)).perform(action1);
        closeSoftKeyboard();
        onView(withId(view2)).perform(action2);
        closeSoftKeyboard();
        onView(withId(view3)).perform(action3);
        closeSoftKeyboard();
        onView(withId(view4)).perform(action4);
    }
}
