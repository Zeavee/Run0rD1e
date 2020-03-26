package ch.epfl.sdp;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final MapApi mapApi = new GoogleMapApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Button mapButton = findViewById(R.id.recenter);
        mapButton.setOnClickListener(v -> mapApi.moveCameraOnCurrentLocation());

        Button scanButton = findViewById(R.id.scanButton);
        Scan scan = new Scan(new GeoPoint(9.34324, 47.24942), true, 30, mapApi);
        scanButton.setOnClickListener(v -> scan.showAllPlayers());

        mapApi.initializeApi((LocationManager) getSystemService(Context.LOCATION_SERVICE), this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        ((GoogleMapApi) mapApi).setMap(googleMap);
        mapApi.updatePosition();
    }
}
