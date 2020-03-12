package ch.epfl.sdp;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Player extends MovingEntity {

    private String username;
    private String email;
    private int score;
    private double healthPoints;
    private double timeTraveled;
    private double distanceTraveled;
    private double speed;
    private boolean alive;
    private ArrayList<Item> itemInventory;
    private boolean isPhantom;
    private boolean isShielded;
    private final double MAX_HEALTH = 100;

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
        this.itemInventory = new ArrayList<Item>();
        this.isPhantom = false;
        this.isShielded = false;
    }


    @Override
    public void updateLocation() {
        //TODO
    }

    public void updateHealth(ArrayList<Enemy> enemies) {
        for (Enemy e : enemies) {
            double distance = this.location.distanceTo(e.getLocation()) - this.aoeRadius - e.getAoeRadius();
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


    private void useShield(Shield s) {
        isShielded = true;
        //Countdown for 40 sec
        isShielded = false;
    }

    public void useItem(int inventoryIndex) {
        Item i = itemInventory.get(inventoryIndex);
        switch(i.getItemID()) {
            case 1:
                useHealthPack((Healthpack) i);
                break;
            case 2:
                isShielded = true;
                TimerTask shieldPlayer = new TimerTask() {
                    @Override
                    public void run() {
                        isShielded = false;
                    }
                };
                Timer timer = new Timer();
                timer.schedule(shieldPlayer, (long) ((Shield) i).getShieldTime()*1000);
                break;
            case 3:
                double initialAoeRadius = aoeRadius;
                aoeRadius = aoeRadius - ((Shrinker) i).getShrinkingRadius();
                TimerTask shrinkAoeRadius = new TimerTask() {
                    @Override
                    public void run() {
                        aoeRadius = initialAoeRadius;
                    }
                };
                Timer t = new Timer();
                t.schedule(shrinkAoeRadius, (long) ((Shrinker) i).getShrinkTime()*1000);
                break;
            case 4:
                //((Scan) i).showPlayersLocation();
                break;
            default:
                break;
        }
        itemInventory.remove(i);
    }

    public int getNumberOfItemsInInventory(int itemId) {
        int nb = 0;
        for (Item i : itemInventory) {
            if(i.getItemID() == itemId) {
                nb =+ 1;
            }
        }
        return nb;
    }

    public void setHealthPoints(double amount) {
        this.healthPoints = amount;
    }

    public void addInventory(Item i) {
        itemInventory.add(i);
    }

    public boolean isShielded() {return this.isShielded; }
}
