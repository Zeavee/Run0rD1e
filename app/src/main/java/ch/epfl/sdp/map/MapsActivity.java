package ch.epfl.sdp.map;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import ch.epfl.sdp.R;
import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.artificial_intelligence.SinusoidalMovement;
import ch.epfl.sdp.database.FirestoreUserData;
import ch.epfl.sdp.database.UserDataController;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.LocalArea;
import ch.epfl.sdp.geometry.PointConverter;
import ch.epfl.sdp.geometry.RectangleArea;
import ch.epfl.sdp.geometry.UnboundedArea;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.ItemBox;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final String TAG = "Test Firebase";
    public static MapApi mapApi = new GoogleMapApi();
    public static UserDataController userDataController = new FirestoreUserData();

    public static void setMapApi(MapApi map){
        mapApi = map;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Button mapButton = findViewById(R.id.recenter);
        mapButton.setOnClickListener(v -> mapApi.moveCameraOnCurrentLocation());

        mapApi.initializeApi((LocationManager) getSystemService(Context.LOCATION_SERVICE), this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void initEnvironment() {
        // Game example
        Game game = new Game();
        game.initGame();

        // Enemy -------------------------------------------
        GeoPoint local = new GeoPoint(6.2419, 46.2201);
        GeoPoint enemyPos = new GeoPoint(6.3419, 46.2301);
        LocalArea localArea = new LocalArea(new RectangleArea(3500, 3500), PointConverter.geoPointToCartesianPoint(local));
        Enemy enemy = new Enemy(localArea, new UnboundedArea());
        enemy.setLocation(enemyPos);
        SinusoidalMovement movement = new SinusoidalMovement(PointConverter.geoPointToCartesianPoint(enemyPos));
        movement.setVelocity(5);
        movement.setAngleStep(0.1);
        movement.setAmplitude(10);
        enemy.setMovement(movement);
        Game.addToDisplayList(enemy);
        Game.addToUpdateList(enemy);
        //  -------------------------------------------

        // ItemBox -------------------------------------------
        Healthpack healthpack = new Healthpack(10);
        ItemBox itemBox = new ItemBox();
        itemBox.putItems(healthpack, 1);
        itemBox.setLocation(new GeoPoint(6.14, 46.22));
        Game.addToDisplayList(itemBox);
        Game.addToUpdateList(itemBox);
        //  -------------------------------------------
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Setting the user
        Player user = new Player(6.3419, 46.2301, 22, "startGame2", "startGame2@gmail.com");
        user.setAoeRadius(10); // detection radius would be a better name, aoe (Area of effect)
        // means affecting (like attacking) all entity inside the area, in this case the circle.
        PlayerManager.setUser(user);

        mapApi.setMap(googleMap);
        mapApi.updatePosition();

        initEnvironment();

        // Join
        userDataController.joinLobby(PlayerManager.getUser());
    }
}
