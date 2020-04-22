package ch.epfl.sdp.map;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.FragmentActivity;
import ch.epfl.sdp.R;
import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.EntityConverter;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Server;
import ch.epfl.sdp.login.AuthenticationAPI;
import ch.epfl.sdp.utils.DependencyFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static MapApi mapApi = new GoogleMapApi();
    private CommonDatabaseAPI commonDatabaseAPI;
    private AuthenticationAPI authenticationAPI;

    public static void setMapApi(MapApi map) {
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



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapApi.setMap(googleMap);


        //Get email of CurrentUser;
        String email = authenticationAPI.getCurrentUserEmail();

        commonDatabaseAPI.fetchUser(email, fetchUserRes -> {
            if (!fetchUserRes.isSuccessful()) {
                Toast.makeText(MapsActivity.this, fetchUserRes.getException().getMessage(), Toast.LENGTH_LONG).show();
            } else {
                Player currentUser = EntityConverter.UserForFirebaseToPlayer(fetchUserRes.getResult());
                PlayerManager.setCurrentUser(currentUser);
                mapApi.updatePosition();

                commonDatabaseAPI.selectLobby(selectLobbyRes -> {
                    if (!selectLobbyRes.isSuccessful()) {
                        Toast.makeText(MapsActivity.this, selectLobbyRes.getException().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        PlayerForFirebase playerForFirebase = EntityConverter.PlayerToPlayerForFirebase(PlayerManager.getCurrentUser());
                        Map<String, Object> data = new HashMap<>();
                        data.put("count", PlayerManager.getNumPlayersBeforeJoin() + 1);
                        if (PlayerManager.isServer()) data.put("startGame", false);

                        commonDatabaseAPI.registerToLobby(playerForFirebase, data, registerToLobbyRes -> {
                            if (!registerToLobbyRes.isSuccessful()) {
                                Toast.makeText(MapsActivity.this, registerToLobbyRes.getException().getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                Server.initEnvironment();
                            }
                        });
                    }
                });
            }

        });
    }
}
