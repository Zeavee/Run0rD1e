package ch.epfl.sdp.items;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.item.ItemBox;
import ch.epfl.sdp.item.ItemBoxManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ItemBoxManagerTest {

    private ItemBox dummyItemBox;

    @Before
    public void setupTest() {
        dummyItemBox = new ItemBox(new GeoPoint());
        ItemBoxManager.getInstance().getItemBoxes().clear();
        ItemBoxManager.getInstance().getWaitingItemBoxes().clear();
    }

    @Test
    public void addItemBoxShouldAddItemInBothMaps() {
        ItemBoxManager.getInstance().addItemBox(dummyItemBox);
        assertEquals(1, ItemBoxManager.getInstance().getItemBoxes().size());
        assertEquals(1, ItemBoxManager.getInstance().getWaitingItemBoxes().size());
    }

    @Test
    public void addItemBoxWithIdShouldAddItem() {
        ItemBoxManager.getInstance().addItemBoxWithId(dummyItemBox, "hds");
        assertEquals(1, ItemBoxManager.getInstance().getItemBoxes().size());
    }

    @Test
    public void moveTakenItemBoxesToWaitingListWorks() {
        Player player = new Player("a", "b");
        PlayerManager.getInstance().setCurrentUser(player);
        dummyItemBox.react(player);
        ItemBoxManager.getInstance().addItemBox(dummyItemBox);
        ItemBoxManager.getInstance().moveTakenItemBoxesToWaitingList();
        assertEquals(0, ItemBoxManager.getInstance().getItemBoxes().size());
        assertEquals(1, ItemBoxManager.getInstance().getWaitingItemBoxes().size());
    }

    @Test
    public void clearWaitingItemBoxesShouldRemoveItems() {
        ItemBoxManager.getInstance().addItemBox(dummyItemBox);
        ItemBoxManager.getInstance().clearWaitingItemBoxes();
        assertEquals(0, ItemBoxManager.getInstance().getWaitingItemBoxes().size());
    }
}
