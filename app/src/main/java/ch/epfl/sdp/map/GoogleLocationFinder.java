package ch.epfl.sdp.map;

import android.annotation.SuppressLint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.PointConverter;

public class GoogleLocationFinder implements LocationFinder {
    private LocationListener locationListener;
    private LocationManager locationManager;
    private Criteria criteria;
    private String bestProvider;
    private Location currentLocation;

    private final double listenTime = 1000; // milliseconds
    private final double listenDistance = 5; // meters


    public GoogleLocationFinder(LocationManager locationManager) {
        this.locationManager = locationManager;

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
        if (currentLocation != null)
            return new GeoPoint(currentLocation.getLongitude(), currentLocation.getLatitude());
        else
            return null;
    }

    @SuppressLint("MissingPermission")
    private void updatePosition() {
        for (String locManager : locationManager.getAllProviders()) {
            locationManager.requestLocationUpdates(locManager, (long) listenTime, (float) listenDistance, locationListener);
        }

        bestProvider = locationManager.getBestProvider(criteria, true);
        currentLocation = locationManager.getLastKnownLocation(bestProvider);
        PlayerManager.getCurrentUser().setLocation(getCurrentLocation());
        PlayerManager.getCurrentUser().setPosition(PointConverter.geoPointToCartesianPoint(PlayerManager.getCurrentUser().getLocation()));
    }
}
