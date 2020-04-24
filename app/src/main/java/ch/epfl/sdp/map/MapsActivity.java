package ch.epfl.sdp.map;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.EntityConverter;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.Server;
import ch.epfl.sdp.item.InventoryFragment;
import ch.epfl.sdp.leaderboard.LeaderboardActivity;
import ch.epfl.sdp.login.AuthenticationAPI;
import ch.epfl.sdp.utils.DependencyFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    //private MapApi mapApi = new GoogleMapApi();
    private CommonDatabaseAPI commonDatabaseAPI;
    private AuthenticationAPI authenticationAPI;
    private static InventoryFragment inventoryFragment = new InventoryFragment();

    private TextView username, healthPointText;
    private ProgressBar healthPointProgressBar;

    boolean flag = false;

   /* public static void setMapApi(MapApi map) {
        mapApi = map;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        commonDatabaseAPI = DependencyFactory.getCommonDatabaseAPI();
        authenticationAPI = DependencyFactory.getAuthenticationAPI();

        Button mapButton = findViewById(R.id.recenter);
        mapButton.setOnClickListener(v -> Game.getInstance().getMapApi().moveCameraOnCurrentLocation());

        findViewById(R.id.button_leaderboard).setOnClickListener(view -> startActivity(new Intent(MapsActivity.this, LeaderboardActivity.class)));

        username = findViewById(R.id.gameinfo_username_text);
        healthPointProgressBar = findViewById(R.id.gameinfo_healthpoint_progressBar);
        healthPointText = findViewById(R.id.gameinfo_healthpoint_text);
        username.setText("");
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        Game.getInstance().setMapApi(new GoogleMapApi(googleMap));
        Game.getInstance().getMapApi().initializeApi((LocationManager) getSystemService(Context.LOCATION_SERVICE), this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
        showGameInfoThread().start();

        //Get email of CurrentUser;
        String email = authenticationAPI.getCurrentUserEmail();

        commonDatabaseAPI.fetchUser(email, fetchUserRes -> {
            if (!fetchUserRes.isSuccessful()) {
                Toast.makeText(MapsActivity.this, fetchUserRes.getException().getMessage(), Toast.LENGTH_LONG).show();
            } else {
                Player currentUser = EntityConverter.UserForFirebaseToPlayer(fetchUserRes.getResult());
                PlayerManager.setCurrentUser(currentUser);
                Game.getInstance().getMapApi().updatePosition();

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
                            if (PlayerManager.getCurrentUser() != null && PlayerManager.getCurrentUser().getHealthPoints() > 0) {
                                healthPointProgressBar.setProgress((int) Math.round(PlayerManager.getCurrentUser().getHealthPoints()));
                                healthPointText.setText(PlayerManager.getCurrentUser().getHealthPoints()+"/"+healthPointProgressBar.getMax());
                                username.setText(PlayerManager.getCurrentUser().getUsername());
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        return thread;
    }
}
