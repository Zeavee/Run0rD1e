package ch.epfl.sdp;

import android.app.Activity;
import android.location.Location;

import java.util.List;

public interface MapApi {

    public Location getCurrentLocation();

    public void updatePosition(Activity activity);

    public void displayEnemies(List<Enemy> enemies);
}
