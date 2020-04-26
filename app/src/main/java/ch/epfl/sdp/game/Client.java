package ch.epfl.sdp.game;

import java.util.List;

import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.database.firebase.api.ClientDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.EnemyForFirebase;
import ch.epfl.sdp.database.firebase.entity.EntityConverter;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.ItemBox;

/**
 * This class updates the game from the client point of view. It fetches the data from firebase and
 * the data is updated by the server.
 */
public class Client implements Updatable{
    private int counter;
    private double oldDamage;
    private List<ItemBox> itemBoxes;
    private ClientDatabaseAPI clientDatabaseAPI;
    private List<Enemy> enemies;

    /**
     * Creates a new client
     */
    public Client(){
        oldDamage = 0;
        counter = GameThread.FPS;
        Game.getInstance().addToUpdateList(this);
    }


    private void receivePlayersPosition(){

    };

    private ItemBox receiveItemBox(){
        return null;
    }

    private void receiveDamage(double damage){
        if (damage != oldDamage) {
            // shielding is done on server?
            PlayerManager.getCurrentUser().setHealthPoints(PlayerManager.getCurrentUser().getHealthPoints() - (damage - oldDamage));
            clientDatabaseAPI.sendHealthPoints(EntityConverter.PlayerToPlayerForFirebase(PlayerManager.getCurrentUser()), value -> {
                if(value.isSuccessful()){
                    oldDamage = damage;
                }
            });
        }
    }

    private void receiveEnemies(List<EnemyForFirebase> firebase_enemies){
        for (EnemyForFirebase enemy: firebase_enemies) {
            //enemy.
        }
    }

    /**
     * Puts the items of the taken item boxes in the user's inventory.
     */
    private void updateItems(){
        if (!itemBoxes.isEmpty()) {
            for (ItemBox box : itemBoxes) {
                box.take();
            }

            itemBoxes.clear();
        }
    }

    /**
     * Change the user's health based on the damage received.
     * Update on client for synchronisation with items.
     */
    private void updateHealth(){
        clientDatabaseAPI.fetchDamage(damage -> {receiveDamage(damage.getResult());});
    }

    /**
     * Update the players position.
     */
    private void updatePlayersPosition(){

    }

    /**
     * Update the enemies positions.
     */
    private void updateEnemiesPosition(){
        clientDatabaseAPI.fetchEnemies(enemies -> {receiveEnemies(enemies.getResult());});
    }

    @Override
    public void update() {
        if(counter <= 0){
            updateHealth();

            updateItems();
            updatePlayersPosition();
            updateEnemiesPosition();
            counter = GameThread.FPS + 1;
        }

        --counter;
    }
}
