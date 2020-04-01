package ch.epfl.sdp.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.item.Scan;
import ch.epfl.sdp.item.Shield;
import ch.epfl.sdp.item.Shrinker;
import ch.epfl.sdp.item.TimedItem;

public class Inventory {
    private Player player;
    private HashMap<String, Integer> items;

    public Inventory(Player player) {
        this.player = player;
        this.items = new HashMap<>();
    }

    public void useItem(String itemName) {
        if (items.get(itemName) > 0) {
            removeItem(itemName);
            switch (itemName) {
                case "Healthpack":
                    player.setHealthPoints(Player.MAX_HEALTH);
                    break;
                case "Shield":
                    Game.addToUpdateList(new Shield(null, false, 10, player));
                    break;
                case "Shrinker":
                    Game.addToUpdateList(new Shrinker(null,false,10,10,player));
                    break;
                case "Scan":
                    Game.addToUpdateList(new Scan(null, false, 10, null));
                    break;
                default:
                    break;
            }
        }
    }

    private void useHealthPack(Healthpack healthpack) {
        player.healthPoints = player.healthPoints + healthpack.getHealthPackAmount();
        if (player.healthPoints > Player.MAX_HEALTH) {
            player.healthPoints = Player.MAX_HEALTH;
        }
    }

    public void addItem(String itemName) {
        int n = items.get(itemName);
        n = n + 1;
        items.put(itemName, n);
    }

    public void removeItem(String itemName) {
        int n = items.get(itemName);
        if (n > 0) {
            n = n - 1;
        }
        items.put(itemName, n);
    }

    public void setItemQuantity(String itemName, int nb) {
        this.items.put(itemName, nb);
    }

    public Map<String, Integer> getItems() {
        return items;
    }
}

