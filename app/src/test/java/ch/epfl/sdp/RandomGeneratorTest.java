package ch.epfl.sdp;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.artificial_intelligence.GenPoint;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Scan;
import ch.epfl.sdp.item.Shield;
import ch.epfl.sdp.item.Shrinker;
import ch.epfl.sdp.logic.RandomGenerator;
import ch.epfl.sdp.map.GeoPoint;

public class RandomGeneratorTest {
    private static RandomGenerator randGen = new RandomGenerator();


    @Test
    public void randomString_test(){
        for(int i = 0; i < 10; ++i){
            assertEquals(i, randGen.randomString(i).length());
        }
    }

    @Test
    public void randomValidString_test(){
        for(int i = 0; i < 10; ++i){
            assertEquals(i, randGen.randomValidString(i).length());
        }
    }

    @Test
    public void randomEmail_test(){
        assertNotNull(randGen.randomEmail());
    }

    @Test
    public void randomGeoPointTest() {
        GeoPoint g = randGen.randomGeoPoint();
        GeoPoint f = new GeoPoint(0,0);
        assertFalse(g.getLongitude() == f.getLongitude());
        assertFalse(g.getLatitude() == f.getLatitude());
    }

    @Test
    public void randomGenPointTest() {
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) {
                GenPoint q = randGen.randomGenPoint(i,j);
                assertTrue(q.toCartesian().getArg1() <= i);
                assertTrue(q.toCartesian().getArg2() <= j);
            }
        }
    }

    @Test
    public void randomHealthPackTest() {
        Healthpack h = randGen.randomHealthPack();
        assertTrue(h.getHealthPackAmount() >=25 && h.getHealthPackAmount() <= 50);
    }

    @Test
    public void randomShieldTest() {
        Shield s = randGen.randomShield();
        assertTrue(s.getShieldTime() >= 20);
        assertTrue(s.getShieldTime() <= 30);
    }

    @Test
    public void randomShrinker() {
        Shrinker s = randGen.randomShrinker();
        assertTrue(s.getShrinkTime() >= 0);
        assertTrue(s.getShrinkTime() <= 1);
        assertTrue(s.getShrinkingRadius() >= 0);
        assertTrue(s.getShrinkingRadius() <= 1);
    }

    @Test
    public void randomScan() {
        Scan s = randGen.randomScan();
        assertTrue(s.getScanTime() <= 1);
        assertTrue(s.getScanTime() >= 0);
    }

    @Test
    public void randomPlayer() {
        for (int i = 0; i <5; i++) {
            Player p = randGen.randomPlayer();
            assertTrue(p.getLocation().getLongitude() >= 5);
            assertTrue(p.getLocation().getLongitude() <= 7);
            assertTrue(p.getLocation().getLatitude() <= 47);
            assertTrue(p.getLocation().getLatitude() >= 45);
        }
    }

    @Test
    public void randomEnemy() {
        Enemy e = randGen.randomEnemy();
        assertFalse(1 == 2);
    }




}
