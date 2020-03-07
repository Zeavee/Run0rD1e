package ch.epfl.sdp;

public class Player extends MovingEntity {
    private String username;
    private String email;
    private int score;
    private double healthPoints;
    private double timeTraveled;
    private double distanceTraveled;
    private double speed;
    private boolean alive;

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
    }


    @Override
    public void updateLocation() {
        //TODO
    }

    @Override
    public void updateAoeRadius() {
        //TODO
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
