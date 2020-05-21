package ch.epfl.sdp.map;

import android.annotation.SuppressLint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.GameController;
import ch.epfl.sdp.geometry.GeoPoint;

public class GoogleLocationFinder implements LocationFinder {
    private LocationListener locationListener;
    private LocationManager locationManager;
    private Criteria criteria;
    private Location currentLocation;
    private boolean gameStarted = false;

    private final double listenTime = 1000; // milliseconds
    private final double listenDistance = 5; // meters


    public GoogleLocationFinder(LocationManager locationManager, GameController gameController) {
        this.locationManager = locationManager;

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location latestLocation) {
                if (Math.floor(latestLocation.getLatitude() * 100) / 100 != 37.42 || Math.ceil(latestLocation.getLongitude() * 100) / 100 != -122.08) {

                    currentLocation = latestLocation;
                    PlayerManager.getInstance().getCurrentUser().setLocation(new GeoPoint(latestLocation.getLongitude(), latestLocation.getLatitude()));

                    if(!gameStarted && latestLocation.getLatitude() != 0 || latestLocation.getLongitude() != 0) {
                        gameController.start();
                        gameStarted = true;
                    }
                    requestUpdatePosition();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        requestUpdatePosition();
    }

    @Override
    public GeoPoint getCurrentLocation() {
        if (currentLocation != null)
            return new GeoPoint(currentLocation.getLongitude(), currentLocation.getLatitude());
        else
            return null;
    }

    @SuppressLint("MissingPermission")
    private void requestUpdatePosition() {
        for (String locManager : locationManager.getAllProviders()) {
            locationManager.requestLocationUpdates(locManager, (long) listenTime, (float) listenDistance, locationListener);
        }
    }
}
