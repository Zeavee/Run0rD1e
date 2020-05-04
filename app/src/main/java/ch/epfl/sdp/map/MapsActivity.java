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
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.Collection;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Renderer {
    private CommonDatabaseAPI commonDatabaseAPI;
    private AuthenticationAPI authenticationAPI;
    private static InventoryFragment inventoryFragment = new InventoryFragment();
    private LocationFinder locationFinder;

    private TextView username, healthPointText;
    private ProgressBar healthPointProgressBar;

    boolean flag = false;

    /**
     * A method to set a LocationFinder
     *
     * @param locationFinder the locationFinder we want to use
     */
    public void setLocationFinder(LocationFinder locationFinder) {
        this.locationFinder = locationFinder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Game.getInstance().setRenderer(this);

        commonDatabaseAPI = DependencyFactory.getCommonDatabaseAPI();
        authenticationAPI = DependencyFactory.getAuthenticationAPI();

        findViewById(R.id.button_leaderboard).setOnClickListener(view -> startActivity(new Intent(MapsActivity.this, LeaderboardActivity.class)));

        username = findViewById(R.id.gameinfo_username_text);
        healthPointProgressBar = findViewById(R.id.gameinfo_healthpoint_progressBar);
        healthPointText = findViewById(R.id.gameinfo_healthpoint_text);
        username.setText("");

        Button mapButton = findViewById(R.id.recenter);
        mapButton.setOnClickListener(v -> Game.getInstance().getMapApi().moveCameraOnLocation(locationFinder.getCurrentLocation()));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
        showGameInfoThread().start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        for (int res : grantResults) {
            if (res != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        locationFinder = new GoogleLocationFinder((LocationManager) getSystemService(Context.LOCATION_SERVICE));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Game.getInstance().setMapApi(new GoogleMapApi(googleMap));
        Game.getInstance().setRenderer(this);

        //Get email of CurrentUser;
        String email = authenticationAPI.getCurrentUserEmail();

        if (!Game.getInstance().isRunning()) {
            commonDatabaseAPI.fetchUser(email, fetchUserRes -> {
                if (!fetchUserRes.isSuccessful()) {
                    Toast.makeText(MapsActivity.this, fetchUserRes.getException().getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Player currentUser = EntityConverter.UserForFirebaseToPlayer(fetchUserRes.getResult());
                    PlayerManager.setCurrentUser(currentUser);
                    Game.getInstance().addToDisplayList(currentUser);

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                    } else {
                        locationFinder = new GoogleLocationFinder((LocationManager) getSystemService(Context.LOCATION_SERVICE));
                    }

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
        display(Game.getInstance().getDisplayablesOnce());
    }

    /**
     * A method that shows the inventory
     *
     * @param v the view of the inventory
     */
    public void showInventory(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_up, R.animator.slide_down);
        if (flag) {
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
                                healthPointText.setText(PlayerManager.getCurrentUser().getHealthPoints() + "/" + healthPointProgressBar.getMax());
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

    @Override
    public void display(Collection<Displayable> displayables) {
        runOnUiThread(() -> {
            for (Displayable displayable : displayables) {
                displayable.displayOn(Game.getInstance().getMapApi());
            }
        });
    }

    @Override
    public void unDisplay(Displayable displayable) {
        runOnUiThread(() -> {
            displayable.unDisplayOn(Game.getInstance().getMapApi());
        });
    }
}
