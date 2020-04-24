package ch.epfl.sdp.map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.location.LocationManager;

import com.google.android.gms.maps.GoogleMap;

import ch.epfl.sdp.geometry.GeoPoint;

public interface MapApi {

    /**
     * Method for the current location
     * @return the current location of the phone
     */
    GeoPoint getCurrentLocation();

    /**
     * Method that update position, will maybe be switched to private
     */
    void updatePosition();

    /**
     * A method that moves the camera on the current location of the phone
     */
    void moveCameraOnCurrentLocation();

    /**
     * A method that creates a small circle that has always the same size on screen,
     * so we can see it even when the map is not zoomed
     * @param color a color in RGB
     * @return a bitmap, which is an image
     */
    Bitmap createSmallCircle(int color);

    /**
     * A method to display objects on the map
     *
     * @param displayable an entity that is displayable on the map
     */
     void displayEntity(Displayable displayable);

     void unDisplayEntity(Displayable displayable);

     void initializeApi(LocationManager locationManager, Activity activity);


     Activity getActivity();

     void setMap(GoogleMap googleMap);
}
