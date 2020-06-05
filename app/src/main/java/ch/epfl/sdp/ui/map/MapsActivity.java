package ch.epfl.sdp.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Pair;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.authentication.AuthenticationAPI;
import ch.epfl.sdp.database.firebase.api.ClientDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.ServerDatabaseAPI;
import ch.epfl.sdp.database.firebase.entityForFirebase.EntityConverter;
import ch.epfl.sdp.database.firebase.entityForFirebase.PlayerForFirebase;
import ch.epfl.sdp.entities.player.Player;
import ch.epfl.sdp.entities.player.PlayerManager;
import ch.epfl.sdp.game.game_architecture.Client;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.game_architecture.Server;
import ch.epfl.sdp.game.game_architecture.Solo;
import ch.epfl.sdp.items.item_box.ItemBox;
import ch.epfl.sdp.items.item_box.ItemBoxManager;
import ch.epfl.sdp.items.money.Market;
import ch.epfl.sdp.map.display.Displayable;
import ch.epfl.sdp.map.display.GoogleMapApi;
import ch.epfl.sdp.map.display.Renderer;
import ch.epfl.sdp.map.display.TimerUI;
import ch.epfl.sdp.map.location.GoogleLocationFinder;
import ch.epfl.sdp.map.location.LocationFinder;
import ch.epfl.sdp.ui.game.GameOverActivity;
import ch.epfl.sdp.ui.game.MarketActivity;
import ch.epfl.sdp.utils.AppContainer;
import ch.epfl.sdp.utils.JunkCleaner;
import ch.epfl.sdp.utils.MyApplication;
import ch.epfl.sdp.utils.ObjectWrapperForBinder;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Renderer, TimerUI {
    private String playMode = "";

    private AuthenticationAPI authenticationAPI;
    private CommonDatabaseAPI commonDatabaseAPI;
    private ServerDatabaseAPI serverDatabaseAPI;
    private ClientDatabaseAPI clientDatabaseAPI;

    private LocationFinder locationFinder;

    private final static InventoryFragment inventoryFragment = new InventoryFragment();
    private final WeatherFragment weatherFragment = new WeatherFragment();
    private final static CurrentGameLeaderBoardFragment ingameLeaderboardFragment = new CurrentGameLeaderBoardFragment();

    private TextView username, healthPointText, timerShrinking, moneyText;
    private ProgressBar healthPointProgressBar;

    private boolean flagInventory = false;
    private boolean flagWeather = false;
    private boolean flagIngameLeaderboard = false;
    public boolean flagGameOver = false;

    private final PlayerManager playerManager = PlayerManager.getInstance();


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

        Intent intent = getIntent();
        playMode = intent.getStringExtra("playMode");

        boolean isSolo = playMode.equals("single-player");

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        authenticationAPI = appContainer.authenticationAPI;
        commonDatabaseAPI = appContainer.commonDatabaseAPI;
        serverDatabaseAPI = appContainer.serverDatabaseAPI;
        clientDatabaseAPI = appContainer.clientDatabaseAPI;

        if (Game.getInstance().gameStarted && PlayerManager.getInstance().isSoloMode() != isSolo) {
            JunkCleaner.clearAllAndListeners(appContainer);
        }

        Game.getInstance().setRenderer(this);

        username = findViewById(R.id.gameinfo_username_text);
        healthPointProgressBar = findViewById(R.id.gameinfo_healthpoint_progressBar);
        healthPointText = findViewById(R.id.gameinfo_healthpoint_text);
        moneyText = findViewById(R.id.gameinfo_money_text);
        username.setText("");

        initViews();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(MapsActivity.this);
        showGameInfoThread().start();

        timerShrinking = findViewById(R.id.timerShrinking);
        Game.getInstance().areaShrinker.setTimerUI(this);
    }

    private void initViews() {
        findViewById(R.id.recenter).setOnClickListener(v -> Game.getInstance().getMapApi().moveCameraOnLocation(locationFinder.getCurrentLocation()));
        setupFragmentButton(R.id.button_weather, new Pair<>(weatherFragment, R.id.fragment_weather_container), flagWeather, () -> flagWeather = !flagWeather);
        setupFragmentButton(R.id.button_inventory, new Pair<>(inventoryFragment, R.id.fragment_inventory_container), flagInventory, () -> flagInventory = !flagInventory);
        setupFragmentButton(R.id.button_leaderboard, new Pair<>(ingameLeaderboardFragment, R.id.fragment_ingame_leaderboard_container), flagIngameLeaderboard, () -> flagIngameLeaderboard = !flagIngameLeaderboard);
    }

    private void setupFragmentButton(int button, Pair<Fragment, Integer> fragment, boolean flag, Runnable changeFlag) {
        findViewById(button).setOnClickListener(v -> {
            showFragment(fragment.first, fragment.second, flag);
            changeFlag.run();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
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
     * @param googleMap the instance of the Google Map
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Game.getInstance().setMapApi(new GoogleMapApi(googleMap));
        Game.getInstance().setRenderer(this);
        String email = authenticationAPI.getCurrentUserEmail();

        if (!Game.getInstance().isRunning()) {
            commonDatabaseAPI.fetchUser(email, fetchUserRes -> {
                if (fetchUserRes.isSuccessful()) {
                    Player currentUser = EntityConverter.userForFirebaseToPlayer(fetchUserRes.getResult());
                    playerManager.setCurrentUser(currentUser);
                    Game.getInstance().addToDisplayList(currentUser);
                    initPlayMode();
                } else {
                    Toast.makeText(MapsActivity.this, fetchUserRes.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            // reDisplay the itemBox when resume the map
            for (ItemBox itemBox : ItemBoxManager.getInstance().getItemBoxes().values()) {
                itemBox.setReDisplay(true);
            }
        }
        initLocationFinder();
    }

    private void initPlayMode() {
        if (playMode.equals("single-player")) {
            // Set the soloMode in playerManager to be true
            playerManager.setSoloMode(true);

            // Only in multiPlayer mode and the currentUser is the first one join the lobby the isServer will be true
            playerManager.setIsServer(false);

            // Create a soloMode instance and start the game
            Game.getInstance().startGameController = new Solo(commonDatabaseAPI);
        } else if (playMode.equals("multi-player")) {
            // Set the soloMode in the playerManager to be false
            playerManager.setSoloMode(false);

            // select the lobby in cloud firebase which has less than the number of players required to play the game, then currentUser is Client
            // If all the lobbies are full, create a new lobby in the cloud firebase and then the currentUser is Server
            selectLobby();
        } else {
            Toast.makeText(MapsActivity.this, "No such play mode", Toast.LENGTH_LONG).show();
        }
    }

    private void selectLobby() {
        commonDatabaseAPI.selectLobby(selectLobbyRes -> {
            if (selectLobbyRes.isSuccessful()) {
                createPlayerInLobby();
            } else {
                Toast.makeText(MapsActivity.this, selectLobbyRes.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createPlayerInLobby() {
        PlayerForFirebase playerForFirebase = EntityConverter.playerToPlayerForFirebase(playerManager.getCurrentUser());
        Map<String, Object> data = new HashMap<>();

        if (!playerManager.isInLobby()) {
            data.put("players", playerManager.getNumPlayersInLobby() + 1);
        }

        joinLobby(playerForFirebase, data);
    }

    private void joinLobby(PlayerForFirebase playerForFirebase, Map<String, Object> lobbyData) {
        commonDatabaseAPI.registerToLobby(playerForFirebase, lobbyData, registerToLobbyRes -> {
            if (registerToLobbyRes.isSuccessful()) {
                if (playerManager.isServer()) {
                    serverDatabaseAPI.setLobbyRef(playerManager.getLobbyDocumentName());
                    Game.getInstance().startGameController = new Server(serverDatabaseAPI, commonDatabaseAPI, this::endGame);
                } else {
                    clientDatabaseAPI.setLobbyRef(playerManager.getLobbyDocumentName());
                    Game.getInstance().startGameController = new Client(clientDatabaseAPI, commonDatabaseAPI, this::endGame);
                }
            } else {
                Toast.makeText(MapsActivity.this, registerToLobbyRes.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initLocationFinder() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        } else {
            locationFinder = new GoogleLocationFinder((LocationManager) getSystemService(Context.LOCATION_SERVICE));
        }
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
        return new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(2000);
                        runOnUiThread(() -> {
                            if (playerManager.getCurrentUser() != null) {
                                healthPointProgressBar.setProgress((int) Math.round(playerManager.getCurrentUser().status.getHealthPoints()));
                                String newText = playerManager.getCurrentUser().status.getHealthPoints() + "/" + healthPointProgressBar.getMax();
                                healthPointText.setText(newText);
                                username.setText(playerManager.getCurrentUser().getUsername());
                                moneyText.setText(String.format(Locale.ENGLISH, "%d", playerManager.getCurrentUser().wallet.getMoney(playerManager.getCurrentUser())));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
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
        runOnUiThread(() -> displayable.unDisplayOn(Game.getInstance().getMapApi()));
    }

    /**
     * switches to a market activity, where user can buy health, shield, phantom, or shrinker items
     */
    public void startMarket(Market backend) {
        final Bundle bundle = new Bundle();
        bundle.putBinder("object_value", new ObjectWrapperForBinder<>(backend));
        startActivity(new Intent(this, MarketActivity.class).putExtras(bundle));
    }

    /**
     * This method starts the game over activity
     */
    public void endGame() {
        startActivity(new Intent(MapsActivity.this, GameOverActivity.class));
        finish();
        flagGameOver = true;
    }

    @Override
    public void displayTime(String timeAsString) {
        runOnUiThread(() -> timerShrinking.setText(timeAsString));
    }
}