package ch.epfl.sdp.game;

import java.util.List;

import ch.epfl.sdp.database.firebase.ClientDatabaseAPI;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.item.ItemBox;

public class Client {
    List<ItemBox> itemBoxes;
    Player player;
    ClientDatabaseAPI clientDatabaseAPI;

    void receivePlayersPosition(){};
    ItemBox receiveItemBox(){
        return null;
    }
    double receiveDamage(){
        return 0;
    }
    void updateClient(){

    };
}
