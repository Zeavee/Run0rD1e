package ch.epfl.sdp;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private MapApi mapApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapApi = new GoogleApi((LocationManager) getSystemService(Context.LOCATION_SERVICE), this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        ((GoogleApi) mapApi).setMap(googleMap);
        mapApi.updatePosition();
    }

    public MapApi getMapApi() {
        return mapApi;
    }


}
