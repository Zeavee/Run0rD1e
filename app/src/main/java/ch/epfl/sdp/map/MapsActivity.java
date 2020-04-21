package ch.epfl.sdp.map;

import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import ch.epfl.sdp.R;
import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.artificial_intelligence.SinusoidalMovement;
import ch.epfl.sdp.database.firebase.CommonDatabaseAPI;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.LocalArea;
import ch.epfl.sdp.geometry.PointConverter;
import ch.epfl.sdp.geometry.RectangleArea;
import ch.epfl.sdp.geometry.UnboundedArea;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.login.AuthenticationAPI;
import ch.epfl.sdp.utils.DependencyFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final String TAG = "Test Firebase";
    public static MapApi mapApi = new GoogleMapApi();
    private CommonDatabaseAPI commonDatabaseAPI;
    private AuthenticationAPI authenticationAPI;

    public static void setMapApi(MapApi map){
        mapApi = map;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        commonDatabaseAPI = DependencyFactory.getCommonDatabaseAPI();
        authenticationAPI = DependencyFactory.getAuthenticationAPI();

        Button mapButton = findViewById(R.id.recenter);
        mapButton.setOnClickListener(v -> mapApi.moveCameraOnCurrentLocation());

        mapApi.initializeApi((LocationManager) getSystemService(Context.LOCATION_SERVICE), this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void initEnvironment() {
        Game.getInstance().initGame();

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
        Game.getInstance().addToDisplayList(enemy);
        Game.getInstance().addToUpdateList(enemy);
        //  -------------------------------------------

        // ItemBox -------------------------------------------
        Healthpack healthpack = new Healthpack(10);
        ItemBox itemBox = new ItemBox();
        itemBox.putItems(healthpack, 1);
        itemBox.setLocation(new GeoPoint(6.14, 46.22));
        Game.getInstance().addToDisplayList(itemBox);
        Game.getInstance().addToUpdateList(itemBox);
        //  -------------------------------------------
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapApi.setMap(googleMap);

        //Get username and email of CurrentUser;
        String email = authenticationAPI.getCurrentUserEmail();

        //Fetch current User
        try {
            // set the currentUser
            //UserForFirebase userForFirebase = commonDatabaseAPI.fetchUser(email).get();
            //Player currentUser = PlayerConverter.UserForFirebaseToPlayer(userForFirebase);
            //PlayerManager.setCurrentUser(currentUser);
           // mapApi.updatePosition();

            //        commonDatabaseAPI.joinLobby(PlayerManager.getUser());
            //initEnvironment();

        } catch (Exception e) {
            Toast.makeText(MapsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
