package ch.epfl.sdp.items;

import org.junit.Test;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Item;

import static org.junit.Assert.assertTrue;

public class HealthpackTest {
    private Player originalPlayer = PlayerManager.getInstance().getCurrentUser();
    @Test
    public void cloneHasSameHealthPoints() {
        Healthpack original = new Healthpack(50);
        Item cloned = original.clone();
        assertTrue(((Healthpack)cloned).getHealthPackAmount() == original.getHealthPackAmount());
    }

    @Test
    public void healthPackIncreasesHealthToThreshold(){
        Player sick = new Player(20.0, 20.0, 100, "amroa", "amro.abdrabo@gmail.com");
        sick.setHealthPoints(20.0);
        PlayerManager.getInstance().setCurrentUser(sick);
        Healthpack pack = new Healthpack(30);
        pack.useOn(sick);
        assertTrue(sick.getHealthPoints() == 50.0);
        Healthpack pack2 = new Healthpack(70);
        pack2.useOn(sick);
        assertTrue(sick.getHealthPoints() == 100.0);
        PlayerManager.getInstance().setCurrentUser(originalPlayer);
    }


}
