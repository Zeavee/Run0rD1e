package ch.epfl.sdp.utils;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.ShelterArea;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Scan;
import ch.epfl.sdp.item.Shield;
import ch.epfl.sdp.item.Shrinker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RandomGeneratorTest {
    private RandomGenerator randGen;

    @Before
    public void setup(){
        randGen = new RandomGenerator();
    }

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
    public void randomHealthPackTest() {
        Healthpack h = randGen.randomHealthPack();
        assertTrue(h.getValue() >=25 && h.getValue() <= 50);
    }

    @Test
    public void randomShieldTest() {
        Shield s = randGen.randomShield();
        assertTrue(s.getRemainingTime() >= 20);
        assertTrue(s.getRemainingTime() <= 30);
    }

    @Test
    public void randomShrinker() {
        Shrinker s = randGen.randomShrinker();
        assertTrue(s.getRemainingTime() >= 0);
        assertTrue(s.getRemainingTime() <= 1);
        assertTrue(s.getShrinkingRadius() >= 0);
        assertTrue(s.getShrinkingRadius() <= 1);
    }

    @Test
    public void randomScan() {
        Scan s = randGen.randomScan();
        assertTrue(s.getRemainingTime() <= 1);
        assertTrue(s.getRemainingTime() >= 0);
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
    public void randomCoinTest() {
        for (int i = 0; i<4; i++) {
            assertTrue(randGen.randomCoin(randGen.randomGeoPoint()).getValue() < 30);
        }
    }

    @Test
    public void randomGeoPointAroundLocationTest() {
        GeoPoint g = new GeoPoint(0,0);
        GeoPoint f = randGen.randomGeoPointAroundLocation(g);
        double dlong = g.getLongitude() - f.getLongitude();
        if(dlong < 0) {
            dlong = (-1)*dlong;
        }
        double dlat = g.getLatitude() - f.getLatitude();
        if(dlat < 0) {
            dlat = (-1)*dlat;
        }
        assertTrue(dlat < 0.5);
        assertTrue(dlong < 0.5);
    }

    @Test
    public void randomShelterAreaTest() {
        GeoPoint g = randGen.randomGeoPoint();
        ShelterArea s = randGen.randomShelterArea(g);
        assertTrue(s.getAoeRadius() <= 70 && s.getAoeRadius() >= 60);
    }

}
