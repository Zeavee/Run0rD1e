package ch.epfl.sdp.items;

import org.junit.Test;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.utils.RandomGenerator;

import static org.junit.Assert.assertTrue;

public class HealthpackTest {

    @Test
    public void cloneHasSameHealthPoints() {
        Healthpack original = new Healthpack(50);
        Item cloned = original.clone();
        assertTrue(((Healthpack)cloned).getValue() == original.getValue());
    }

    @Test
    public void healthPackIncreasesHealthToThreshold(){
        RandomGenerator randGen = new RandomGenerator();
        Player sick = new Player(20.0, 20.0, 100, "Player Sick", "test@email.com");
        sick.status.setHealthPoints(10.0, sick);
        RandomGenerator rand = new RandomGenerator();
        Healthpack pack = rand.randomHealthPack();
        pack.useOn(sick);
        assertTrue(sick.status.getHealthPoints() == 10+pack.getValue());
        Healthpack pack2 = new Healthpack(1203);
        pack2.useOn(sick);
        assertTrue(sick.status.getHealthPoints() == 100.0);
    }

    @Test
    public void getHealthPackTest() {
        Healthpack pack1 = new Healthpack(70);
        assertTrue(pack1.getValue() == 70);
    }


}
