package ch.epfl.sdp.map;

import org.junit.Test;

import static org.junit.Assert.assertNull;

public class GoogleMapApiTest {
    @Test
    public void currentLocationIsNull() {
        MapsActivity.setMapApi(new GoogleMapApi());
        assertNull(MapsActivity.mapApi.getCurrentLocation());
    }
}
