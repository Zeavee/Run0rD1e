package ch.epfl.sdp;

import android.util.Pair;

import androidx.test.espresso.ViewAction;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
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
    public static void testFieldFourActions(Pair<ViewAction, Integer> actionInt1, Pair<ViewAction, Integer> actionInt2, Pair<ViewAction, Integer> actionInt3, Pair<ViewAction, Integer> actionInt4)
    {
        onView(withId(actionInt1.second)).perform(actionInt1.first);
        closeSoftKeyboard();
        onView(withId(actionInt2.second)).perform(actionInt2.first);
        closeSoftKeyboard();
        onView(withId(actionInt3.second)).perform(actionInt3.first);
        closeSoftKeyboard();
        onView(withId(actionInt4.second)).perform(actionInt4.first);
    }
}
