package ch.epfl.sdp.item;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemBoxManager {
    public static final String ITEMBOX_COLLECTION_NAME = "ItemBoxes";

    private static final ItemBoxManager instance = new ItemBoxManager();
    private Map<String, ItemBox> itemBoxes;
    private Map<String, ItemBox> waitingItemBoxes;

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
        for (String key : itemBoxes.keySet()) {
            if (itemBoxes.get(key).isTaken()) {
                waitingItemBoxes.put(key, itemBoxes.get(key));
                itemBoxes.remove(key);
            }
        }
    }

    public void clearWaitingItemBoxes() {
        waitingItemBoxes.clear();
    }
}
