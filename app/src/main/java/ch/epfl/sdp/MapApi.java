package ch.epfl.sdp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.location.Location;

import java.util.List;

public interface MapApi {

    public Location getCurrentLocation();

    public void updatePosition();

    public void displayEnemies(List<Enemy> enemies);

    void moveCameraOnCurrentLocation();

    public Bitmap createSmallCircle(int color);
}
