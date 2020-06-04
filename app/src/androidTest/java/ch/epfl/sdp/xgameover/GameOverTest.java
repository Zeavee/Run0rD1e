package ch.epfl.sdp.xgameover;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.R;
import ch.epfl.sdp.artificial_intelligence.Behaviour;
import ch.epfl.sdp.database.firebase.ClientMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.CommonMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.ServerMockDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.ServerDatabaseAPI;
import ch.epfl.sdp.database.firebase.entityForFirebase.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.GeoPointForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.ItemBoxForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.ItemsForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entityForFirebase.UserForFirebase;
import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.Server;
import ch.epfl.sdp.geometry.AreaShrinker;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;
import ch.epfl.sdp.market.Market;
import ch.epfl.sdp.utils.MockMap;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GameOverTest {
    private Player client;

    private ServerMockDatabaseAPI serverMockDatabaseAPI;
    private ClientMockDatabaseAPI clientMockDatabaseAPI;
    private CommonMockDatabaseAPI commonMockDatabaseAPI;

    @Rule
    public final ActivityTestRule<MapsActivity> mActivityTestRule = new ActivityTestRule<MapsActivity>(MapsActivity.class) {

        @Override
        public void beforeActivityLaunched() {
            setupEnvironment();
            AppContainer appContainer = ((MyApplication) ApplicationProvider.getApplicationContext()).appContainer;
            appContainer.commonDatabaseAPI = commonMockDatabaseAPI;
            appContainer.serverDatabaseAPI = serverMockDatabaseAPI;
            appContainer.clientDatabaseAPI = clientMockDatabaseAPI;
        }

        // start the game engine MANUALLY
        @Override
        public void afterActivityLaunched() {
            getActivity().setLocationFinder(() -> new GeoPoint(6.14, 47.22));
            Game.getInstance().initGame();
        }

        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            intent.putExtra("playMode", "multi-player");
            return intent;
        }
    };

    // check "Game Ovr" is displayed
    @Test
    public void serverLosesIfDead() {
        checkIfTextIsDisplayedAfterGameOver(PlayerManager.getInstance().getCurrentUser(), "Game 0vr");
    }

    // check "Y0u w0n!" is displayed
    @Test
    public void serverWinsIfAlone() throws InterruptedException {
        checkIfTextIsDisplayedAfterGameOver(client, "Y0u w0n!");
    }

    private void checkIfTextIsDisplayedAfterGameOver(Player player, String text) {
        player.setHealthPoints(0);
        // wait a moment for the splash screen to be intended
        while (!mActivityTestRule.getActivity().flagGameOver) {
            ((Server) Game.getInstance().startGameController).update();
        };
        ViewInteraction textView = onView(withId(R.id.gameOverText));
        textView.check(matches(withText(text)));
        onView(withId(R.id.backFromGameOver)).perform(click());
        onView(withId(R.id.solo)).check(matches(isDisplayed()));
    }

    private void setupEnvironment() {
        Game.getInstance().setMapApi(new MockMap());
        Game.getInstance().setRenderer(new MockMap());

        Player server = new Player("server", "server@gmail.com");
        server.setLocation(new GeoPoint(33.001, 33));
        PlayerManager.getInstance().setCurrentUser(server);
        PlayerManager.getInstance().setIsServer(true);

        Map<String, UserForFirebase> userForFirebaseMap = new HashMap<>();
        Map<String, PlayerForFirebase> playerForFirebaseMap = new HashMap<>();
        List<EnemyForFirebase> enemyForFirebaseList = new ArrayList<>();
        List<ItemBoxForFirebase> itemBoxForFirebaseList = new ArrayList<>();
        Map<String, ItemsForFirebase> usedItems = new HashMap<>();
        Map<String, ItemsForFirebase> items = new HashMap<>();

        /*
         * populate All Users
         */
        UserForFirebase userForFirebase0 = new UserForFirebase("server@gmail.com", "server", 100);
        UserForFirebase userForFirebase1 = new UserForFirebase("client@gmail.com", "client", 0);
        UserForFirebase userForFirebase2 = new UserForFirebase("test@gmail.com", "test", 0);
        userForFirebaseMap.put(userForFirebase0.getEmail(), userForFirebase0);
        userForFirebaseMap.put(userForFirebase1.getEmail(), userForFirebase1);
        userForFirebaseMap.put(userForFirebase2.getEmail(), userForFirebase2);


        /*
         * polulate the players in lobby
         */
        PlayerForFirebase playerForFirebase0 = new PlayerForFirebase();
        playerForFirebase0.setUsername("server");
        playerForFirebase0.setEmail("server@gmail.com");
        playerForFirebase0.setGeoPointForFirebase(new GeoPointForFirebase(33.001, 33));
        playerForFirebase0.setAoeRadius(22.0);
        playerForFirebase0.setHealthPoints(20.0);
        playerForFirebase0.setCurrentGameScore(0);


        client = new Player("server", "server@gmail.com");
        PlayerManager.getInstance().addPlayer(client);

        PlayerForFirebase playerForFirebase1 = new PlayerForFirebase();
        playerForFirebase1.setUsername("client");
        playerForFirebase1.setEmail("client@gmail.com");
        playerForFirebase1.setGeoPointForFirebase(new GeoPointForFirebase(33, 33));
        playerForFirebase1.setAoeRadius(22.0);
        playerForFirebase1.setHealthPoints(20.0);
        playerForFirebase1.setCurrentGameScore(0);

        playerForFirebaseMap.put(playerForFirebase0.getEmail(), playerForFirebase0);
        playerForFirebaseMap.put(playerForFirebase1.getEmail(), playerForFirebase1);

        /*
         *  Populate the enemy in lobby
         */
        EnemyForFirebase enemyForFirebase = new EnemyForFirebase(0, Behaviour.WAIT, new GeoPointForFirebase(22, 22), 0);
        enemyForFirebaseList.add(enemyForFirebase);

        /*
         *  Populate the itemBox in lobby
         */
        ItemBoxForFirebase itemBoxForFirebase0 = new ItemBoxForFirebase("itembox0", new GeoPointForFirebase(22, 22), false);
        ItemBoxForFirebase itemBoxForFirebase1 = new ItemBoxForFirebase("itembox1", new GeoPointForFirebase(23, 23), true);

        itemBoxForFirebaseList.add(itemBoxForFirebase0);
        itemBoxForFirebaseList.add(itemBoxForFirebase1);

        /*
         * Populate the usedItem for each player in the lobby
         */
        Map<String, Integer> itemsMap = new HashMap<>();
        itemsMap.put("Healthpack 10", 2);
        ItemsForFirebase itemsForFirebase = new ItemsForFirebase(itemsMap, new Date(System.currentTimeMillis()));
        usedItems.put("server@gmail.com", itemsForFirebase);
        PlayerManager.getInstance().getCurrentUser().getInventory().setItems(itemsMap);

        serverMockDatabaseAPI = new ServerMockDatabaseAPI();
        commonMockDatabaseAPI = new CommonMockDatabaseAPI(new HashMap<>(), new ArrayList<>());
        clientMockDatabaseAPI = new ClientMockDatabaseAPI();
        serverMockDatabaseAPI.hardCodedInit(userForFirebaseMap, playerForFirebaseMap, enemyForFirebaseList, itemBoxForFirebaseList, usedItems, items);
        commonMockDatabaseAPI.hardCodedInit(userForFirebaseMap, playerForFirebaseMap);
    }
}
