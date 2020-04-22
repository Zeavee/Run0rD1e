package ch.epfl.sdp.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

import ch.epfl.sdp.R;
import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.artificial_intelligence.LocalBounds;
import ch.epfl.sdp.artificial_intelligence.PointConverter;
import ch.epfl.sdp.artificial_intelligence.RectangleBounds;
import ch.epfl.sdp.artificial_intelligence.SinusoidalMovement;
import ch.epfl.sdp.artificial_intelligence.UnboundedArea;
import ch.epfl.sdp.database.FirestoreUserData;
import ch.epfl.sdp.database.InitializeGameFirestore;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.entity.ShelterArea;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.InventoryFragment;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.leaderboard.LeaderboardActivity;
import ch.epfl.sdp.login.AuthenticationController;
import ch.epfl.sdp.login.FirebaseAuthentication;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {
    public static final int LOCATION_UPDATES_REQUEST_CODE = 101;
    public static final String lobbyCollectionName = "Lobby";
    public static MapApi mapApi = new GoogleMapApi();
    public static final FirestoreUserData firestoreUserData = new FirestoreUserData();
    public static final AuthenticationController authenticationController = new FirebaseAuthentication(firestoreUserData);
    private static InventoryFragment inventoryFragment = new InventoryFragment();
    private static Game game;
    public static String currentUserEmail;
    //public static Player currentUser;
    public static Enemy currentEnnemy;
    private TextView username, healthPointText;
    private ProgressBar healthPointProgressBar;

    boolean flag = false;

    public static void setMapApi(MapApi map){
        mapApi = map;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Button mapButton = findViewById(R.id.recenter);
        mapButton.setOnClickListener(v -> {
            mapApi.moveCameraOnCurrentLocation();
        });

        //ShelterArea shelterArea = new ShelterArea(new GeoPoint(6.147467, 46.210428), 100, null);

        findViewById(R.id.button_leaderboard).setOnClickListener(view -> startActivity(new Intent(MapsActivity.this, LeaderboardActivity.class)));

        username = findViewById(R.id.gameinfo_username_text);
        healthPointProgressBar = findViewById(R.id.gameinfo_healthpoint_progressBar);
        healthPointText = findViewById(R.id.gameinfo_healthpoint_text);
        username.setText("");

        mapApi.initializeApi((LocationManager) getSystemService(Context.LOCATION_SERVICE), this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
        showGameInfoThread().start();
    }

    private void initEnvironment() {
        // Game example
        game = new Game();
        game.initGame();

        // Enemy -------------------------------------------
      /*  GeoPoint local = new GeoPoint(6.2419, 46.2201);
        GeoPoint enemyPos = new GeoPoint(6.3419, 46.2301);
        LocalBounds localBounds = new LocalBounds(new RectangleBounds(3500,3500, enemyPos), PointConverter.GeoPointToGenPoint(local));
        Enemy enemy = new Enemy(localBounds, new UnboundedArea());
        enemy.setLocation(enemyPos);
        SinusoidalMovement movement = new SinusoidalMovement(PointConverter.GeoPointToGenPoint(enemyPos));
        movement.setVelocity(5);
        movement.setAngleStep(0.1);
        movement.setAmplitude(10);
        enemy.setMovement(movement);
        Game.addToDisplayList(enemy);
        Game.addToUpdateList(enemy);*/
        //  -------------------------------------------

        currentEnnemy = new Enemy();
        currentEnnemy.setLocation(new GeoPoint(6.145606,46.209633));
        currentEnnemy.setPosition(PointConverter.GeoPointToGenPoint(currentEnnemy.getLocation()));
        game.addToDisplayList(currentEnnemy);
        game.addToUpdateList(currentEnnemy);

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
        mapApi.setMap(googleMap);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_UPDATES_REQUEST_CODE);
            return;
        }
        mapApi.onLocationUpdatesGranted();
        PlayerManager.setUser(new Player(6.1466, 46.1576, 20, "test", "test"));
        mapApi.updatePosition();
        initEnvironment();
        startGame();
        // Join
        firestoreUserData.joinLobby(PlayerManager.getUser());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == LOCATION_UPDATES_REQUEST_CODE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mapApi.onLocationUpdatesGranted();
            }
        }
    }

    public void showInventory(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_up,R.animator.slide_down);
        if(flag) {
            transaction.remove(inventoryFragment);
        } else {
            transaction.add(R.id.fragment_inventory_container, inventoryFragment);
        }
        flag = !flag;
        transaction.commit();
    }

    private Thread showGameInfoThread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(2000);
                        runOnUiThread(() -> {
                            if (PlayerManager.getUser() != null && PlayerManager.getUser().getHealthPoints() > 0) {
                                healthPointProgressBar.setProgress((int) Math.round(PlayerManager.getUser().getHealthPoints()));
                                healthPointText.setText(PlayerManager.getUser().getHealthPoints()+"/"+healthPointProgressBar.getMax());
                                username.setText(PlayerManager.getUser().getUsername());
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        return thread;
    }

    // Launches the game loop in another thread, must be destroyed at the end
    public static void startGame() {
        if (game != null) {
            game.initGame();
        }
    }

    public static void killGame() {
        if (game != null) {
            game.destroyGame();
        }
    }

}
