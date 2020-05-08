package ch.epfl.sdp.database.firebase.entity;

import ch.epfl.sdp.geometry.GeoPoint;

/**
 * The in-game enemy entity to be stored in the cloud firebase
 */
public class EnemyForFirebase {
    private int id;
    private GeoPoint location;

    /**
     * For firebase each custom class must hava a public constructor that takes no argument.
     */
    public EnemyForFirebase() {
    }

    /**
     * Construct a EnemyForFirebase instance
     *
     * @param id       The id of the enemyForFirebase
     * @param location The location of the enemyForFirebase
     */
    public EnemyForFirebase(int id, GeoPoint location) {
        this.id = id;
        this.location = location;
    }

    /**
     * Get the id of the enemyForFirebase
     *
     * @return The id of the enemyForFirebase
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of the enemyForFirebase
     *
     * @param id The id of the enemyForFirebase
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the location of the enemyForFirebase
     *
     * @return The location of the enemyForFirebase
     */
    public GeoPoint getLocation() {
        return location;
    }

    /**
     * Set the location of the enemyForFirebase
     *
     * @param location The location of the enemyForFirebase
     */
    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}
