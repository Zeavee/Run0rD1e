package ch.epfl.sdp.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import ch.epfl.sdp.R;
import ch.epfl.sdp.database.FirestoreUserData;
import ch.epfl.sdp.database.UserDataController;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.Scan;
import ch.epfl.sdp.login.AuthenticationController;
import ch.epfl.sdp.login.FirebaseAuthentication;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {
    public static final int LOCATION_UPDATES_REQUEST_CODE = 101;

    public static final MapApi mapApi = new GoogleMapApi();
//    public static final UserDataController firestoreUserData = new FirestoreUserData();
//    public static final AuthenticationController authenticationController = new FirebaseAuthentication(firestoreUserData);
//
//    // Use the email as the key to identify the CurrentUser in the List of players
//    public static String emailOfCurrentUser = authenticationController.getEmailOfCurrentUser();

    // PlayerManager
    public static PlayerManager playerManager = new PlayerManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Button mapButton = findViewById(R.id.recenter);
        mapButton.setOnClickListener(v -> mapApi.moveCameraOnCurrentLocation());

        Button scanButton = findViewById(R.id.scanButton);
        Scan scan = new Scan(new GeoPoint(9.34324, 47.24942), true, 30, mapApi);
        scanButton.setOnClickListener(v -> scan.showAllPlayers());

        mapApi.initializeApi((LocationManager) getSystemService(Context.LOCATION_SERVICE), this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);

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
