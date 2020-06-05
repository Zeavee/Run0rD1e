package ch.epfl.sdp.items;

import org.junit.Test;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.utils.RandomGenerator;

import static org.junit.Assert.assertEquals;

public class HealthpackTest {

    @Test
    public void cloneHasSameHealthPoints() {
        Healthpack original = new Healthpack(50);
        Item cloned = original.clone();
        assertEquals(((Healthpack) cloned).getValue(), original.getValue(), 0.0);
    }

    @Test
    public void healthPackIncreasesHealthToThreshold(){
        Player sick = new Player(20.0, 20.0, 100, "Player Sick", "test@email.com");
        sick.setHealthPoints(10.0);
        RandomGenerator rand = new RandomGenerator();
        Healthpack pack = rand.randomHealthPack();
        pack.useOn(sick);
        assertEquals(sick.getHealthPoints(), 10 + pack.getValue(), 0.0);
        Healthpack pack2 = new Healthpack(1203);
        pack2.useOn(sick);
        assertEquals(100.0, sick.getHealthPoints(), 0.0);
    }

    @Test
    public void getHealthPackTest() {
        Healthpack pack1 = new Healthpack(70);
        assertEquals(70, pack1.getValue(), 0.0);
    }


}
