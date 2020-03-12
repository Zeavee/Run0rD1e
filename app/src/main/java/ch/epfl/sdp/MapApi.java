package ch.epfl.sdp;

import android.graphics.Bitmap;

import java.util.List;

public interface MapApi {

    /**
     * Method for the current location
     * @return the current location of the phone
     */
    public GeoPoint getCurrentLocation();

    /**
     * Method that update position, will maybe be switched to private
     */
    public void updatePosition();

    /**
     * Displays a list of enemies on the map
     * @param enemies a list of enemies to display
     */
    public void displayEnemies(List<Enemy> enemies);

    /**
     * A method that moves the camera on the current location of the phone
     */
    public void moveCameraOnCurrentLocation();

    /**
     * A method that creates a small circle that has always the same size on screen,
     * so we can see it even when the map is not zoomed
     * @param color a color in RGB
     * @return a bitmap, which is an image
     */
    public Bitmap createSmallCircle(int color);
}
