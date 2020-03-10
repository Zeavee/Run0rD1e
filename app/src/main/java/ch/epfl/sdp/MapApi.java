package ch.epfl.sdp;

import android.graphics.Bitmap;

import java.util.List;

public interface MapApi {

    public GeoPoint getCurrentLocation();

    public void updatePosition();

    public void displayEnemies(List<Enemy> enemies);

    public void moveCameraOnCurrentLocation();

    public Bitmap createSmallCircle(int color);
}
