package ch.epfl.sdp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.net.InetAddress;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OfflineAble {
    public static final MapApi mapApi = new GoogleMapApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Button mapButton = findViewById(R.id.recenter);
        mapButton.setOnClickListener(v -> mapApi.moveCameraOnCurrentLocation());

        mapApi.initializeApi((LocationManager) getSystemService(Context.LOCATION_SERVICE), this);
        InternetConnectionManager.getInstance().setCurrentActivity(this);
        InternetConnectionManager.getInstance().startConnectionMonitor();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        ((GoogleMapApi) mapApi).setMap(googleMap);
        mapApi.updatePosition();
    }


    @Override
    public void switchMode(ConnectionMode cm) {
        System.out.println(" IT WAS CALLEED     CALLED CALLED ");
        Intent offline = new Intent(MapsActivity.this, OfflineMapsActivity.class);
        InternetConnectionManager.getInstance().stopMonitor();
        startActivity(offline);
        finish();
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
