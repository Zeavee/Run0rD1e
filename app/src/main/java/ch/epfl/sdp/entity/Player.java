package ch.epfl.sdp.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ch.epfl.sdp.artificial_intelligence.CartesianPoint;
import ch.epfl.sdp.artificial_intelligence.GenPoint;
import ch.epfl.sdp.artificial_intelligence.Localizable;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.item.Shield;
import ch.epfl.sdp.item.Shrinker;

public class Player extends MovingEntity implements Localizable {
    private String username;
    private String email;
    private GenPoint position;
    private int score;
    private double healthPoints;
    private double timeTraveled;
    private double distanceTraveled;
    private double speed;
    private boolean alive;
    private boolean isPhantom;
    private boolean isShielded;
    private final double MAX_HEALTH = 100;
    private HashMap<String, Integer> itemInventory = new HashMap<>();




    public Player(double longitude, double latitude, double aoeRadius, String username, String email) {
        super(longitude, latitude, aoeRadius);
        this.username = username;
        this.email = email;
        this.score = 0;
        this.healthPoints = 100;
        this.distanceTraveled = 0;
        this.timeTraveled = 0;
        this.speed = 0;
        this.alive = true;
        this.isPhantom = false;
        this.isShielded = false;
        this.position = new CartesianPoint((float) longitude, (float) latitude);
    }

    @Override
    public void updateLocation() {
        //TODO
    }

    public void updateHealth(ArrayList<Enemy> enemies) {
        for (Enemy e : enemies) {
            double distance = this.getLocation().distanceTo(e.getLocation()) - this.getAoeRadius() - e.getAoeRadius();
            if (distance < 0 && !isShielded) {
                this.healthPoints = this.healthPoints + 1/distance * 10; //distance is negative
            }
        }
    }


    public double getHealthPoints() {
        return healthPoints;
    }

    public boolean isAlive() {
        return alive;
    }

    public double getSpeed() {
        return speed;
    }

    public double getTimeTraveled() {
        return timeTraveled;
    }

    public int getScore() {
        return score;
    }

    public double getDistanceTraveled() {
        return this.distanceTraveled;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean isPhantom() {return this.isPhantom; }

    private void useHealthPack(Healthpack healthpack) {
        this.healthPoints = this.healthPoints + healthpack.getHealthPackAmount();
        if (this.healthPoints > MAX_HEALTH) {
            this.healthPoints = MAX_HEALTH;
        }
    }

    public void useItem(Item i) {
        String name = i.getName();
        int numberOfInstances = itemInventory.get(name);
        if(numberOfInstances > 0) {
            switch(i.getName()) {
                case "Healthpack":
                    ((Healthpack) i).increaseHealthPlayer(this, MAX_HEALTH);
                    break;
                case "Shield":
                    isShielded = true;
                    TimerTask shieldPlayer = new TimerTask() {
                    @Override
                    public void run() {
                        isShielded = false;
                    }
                };
                    Timer timer = new Timer();
                    timer.schedule(shieldPlayer, (long) ((Shield) i).getShieldTime() * 1000);
                    break;
                case "Shrinker":
                    double initialAoeRadius = getAoeRadius();
                    setAoeRadius(this.getAoeRadius() - ((Shrinker) i).getShrinkingRadius());
                    TimerTask shrinkAoeRadius = new TimerTask() {
                    @Override
                    public void run() {
                            setAoeRadius(initialAoeRadius);
                        }
                    };
                    Timer t = new Timer();
                    t.schedule(shrinkAoeRadius, (long) ((Shrinker) i).getShrinkTime() * 1000);
                    break;
                case "Scan":
                    //((Scan) i).showPlayersLocation();
                    break;
                default:
                    break;
            }
            itemInventory.put(i.getName(), numberOfInstances-1);
        }
    }

    public void setItemInventory(String itemName, int nb) {
        this.itemInventory.put(itemName, nb);
    }
    public Map<String, Integer> getItemInventory() { return itemInventory; }

    public void setHealthPoints(double amount) {
        this.healthPoints = amount;
    }


    public boolean isShielded() {return this.isShielded; }

    @Override
    public EntityType getEntityType() {
        return EntityType.USER;
    }

    public void addItemInInventory(String itemName) {
        int n = itemInventory.get(itemName);
        n = n+1;
        itemInventory.put(itemName, n);
    }

    public void removeItemInInventory(String itemName) {
        int n = itemInventory.get(itemName);
        if(n > 0) {
            n=n-1;
        }
        itemInventory.put(itemName, n);
    }

    @Override
    public GenPoint getPosition() {
        return position;
    }
}