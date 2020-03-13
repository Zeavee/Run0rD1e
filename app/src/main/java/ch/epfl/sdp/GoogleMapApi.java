package ch.epfl.sdp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class GoogleMapApi implements MapApi {
    private static double listenTime = 1000; // milliseconds
    private static double listenDistance = 5; // meters

    private Location currentLocation;
    private LocationManager locationManager;
    private Criteria criteria;
    private String bestProvider;
    private LocationListener locationListener;
    private GoogleMap mMap;
    private Activity activity;
    private Map<Displayable, MapDrawing> enemiesCircles;
    private Player currentUser;

    public GoogleMapApi(LocationManager locationManager, Activity activity) {

        this.locationManager = locationManager;
        this.activity = activity;

        enemiesCircles = new HashMap<>();

        currentUser = new Player(0, 0, 100, "current", "test");

        // setup bestProvider
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        bestProvider = locationManager.getBestProvider(criteria, true);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location latestLocation) {
                // Called when a new location is found by the network location provider.
                updatePosition();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };
    }

    @Override
    public GeoPoint getCurrentLocation() {
        return new GeoPoint(currentLocation.getLongitude(), currentLocation.getLatitude());
    }

    @Override
    public void updatePosition() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }

        for (String locManager : locationManager.getAllProviders()) {
            locationManager.requestLocationUpdates(locManager, (long) listenTime, (float) listenDistance, locationListener);
        }

        bestProvider = locationManager.getBestProvider(criteria, true);
        currentLocation = locationManager.getLastKnownLocation(bestProvider);
        displayEntity(currentUser);
    }

    @Override
    public void moveCameraOnCurrentLocation() {
        if (currentLocation == null) {
            return;
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())));
    }

    public void setMap(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public Bitmap createSmallCircle(int color) {
        Bitmap output = Bitmap.createBitmap(25,
                25, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle(25 / 2, 25 / 2,
                25 / 2, paint);
        return output;
    }

    @Override
    public void displayEntity(Displayable displayable) {
        if (enemiesCircles.containsKey(displayable)) {
            enemiesCircles.get(displayable).marker.remove();
            enemiesCircles.get(displayable).aoe.remove();
        }
        switch (displayable.getEntityType()) {
            case USER:
                if (currentLocation == null) {
                    return;
                }
                currentUser.location = displayable.getLocation();
                displayMarkerCircle(displayable, Color.BLUE, "My position", 100);
                break;
            case ENEMY:
                LatLng enemyPosition = new LatLng(displayable.getLocation().latitude(), displayable.getLocation().longitude());
                displayMarkerCircle(displayable, Color.RED, "Enemy", 1000);
        }
    }

    private void displayMarkerCircle(Displayable displayable, int color, String title, int aoeRadius) {
        LatLng position = new LatLng(displayable.getLocation().latitude(), displayable.getLocation().longitude());
        enemiesCircles.put(displayable, new MapDrawing(mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(title)
                .icon(BitmapDescriptorFactory.fromBitmap(createSmallCircle(color)))),
                mMap.addCircle(new CircleOptions()
                        .center(position)
                        .strokeColor(color)
                        .fillColor(color-0x80000000)
                        .radius(aoeRadius)
                        .strokeWidth(1f))));
    }
}
