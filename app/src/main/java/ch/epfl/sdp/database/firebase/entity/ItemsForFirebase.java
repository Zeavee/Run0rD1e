package ch.epfl.sdp.database.firebase.entity;

import java.util.Date;
import java.util.Map;

/**
 * The items entity to be stored in the cloud firebase
 */
public class ItemsForFirebase {
    private Map<String, Integer> itemsMap;
    private Date date;

    /**
     * For Firebase each custom class much have a public constructor that takes no argument.
     */
    public ItemsForFirebase() {
    }

    /**
     * Construct a itemsForFirebase instance
     *
     * @param itemsMap A map from the name of the item to the quantity of that item
     * @param date     The date indicates the time this itemsForFirebase is created
     */
    public ItemsForFirebase(Map<String, Integer> itemsMap, Date date) {
        this.itemsMap = itemsMap;
        this.date = date;
    }

    /**
     * Get the itemsMap of the itemsForFirebase
     *
     * @return The itemsMap of the itemsForFirebase
     */
    public Map<String, Integer> getItemsMap() {
        return itemsMap;
    }

    /**
     * Set the itemsMap of the itemsForFirebase
     *
     * @param itemsMap The itemsMap from the name of the item to the quantity of that item
     */
    public void setItemsMap(Map<String, Integer> itemsMap) {
        this.itemsMap = itemsMap;
    }

    /**
     * Get the Date indicates the time this itemsForFirebase is created
     *
     * @return The Data indicates the time this itemsForFirebase is created
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set the Date indicates the time this itemsForFirebase is created
     *
     * @param date The Date indicates the time this itemsForFirebase is created
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
