package ch.epfl.sdp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class OfflineMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private static float DEFAULT_ZOOM = 3.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_maps);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
