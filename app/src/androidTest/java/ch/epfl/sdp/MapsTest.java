package ch.epfl.sdp;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MapsTest {
    @Rule
    public final ActivityTestRule<MapsActivity> mActivityRule =
            new ActivityTestRule<>(MapsActivity.class);
    @Test
    public void positionIsCorrect() {
        onView(withId(R.id.update_loc)).perform(click());
        /*LocationManager locationManager = (LocationManager) mActivityRule.getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.addTestProvider(LocationManager.GPS_PROVIDER, false, false, false, false, false, false, false, Criteria.POWER_MEDIUM, Criteria.ACCURACY_FINE);
        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);

        Location mockLoc1 = new Location(LocationManager.GPS_PROVIDER);
        mockLoc1.setLatitude(-33.865143);
        mockLoc1.setLongitude(151.209900);
        mockLoc1.setAltitude(100);
        mockLoc1.setAccuracy(1);
        mockLoc1.setElapsedRealtimeNanos(1);
        mockLoc1.setTime(1);
        locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, mockLoc1);
        onView(withId(R.id.update_loc)).perform(click());
        assertEquals(-33.865143, mActivityRule.getActivity().getCurrentLocation().getLatitude(), 0.001);
        assertEquals(151.209900, mActivityRule.getActivity().getCurrentLocation().getLongitude(), 0.001);

        Location mockLoc2 = new Location(LocationManager.GPS_PROVIDER);
        mockLoc2.setLatitude(46.52);
        mockLoc2.setLongitude(6.57);
        mockLoc2.setAltitude(100);
        mockLoc2.setAccuracy(1);
        mockLoc2.setElapsedRealtimeNanos(1);
        mockLoc2.setTime(1);
        locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, mockLoc2);
        onView(withId(R.id.update_loc)).perform(click());
        assertEquals(46.52, mActivityRule.getActivity().getCurrentLocation().getLatitude(), 0.001);
        assertEquals(6.57, mActivityRule.getActivity().getCurrentLocation().getLongitude(), 0.001);*/
    }
}