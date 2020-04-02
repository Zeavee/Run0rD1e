package ch.epfl.sdp.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
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

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.FirestoreUserData;
import ch.epfl.sdp.database.InitializeGameFirestore;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.item.InventoryFragment;
import ch.epfl.sdp.item.Scan;
import ch.epfl.sdp.leaderboard.LeaderboardActivity;
import ch.epfl.sdp.login.AuthenticationController;
import ch.epfl.sdp.login.FirebaseAuthentication;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {
    public static final int LOCATION_UPDATES_REQUEST_CODE = 101;
    public static final String lobbyCollectionName = "Lobby";
    public static final MapApi mapApi = new GoogleMapApi();
    public static final FirestoreUserData firestoreUserData = new FirestoreUserData();
    public static final AuthenticationController authenticationController = new FirebaseAuthentication(firestoreUserData);
    private static InventoryFragment inventoryFragment = new InventoryFragment();

    private static Game game;
    public static String currentUserEmail;
    public static Player currentUser;

    private TextView username, healthPointText;
    private ProgressBar healthPointProgressBar;

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Button mapButton = findViewById(R.id.recenter);
        mapButton.setOnClickListener(v -> mapApi.moveCameraOnCurrentLocation());

        Button scanButton = findViewById(R.id.scanButton);
        Scan scan = new Scan(new GeoPoint(9.34324, 47.24942), true, 30, mapApi);
        scanButton.setOnClickListener(v -> scan.showAllPlayers());

        findViewById(R.id.button_leaderboard).setOnClickListener(view -> startActivity(new Intent(MapsActivity.this, LeaderboardActivity.class)));

        username = findViewById(R.id.gameinfo_username_text);
        healthPointProgressBar = findViewById(R.id.gameinfo_healthpoint_progressBar);
        healthPointText = findViewById(R.id.gameinfo_healthpoint_text);
        username.setText("");

        mapApi.initializeApi((LocationManager) getSystemService(Context.LOCATION_SERVICE), this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);

        showGameInfoThread().start();

        game = new Game(mapApi, new InitializeGameFirestore());
        startGame();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        ((GoogleMapApi) mapApi).setMap(googleMap);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_UPDATES_REQUEST_CODE);
            return;
        }

        mapApi.onLocationUpdatesGranted();
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
                            if (currentUser != null && currentUser.getHealthPoints() > 0) {
                                healthPointProgressBar.setProgress((int) Math.round(currentUser.getHealthPoints()));
                                healthPointText.setText(currentUser.getHealthPoints()+"/"+healthPointProgressBar.getMax());
                                username.setText(currentUser.getUsername());
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
