package ch.epfl.sdp;

import java.util.List;

public class Player extends MovingEntity {

    String username;
    String email;
    int score;
    double healthPoints;
    double timeTraveled;
    double distanceTraveled;
    double speed;
    boolean alive;

    public Player(GeoPoint localisation, double aoeRadius, String username, String email) {
        super(localisation, aoeRadius);
        this.username = username;
        this.email = email;
        this.score = 0;
        this.healthPoints = 100;
        this.distanceTraveled = 0;
        this.timeTraveled = 0;
        this.speed = 0;
        this.alive = true;
    }


    @Override
    public void updateLocation() {
        //TODO
    }

    @Override
    public void updateAoeRadius() {
        //TODO
    }


    public void updateHealth(List<GeoPoint> enemyLocations, double currentEnemyAoeRadius) {
        for (GeoPoint elocation : enemyLocations) {
            double distance = this.location.distanceTo(elocation) - this.aoeRadius - currentEnemyAoeRadius;
            if (distance < 0) {
                this.healthPoints = this.healthPoints + distance; //distance is negative
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
}
