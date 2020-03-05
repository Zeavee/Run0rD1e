package ch.epfl.sdp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleApi implements MapApi {
    public static double listenTime = 1000; // milliseconds
    public static double listenDistance = 5; // meters

    private Location currentLocation = new Location("Temp_Loc");
    private LocationManager locationManager;
    private Criteria criteria;
    private String bestProvider;
    private LocationListener locationListener;
    private Marker marker;
    private GoogleMap mMap;

    public GoogleApi(LocationManager locationManager) {
        this.locationManager = locationManager;

        // setup bestProvider
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        bestProvider = locationManager.getBestProvider(criteria, true);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location latestLocation) {
                // Called when a new location is found by the network location provider.
                currentLocation = latestLocation;
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
    public Location getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public void updatePosition(Activity activity) {
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
        if (currentLocation == null) {
            return;
        }

        LatLng myPos = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        setMarker(mMap.addMarker(new MarkerOptions().position(myPos).title("My position")));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myPos));
    }

    public void setMap(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void setMarker(Marker marker) {
        if (marker != null) {
            marker.remove();
        }
        this.marker = marker;
    }
}
