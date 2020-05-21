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
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.InventoryFragment;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.item.ItemBoxManager;
import ch.epfl.sdp.leaderboard.CurrentGameLeaderboardFragment;
import ch.epfl.sdp.market.Market;
import ch.epfl.sdp.market.MarketActivity;
import ch.epfl.sdp.market.ObjectWrapperForBinder;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Renderer {
    private CommonDatabaseAPI commonDatabaseAPI;
    private AuthenticationAPI authenticationAPI;
    private static InventoryFragment inventoryFragment = new InventoryFragment();
    private static WeatherFragment weatherFragment = new WeatherFragment();
    private static CurrentGameLeaderboardFragment ingameLeaderboardFragment = new CurrentGameLeaderboardFragment();
    private ServerDatabaseAPI serverDatabaseAPI;
    private ClientDatabaseAPI clientDatabaseAPI;
    private LocationFinder locationFinder;

    private TextView username, healthPointText, timerShrinking, moneyText;
    private ProgressBar healthPointProgressBar;

    private boolean flagInventory = false;
    private boolean flagWeather = false;
    private boolean flagIngameLeaderboard = false;

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

        username = findViewById(R.id.gameinfo_username_text);
        healthPointProgressBar = findViewById(R.id.gameinfo_healthpoint_progressBar);
        healthPointText = findViewById(R.id.gameinfo_healthpoint_text);
        moneyText = findViewById(R.id.gameinfo_money_text);
        username.setText("");

        Button mapButton = findViewById(R.id.recenter);
        mapButton.setOnClickListener(v -> Game.getInstance().getMapApi().moveCameraOnLocation(locationFinder.getCurrentLocation()));

        Button weather = findViewById(R.id.button_weather);
        weather.setOnClickListener(v -> {
            showFragment(weatherFragment, R.id.fragment_weather_container, flagWeather);
            flagWeather = !flagWeather;
        });

        Button inventory = findViewById(R.id.button_inventory);
        inventory.setOnClickListener(v -> {
            showFragment(inventoryFragment, R.id.fragment_inventory_container, flagInventory);
            flagInventory = !flagInventory;
        });

        findViewById(R.id.button_leaderboard).setOnClickListener(view -> {
            showFragment(ingameLeaderboardFragment, R.id.fragment_ingame_leaderboard_container, flagIngameLeaderboard);
            flagIngameLeaderboard = !flagIngameLeaderboard;
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

    /**
     * on Travis the map will not appear since it is not connected via API Key
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Game.getInstance().setMapApi(new GoogleMapApi(googleMap));
        Game.getInstance().setRenderer(this);

        //Get email of CurrentUser;
        String email = authenticationAPI.getCurrentUserEmail();

        Log.d("Inside MapsActivity", "Game running is " + Game.getInstance().isRunning());

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
        } else {
            // reDisplay the itemBox when resume the map
            for(ItemBox itemBox: ItemBoxManager.getInstance().getItemBoxes().values()) {
                itemBox.setReDisplay(true);
            }
        }

        Log.d("Database", "Quit map ready");
    }

    private void joinLobby(PlayerForFirebase playerForFirebase, Map<String, Object> lobbyData) {
        commonDatabaseAPI.registerToLobby(playerForFirebase, lobbyData, registerToLobbyRes -> {
            if (registerToLobbyRes.isSuccessful()) {
                Log.d("Database", "Lobby registered/joined");
                if (playerManager.isServer()) {
                    serverDatabaseAPI.setLobbyRef(playerManager.getLobbyDocumentName());
                    Server server = new Server(serverDatabaseAPI, commonDatabaseAPI);
                    server.start();

                } else {
                    clientDatabaseAPI.setLobbyRef(playerManager.getLobbyDocumentName());
                    Client client = new Client(clientDatabaseAPI, commonDatabaseAPI);
                    client.start();
                }
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
                                moneyText.setText(String.format("%d", playerManager.getCurrentUser().getMoney()));
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
                if (displayable instanceof Market) {
                    ((Market) displayable).setCallingActivity(this);
                }
                displayable.displayOn(Game.getInstance().getMapApi());
            }
        });
    }

    @Override
    public void unDisplay(Displayable displayable) {
        runOnUiThread(() -> displayable.unDisplayOn(Game.getInstance().getMapApi()));
    }

    /**
     * switches to a market activity, where user can buy health, shield, scan, or shrinker items
     */
    public void startMarket(Market backend) {
        Log.d("MapsActivity", "start market");

        final Bundle bundle = new Bundle();
        bundle.putBinder("object_value", new ObjectWrapperForBinder<>(backend));
        startActivity(new Intent(this, MarketActivity.class).putExtras(bundle));

    }
}
