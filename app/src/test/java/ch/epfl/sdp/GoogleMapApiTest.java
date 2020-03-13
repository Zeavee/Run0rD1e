package ch.epfl.sdp;

import android.graphics.Bitmap;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class GoogleMapApiTest {
    @Test
    public void createSmallCircleWorks() {
        Bitmap smallCircle = MapsActivity.mapApi.createSmallCircle(0);
        assertNotNull(smallCircle);
    }
}
