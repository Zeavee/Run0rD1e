package ch.epfl.sdp.database.firebase.entityForFirebase;

import ch.epfl.sdp.entities.artificial_intelligence.Behaviour;

/**
 * The in-game enemy entity to be stored in the cloud firebase
 */
public class EnemyForFirebase {
    private int id;
    private Behaviour behaviour;
    private GeoPointForFirebase location;
    private double orientation;

    /**
     * For firebase each custom class must have a public constructor that takes no argument.
     */
    public EnemyForFirebase() {
    }

    /**
     * Construct a EnemyForFirebase instance
     *
     * @param id          The id of the enemyForFirebase
     * @param behaviour   The behavior of the enemyForFirebase
     * @param location    The location of the enemyForFirebase
     * @param orientation The orientation of the movement in enemyForFirebase
     */
    public EnemyForFirebase(int id, Behaviour behaviour, GeoPointForFirebase location, double orientation) {
        this.id = id;
        this.location = location;
        this.behaviour = behaviour;
        this.orientation = orientation;
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
     * Get the behaviour of the enemyForFirebase
     *
     * @return The behaviour of the enemyForFirebase
     */
    public Behaviour getBehaviour() {
        return behaviour;
    }

    /**
     * Set the behaviour of the enemyForFirebase
     *
     * @param behaviour The behaviour of the enemyForFirebase
     */
    public void setBehaviour(Behaviour behaviour) {
        this.behaviour = behaviour;
    }

    /**
     * Get the location of the enemyForFirebase
     *
     * @return The location of the enemyForFirebase
     */
    public GeoPointForFirebase getLocation() {
        return location;
    }

    /**
     * Set the location of the enemyForFirebase
     *
     * @param location The location of the enemyForFirebase
     */
    public void setLocation(GeoPointForFirebase location) {
        this.location = location;
    }

    /**
     * Get the orientation of the movement in enemyForFirebase
     *
     * @return The orientation of the movement in enemyForFirebase
     */
    public double getOrientation() {
        return orientation;
    }

    /**
     * Set the orientation of the movement in enemyForFirebase
     *
     * @param orientation The orientation of the movement in enemyForFirebase
     */
    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }
}
