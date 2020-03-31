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
import ch.epfl.sdp.database.FirestoreUserData;
import ch.epfl.sdp.database.UserDataController;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.Scan;
import ch.epfl.sdp.login.AuthenticationController;
import ch.epfl.sdp.login.FirebaseAuthentication;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
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
        mapApi.updatePosition();
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
