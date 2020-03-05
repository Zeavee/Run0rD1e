package ch.epfl.sdp;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private MapApi mapApi;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Button button = findViewById(R.id.update_loc);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mapApi.updatePosition(activity);
            }
        });

        mapApi = new GoogleApi((LocationManager) getSystemService(Context.LOCATION_SERVICE));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        ((GoogleApi) mapApi).setMap(googleMap);
    }

    public MapApi getMapApi() {
        return mapApi;
    }


}
