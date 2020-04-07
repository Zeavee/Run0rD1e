package ch.epfl.sdp.map;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.R;
import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.item.Scan;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static MapApi mapApi = new GoogleMapApi();

    public static void setMapApi(MapApi map){
        mapApi = map;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Button mapButton = findViewById(R.id.recenter);
        mapButton.setOnClickListener(v -> mapApi.moveCameraOnCurrentLocation());

        Button scanButton = findViewById(R.id.scanButton);

      /*  Scan scan = new Scan(30);
        ItemBox itemBox = new ItemBox();
        itemBox.putItems(scan,1);
        itemBox.setLocation(new GeoPoint(9.34324, 47.24942));
        scanButton.setOnClickListener(v -> scan.use());*/

        mapApi.initializeApi((LocationManager) getSystemService(Context.LOCATION_SERVICE), this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Player user = new Player();
        user.setAoeRadius(10);
        PlayerManager.setUser(user);

        mapApi.setMap(googleMap);
        mapApi.updatePosition();

        Game game = new Game();
        game.initGame();

        Healthpack healthpack = new Healthpack(10);
        ItemBox itemBox = new ItemBox();
        itemBox.putItems(healthpack,1);
        itemBox.setLocation(new GeoPoint(6.14, 46.22));

        Game.addToDisplayList(itemBox);
        Game.addToUpdateList(itemBox);

        Enemy enemy = new Enemy();
        enemy.setLocation(new GeoPoint(6.1419, 46.2201));

        Game.addToDisplayList(enemy);
    }
}
