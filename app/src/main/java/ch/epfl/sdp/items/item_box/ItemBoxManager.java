package ch.epfl.sdp.items.item_box;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemBoxManager {
    public static final String ITEMBOX_COLLECTION_NAME = "ItemBoxes";

    private static final ItemBoxManager instance = new ItemBoxManager();
    private final Map<String, ItemBox> itemBoxes;
    private final Map<String, ItemBox> waitingItemBoxes;

    public static ItemBoxManager getInstance() {
        return instance;
    }

    private ItemBoxManager() {
        itemBoxes = new HashMap<>();
        waitingItemBoxes = new HashMap<>();
    }

    public void addItemBox(ItemBox itemBox) {
        String uniqueID = UUID.randomUUID().toString();
        itemBoxes.put(uniqueID, itemBox);
        waitingItemBoxes.put(uniqueID, itemBox);
    }

    public void addItemBoxWithId(ItemBox itemBox, String id) {
        itemBoxes.put(id, itemBox);
    }

    public Map<String, ItemBox> getItemBoxes() {
        return itemBoxes;
    }

    public Map<String, ItemBox> getWaitingItemBoxes() {
        return waitingItemBoxes;
    }

    public void moveTakenItemBoxesToWaitingList() {
        ArrayList<String> keys = new ArrayList<>();

        for (String key : itemBoxes.keySet()) {
            if (itemBoxes.get(key).isTaken()) {
                waitingItemBoxes.put(key, itemBoxes.get(key));
                keys.add(key);
            }
        }

        for (String key: keys) {
            itemBoxes.remove(key);
        }
    }

    public void clearWaitingItemBoxes() {
        waitingItemBoxes.clear();
    }

    private void clearItemBoxes() {
        itemBoxes.clear();
    }

    public void clear(){
        clearWaitingItemBoxes();
        clearItemBoxes();
    }
}
