package ch.epfl.sdp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.location.LocationManager;

import com.google.android.gms.maps.GoogleMap;

import org.junit.Test;

import java.util.ArrayList;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.MapApi;

public class MockMapApi implements MapApi {
    // Used for tests
    private ArrayList<Displayable> displayables = new ArrayList<>();
    private GeoPoint currentLocation = new GeoPoint(40, 50);


    public ArrayList<Displayable> getDisplayables(){
        return displayables;
    }

    private Activity activity;

    @Override
    public GeoPoint getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(GeoPoint currentLocation){
        this.currentLocation = currentLocation;
    }

    @Override
    public void updatePosition() {
    }

    @Override
    public void moveCameraOnCurrentLocation() {

    }

    @Override
    public Bitmap createSmallCircle(int color) {
      /*  Bitmap output = Bitmap.createBitmap(25, 25, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle(25 / 2, 25 / 2, 25 / 2, paint);
        return output;*/
      return null;
    }

    @Override
    public void displayEntity(Displayable displayable) {
        displayables.add(displayable);
    }

    @Override
    public void unDisplayEntity(Displayable displayable) {
        displayables.remove(displayable);
    }

    @Override
    public void initializeApi(LocationManager locationManager, Activity activity) {
        this.activity = activity;
    }

    @Override
    public void displaySmallIcon(Displayable displayable, String title, int id) {

    }

    @Override
    public Activity getActivity() {
        return activity;
    }

    @Override
    public void displayMarkerCircle(Displayable displayable, int yellow, String other_player, int i) {

    }

    @Override
    public void removeMarkers(Displayable displayable) {

    }

    @Test
    public void unDisplayEntity() {
        Player player1 = new Player(6.149290, 46.212470, 50,
                "Skyris", "test@email.com"); //player position is in Geneva
        this.unDisplayEntity(player1);
    }
}
