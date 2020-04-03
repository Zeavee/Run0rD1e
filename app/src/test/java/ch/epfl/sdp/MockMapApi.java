package ch.epfl.sdp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.location.LocationManager;

import org.junit.Test;

import java.util.ArrayList;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.GeoPoint;
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
        Bitmap output = Bitmap.createBitmap(25, 25, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle(25 / 2, 25 / 2, 25 / 2, paint);
        return output;
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
    public Activity getActivity() {
        return activity;
    }

    @Test
    public void unDisplayEntity() {
        Player player1 = new Player(6.149290, 46.212470, 50,
                "Skyris", "test@email.com"); //player position is in Geneva
        this.unDisplayEntity(player1);
    }
}
