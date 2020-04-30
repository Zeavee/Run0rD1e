package ch.epfl.sdp.game;

import java.util.List;

import ch.epfl.sdp.database.firebase.api.ClientDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.utils.EntityConverter;
import ch.epfl.sdp.entity.EnemyManager;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.utils.DependencyFactory;

/**
 * This class updates the game from the client point of view. It fetches the data from firebase and
 * the data is updated by the server.
 */
public class Client implements Updatable{
    private int counter;
    private double oldDamage;
    //private List<ItemBox> itemBoxes;
    private ClientDatabaseAPI clientDatabaseAPI;
    protected PlayerManager playerManager = PlayerManager.getInstance();

    /**
     * Creates a new client
     */
    public Client(){
        oldDamage = 0;
        counter = GameThread.FPS;
        Game.getInstance().addToUpdateList(this);
        clientDatabaseAPI = DependencyFactory.getClientDatabaseAPI();
    }

    public void initEnvironment() {
        clientDatabaseAPI.listenToGameStart(start -> {
            if(start.isSuccessful()){
                // ItemBox -------------------------------------------
                Healthpack healthpack = new Healthpack(10);
                ItemBox itemBox = new ItemBox();
                itemBox.putItems(healthpack, 1);
                itemBox.setLocation(new GeoPoint(6.14, 46.22));
                Game.getInstance().addToDisplayList(itemBox);
                Game.getInstance().addToUpdateList(itemBox);
                //  -------------------------------------------

                Game.getInstance().initGame();
            }
        });
    }

    private void receiveDamage(double damage) {
        Player player = playerManager.getCurrentUser();
        if(player.isShielded()) {
            return;
        }

        double newHealthPoints = playerManager.getCurrentUser().getHealthPoints() - damage;
        player.setHealthPoints(newHealthPoints);
        clientDatabaseAPI.sendHealthPoints(EntityConverter.playerToPlayerForFirebase(playerManager.getCurrentUser()), value -> {
            if(value.isSuccessful()){
                oldDamage = damage;
            }
        });
/*
        if (damage != oldDamage) {
            // shielding is done on server?

            double damageReceived = playerManager.getCurrentUser().getHealthPoints() - (damage - oldDamage);

            if(playerManager.getCurrentUser().isShielded()){
                damageReceived = 0;
            }

            playerManager.getCurrentUser().setHealthPoints(damageReceived);
            clientDatabaseAPI.sendHealthPoints(EntityConverter.playerToPlayerForFirebase(playerManager.getCurrentUser()), value -> {
                if(value.isSuccessful()){
                    oldDamage = damage;
                }
            });
        }
        */
    }

    private void receiveEnemies(List<EnemyForFirebase> firebase_enemies){
        for (EnemyForFirebase enemyForFirebase: firebase_enemies) {
            EnemyManager.getInstance().updateEnemies(enemyForFirebase.getId(), enemyForFirebase.getLocation());
        }
    }

    /**
     * Puts the items of the taken item boxes in the user's inventory.
     */
   /* private void updateItems(){
        if (!itemBoxes.isEmpty()) {
            for (ItemBox box : itemBoxes) {
                box.take();
            }

            itemBoxes.clear();
        }
    }*/

    /**
     * Change the user's health based on the damage received.
     * Update on client for synchronisation with items.
     */
    private void updateHealth(){
        clientDatabaseAPI.fetchDamage(damage -> {if(damage.isSuccessful()) receiveDamage(damage.getResult());});
    }

    /**
     * Update the players position.
     */
    private void updatePlayersPosition(){
        clientDatabaseAPI.fetchPlayers(listPlayer -> {if(listPlayer.isSuccessful()) playerManager.updatePlayersPosition(listPlayer.getResult());});
        clientDatabaseAPI.updateLocation(EntityConverter.playerToPlayerForFirebase(playerManager.getCurrentUser()), value -> {});
    }

    /**
     * Update the enemies positions.
     */
    private void updateEnemiesPosition(){
        clientDatabaseAPI.fetchEnemies(enemies -> {if(enemies.isSuccessful()) receiveEnemies(enemies.getResult());});
    }

    @Override
    public void update() {
        if(counter <= 0){
            updateHealth();

            //updateItems();
            updatePlayersPosition();
            updateEnemiesPosition();
            counter = GameThread.FPS + 1;
        }

        --counter;
    }
}
