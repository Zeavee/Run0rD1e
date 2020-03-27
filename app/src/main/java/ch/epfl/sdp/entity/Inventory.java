package ch.epfl.sdp.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.item.Scan;
import ch.epfl.sdp.item.Shield;
import ch.epfl.sdp.item.Shrinker;

public class Inventory {
    private Player player;
    private HashMap<String, Integer> items;

    public Inventory(Player player) {
        this.player = player;
        this.items = new HashMap<>();
    }

    public void useItem(Item i) {
        String name = i.getName();
        int numberOfInstances = items.get(name);
        if (numberOfInstances > 0) {
            switch (i.getName()) {
                case "Healthpack":
                    ((Healthpack) i).increaseHealthPlayer(player, Player.MAX_HEALTH);
                    break;
                case "Shield":
                    player.setShielded(true);
                    TimerTask shieldPlayer = new TimerTask() {
                        @Override
                        public void run() {
                            player.setShielded(false);
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(shieldPlayer, (long) ((Shield) i).getShieldTime() * 1000);
                    break;
                case "Shrinker":
                    double initialAoeRadius = player.getAoeRadius();
                    player.setAoeRadius(player.getAoeRadius() - ((Shrinker) i).getShrinkingRadius());
                    TimerTask shrinkAoeRadius = new TimerTask() {
                        @Override
                        public void run() {
                            player.setAoeRadius(initialAoeRadius);
                        }
                    };
                    Timer t = new Timer();
                    t.schedule(shrinkAoeRadius, (long) ((Shrinker) i).getShrinkTime() * 1000);
                    break;
                case "Scan":
                    ((Scan) i).showAllPlayers();
                    break;
                default:
                    break;
            }
            items.put(i.getName(), numberOfInstances - 1);
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

