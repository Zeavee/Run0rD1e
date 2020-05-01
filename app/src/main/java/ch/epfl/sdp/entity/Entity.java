package ch.epfl.sdp.entity;

/**
 * Base interface for all entities.
 */
public interface Entity {

    /**
     * Method to get the type of the object we want to display
     * @return an EntityType which is an enum of types
     */
    EntityType getEntityType();
}
