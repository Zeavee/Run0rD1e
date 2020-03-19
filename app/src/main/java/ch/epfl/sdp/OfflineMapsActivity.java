package ch.epfl.sdp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class OfflineMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private static float DEFAULT_ZOOM = 3.0f;
    private TileOverlay lausanneTiles;
    private TileProvider customTiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_maps);

        customTiles = new CustomTileProvider(getResources().getAssets());

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        // Default zoom at 3
        map.animateCamera(CameraUpdateFactory.zoomTo( DEFAULT_ZOOM ) );
        map.setMapType(GoogleMap.MAP_TYPE_NONE);
        lausanneTiles = map.addTileOverlay(new TileOverlayOptions().tileProvider(customTiles));
    }
}
