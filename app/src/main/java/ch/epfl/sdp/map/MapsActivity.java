package ch.epfl.sdp.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.R;
import ch.epfl.sdp.WeatherFragment;
import ch.epfl.sdp.database.authentication.AuthenticationAPI;
import ch.epfl.sdp.database.firebase.api.ClientDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.ServerDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.EntityConverter;
import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Client;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.Server;
import ch.epfl.sdp.item.InventoryFragment;
import ch.epfl.sdp.leaderboard.LeaderboardActivity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Renderer {
    private CommonDatabaseAPI commonDatabaseAPI;
    private AuthenticationAPI authenticationAPI;
    private static InventoryFragment inventoryFragment = new InventoryFragment();
    private static WeatherFragment weatherFragment = new WeatherFragment();
    private ServerDatabaseAPI serverDatabaseAPI;
    private ClientDatabaseAPI clientDatabaseAPI;
    private LocationFinder locationFinder;

    private TextView username, healthPointText, timerShrinking;
    private ProgressBar healthPointProgressBar;
  
    private boolean flagInventory = false;
    private boolean flagWeather = false;
  
    private PlayerManager playerManager = PlayerManager.getInstance();

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

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        authenticationAPI = appContainer.authenticationAPI;
        commonDatabaseAPI = appContainer.commonDatabaseAPI;
        serverDatabaseAPI = appContainer.serverDatabaseAPI;
        clientDatabaseAPI = appContainer.clientDatabaseAPI;

        findViewById(R.id.button_leaderboard).setOnClickListener(view -> startActivity(new Intent(MapsActivity.this, LeaderboardActivity.class)));

        username = findViewById(R.id.gameinfo_username_text);
        healthPointProgressBar = findViewById(R.id.gameinfo_healthpoint_progressBar);
        healthPointText = findViewById(R.id.gameinfo_healthpoint_text);
        username.setText("");

        Button mapButton = findViewById(R.id.recenter);
        mapButton.setOnClickListener(v -> Game.getInstance().getMapApi().moveCameraOnLocation(locationFinder.getCurrentLocation()));

        Button weather = findViewById(R.id.button_weather);
        weather.setOnClickListener(v ->  {
            showFragment(weatherFragment, R.id.fragment_weather_container, flagWeather);
            flagWeather = ! flagWeather;
        });

        Button inventory = findViewById(R.id.button_inventory);
        inventory.setOnClickListener(v ->  {
            showFragment(inventoryFragment, R.id.fragment_inventory_container, flagInventory);
            flagInventory = ! flagInventory;
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
        showGameInfoThread().start();

        timerShrinking = findViewById(R.id.timerShrinking);
        Game.getInstance().areaShrinker.setTextViewAndActivity(timerShrinking, this);
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

        Log.d("Database", "Game running is " + Game.getInstance().isRunning());

        if (!Game.getInstance().isRunning()) {
            commonDatabaseAPI.fetchUser(email, fetchUserRes -> {
                if (fetchUserRes.isSuccessful()) {
                    Player currentUser = EntityConverter.userForFirebaseToPlayer(fetchUserRes.getResult());
                    playerManager.setCurrentUser(currentUser);
                    Game.getInstance().addToDisplayList(currentUser);
                    Log.d("Database", "User fetched");

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                    } else {
                        locationFinder = new GoogleLocationFinder((LocationManager) getSystemService(Context.LOCATION_SERVICE));
                    }

                    commonDatabaseAPI.selectLobby(selectLobbyRes -> {
                        if (selectLobbyRes.isSuccessful()) {
                            PlayerForFirebase playerForFirebase = EntityConverter.playerToPlayerForFirebase(playerManager.getCurrentUser());
                            Map<String, Object> data = new HashMap<>();
                            data.put("count", playerManager.getNumPlayersBeforeJoin() + 1);
                            if (playerManager.isServer()) data.put("startGame", false);
                            Log.d("Database", "Lobby selected:" + playerManager.getLobbyDocumentName());
                            joinLobby(playerForFirebase, data);
                        } else {
                            Toast.makeText(MapsActivity.this, selectLobbyRes.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(MapsActivity.this, fetchUserRes.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        Log.d("Database", "Quit map ready");
    }

    private void joinLobby(PlayerForFirebase playerForFirebase, Map<String, Object> lobbyData) {
        commonDatabaseAPI.registerToLobby(playerForFirebase, lobbyData, registerToLobbyRes -> {
            if (registerToLobbyRes.isSuccessful()) {
                if (playerManager.isServer()) {
                    serverDatabaseAPI.setLobbyRef(playerManager.getLobbyDocumentName());
                    new Server(serverDatabaseAPI);
                } else {
                    clientDatabaseAPI.setLobbyRef(playerManager.getLobbyDocumentName());
                    new Client(clientDatabaseAPI);
                }

                Log.d("Database", "Lobby registered/joined");

            } else {
                Toast.makeText(MapsActivity.this, registerToLobbyRes.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showFragment(Fragment fragment, int containerId, boolean flag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_up, R.animator.slide_down);
        if (flag) {
            transaction.remove(fragment);
        } else {
            transaction.add(containerId, fragment);
        }
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
                            if (playerManager.getCurrentUser() != null) {
                                healthPointProgressBar.setProgress((int) Math.round(playerManager.getCurrentUser().getHealthPoints()));
                                healthPointText.setText(playerManager.getCurrentUser().getHealthPoints() + "/" + healthPointProgressBar.getMax());
                                username.setText(playerManager.getCurrentUser().getUsername());
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
