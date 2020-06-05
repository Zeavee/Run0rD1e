package ch.epfl.sdp.utils;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.ShelterArea;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Phantom;
import ch.epfl.sdp.item.Shield;
import ch.epfl.sdp.item.Shrinker;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class RandomGeneratorTest {
    private RandomGenerator randGen;

    @Before
    public void setup(){
        randGen = new RandomGenerator();
    }

    @Test
    public void randomGeoPointTest() {
        GeoPoint g = randGen.randomGeoPoint();
        GeoPoint f = new GeoPoint(0,0);
        assertNotEquals(g.getLongitude(), f.getLongitude(), 0.0);
        assertNotEquals(g.getLatitude(), f.getLatitude(), 0.0);
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
        for(int i = 0; i < 100; ++i){
            Shrinker shrinker = randGen.randomShrinker();
            assertTrue(shrinker.getRemainingTime() >= -1);
            assertTrue(shrinker.getRemainingTime() <= 5);
            assertTrue(shrinker.getShrinkingRadius() >= -1);
            assertTrue(shrinker.getShrinkingRadius() <= 5);
        }
    }

    @Test
    public void randomPhantom() {
        Phantom phantom = randGen.randomPhantom();
        assertTrue(phantom.getRemainingTime() <= 1);
        assertTrue(phantom.getRemainingTime() >= 0);
    }


    @Test
    public void randomCoinTest() {
        for (int i = 0; i<4; i++) {
            assertTrue(randGen.randomCoin(randGen.randomGeoPoint()).getValue() < 30);
        }
    }



    @Test
    public void randomShelterAreaTest() {
        GeoPoint g = randGen.randomGeoPoint();
        ShelterArea s = randGen.randomShelterArea(g);
        assertTrue(s.getAoeRadius() <= 120 && s.getAoeRadius() >= 90);
    }

}
