package ch.epfl.sdp.database.firebase.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

public class ItemsForFirebase {
    private Map<String, Integer> itemsMap;
    private Date date;


    public ItemsForFirebase() {
    }

    public ItemsForFirebase(Map<String, Integer> itemsMap, Date date) {
        this.itemsMap = itemsMap;
        this.date = date;
    }

    public Map<String, Integer> getItemsMap() {
        return itemsMap;
    }

    public void setItemsMap(Map<String, Integer> itemsMap) {
        this.itemsMap = itemsMap;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
