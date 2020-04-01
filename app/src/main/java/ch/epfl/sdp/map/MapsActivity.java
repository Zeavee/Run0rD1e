package ch.epfl.sdp.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import ch.epfl.sdp.R;
import ch.epfl.sdp.database.FirestoreUserData;
import ch.epfl.sdp.database.UserDataController;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.item.InventoryFragment;
import ch.epfl.sdp.item.Scan;
import ch.epfl.sdp.leaderboard.LeaderboardActivity;
import ch.epfl.sdp.login.AuthenticationController;
import ch.epfl.sdp.login.FirebaseAuthentication;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {
    public static final int LOCATION_UPDATES_REQUEST_CODE = 101;

    public static final MapApi mapApi = new GoogleMapApi();
    private TextView username, healthPointText;
    private ProgressBar healthPointProgressBar;
    private Handler handler = new Handler();

    boolean flag = false;

    // Hardcoded at this stage, later will use currentUser in game
    Player player = new Player(6.149290,
            46.212470,
            50,
            "admin",
            "admin@epfl.ch");

    public static final UserDataController firestoreUserData = new FirestoreUserData();
    public static final AuthenticationController authenticationController = new FirebaseAuthentication(firestoreUserData);

    // Use the email as the key to identify the CurrentUser in the List of players
    public static String emailOfCurrentUser = authenticationController.getEmailOfCurrentUser();

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


        Button inventoryButton = findViewById(R.id.button_inventory);
        InventoryFragment inventoryFragment = new InventoryFragment();
        inventoryButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.animator.slide_up,R.animator.slide_down);
            if(flag) {
                transaction.remove(inventoryFragment);
            } else {
                transaction.add(R.id.fragment_inventory_container, inventoryFragment);
            }
            flag = !flag;
            transaction.commit();
        });

        username = findViewById(R.id.gameinfo_username_text);
        healthPointProgressBar = findViewById(R.id.gameinfo_healthpoint_progressBar);
        healthPointText = findViewById(R.id.gameinfo_healthpoint_text);
        username.setText(player.username);

        mapApi.initializeApi((LocationManager) getSystemService(Context.LOCATION_SERVICE), this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);

        Thread thread = showGameInfoThread();
        thread.start();

//        // just to test the fetch data from cloud firebase GetLobby function and StoreUser function
//        Thread thread = test();
//        thread.start();
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

    private Thread showGameInfoThread() {
        Thread thread = new Thread(() -> {
            while (player.getHealthPoints() > 0) {
                // Update the progress bar and display the
                //current value in the text view
                handler.post(() -> {
                    healthPointProgressBar.setProgress((int) Math.round(player.getHealthPoints()));
                    healthPointText.setText(player.getHealthPoints()+"/"+healthPointProgressBar.getMax());
                });
                try {
                    // Sleep for 200 milliseconds.
                    Thread.sleep(2000);
                    player.setHealthPoints(55);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return thread;
    }
//    /**
//     * just to test the fetch data from cloud firebase GetLobby function and StoreUser function
//     *
//     * @return
//     */
//    private Thread test() {
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                // just to test the fetch data from cloud firebase function and StoreUser function
//                firestoreUserData.getLobby("Users");
//                // Since fetching the data is asynchronized, for the purpose of test, just wait
//                while(playerManager.getPlayers().size() != 7) {}
//
//                if(emailOfCurrentUser == null ){}
//                else{
//                    Player currentUser = playerManager.getPlayer(emailOfCurrentUser);
//
//                    //Just for the purpose of test, check this value on Cloudfirebase website
//                    currentUser.healthPoints = 44.6;
//                    firestoreUserData.storeUser("Users", currentUser);
//                }
//            }
//        };
//        return thread;
//    }
}
