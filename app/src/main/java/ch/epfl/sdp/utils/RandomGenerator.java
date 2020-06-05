package ch.epfl.sdp.utils;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.sdp.entity.ShelterArea;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.Vector;
import ch.epfl.sdp.item.Coin;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.item.Phantom;
import ch.epfl.sdp.item.Shield;
import ch.epfl.sdp.item.Shrinker;

/**
 * Utility class which helps generating random positions, strings, GeoPoints, Coins, etc
 * These methods are mainly used in tests, but also allow us to create random GeoPoints around specific locations which is useful when we want to generate Coins or ShelterPoints
 */
public class RandomGenerator {

    private static Random rand;

    public RandomGenerator() {
        rand = new Random();
    }

    /**
     * Creates a random GeoPoint
     * Remark : It's not absolutely random. The GeoPoint in the output is always in the form (long, lat) = (45 + rx, 5 + ry), where rx and ry are generated randomly
     * @return the generated GeoPoint
     */
    public GeoPoint randomGeoPoint() {
        double randomLong = rand.nextDouble() * 2 + 45;
        double randomLat = rand.nextDouble() * 2 + 5;
        return new GeoPoint(randomLong, randomLat);
    }

    /**
     *  Chooses a random location on a circle of chosen radius
     * @param reference the reference GeoPoint
     * @param radius
     * @return the generated GeoPoint
     */
    public GeoPoint randomLocationOnCircle(GeoPoint reference, int radius) {
        Vector vector = Vector.fromPolar(rand.nextDouble() * radius, rand.nextDouble() * Math.PI);
        return reference.asOriginTo(vector);
    }



    /**
     * Creates a helthpack with a random amount of health points between 25 and 50
     * @return
     */
    public Healthpack randomHealthPack() {
        Healthpack h = new Healthpack(rand.nextInt(25) + 25);
        return h;
    }


    /**
     * Creates a Shield with random effective time
     * @return
     */
    public Shield randomShield() {
        Shield s = new Shield(rand.nextInt(1) * 10 + 20);
        return s;
    }

    /**
     * Creates a shrinker with random effective time and radius
     * @return
     */
    public Shrinker randomShrinker() {
        Shrinker s = new Shrinker(rand.nextInt(1), rand.nextInt(5));
        return s;
    }

    /**
     * Creates a random phantom with random effective time
     * @return
     */
    public Phantom randomPhantom() {
        Phantom s = new Phantom(1 + rand.nextInt(1));
        return s;
    }



    /**
     * Creates a ShelterArea around a given location. The Aoe of the ShelterArea is random
     * @param location
     * @return shelterArea with random aoe (between 60 and 70)
     */
    public ShelterArea randomShelterArea(GeoPoint location) {
        double rangeMin = 90.0;
        double rangeMax = 120.0;
        double aoe = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
        ShelterArea s = new ShelterArea(location, aoe);
        return s;
    }

    /**
     * Creates a list of random Items
     * @return
     */
    public ArrayList<Item> randomItemsList() {
        ArrayList<Item> result = new ArrayList<>();
        result.add(randomHealthPack());
        result.add(randomPhantom());
        result.add(randomShield());
        result.add(randomShrinker());
        return result;
    }


    /**
     * Creates a coin with a random value at a given location
     * @param location
     * @return the generated coin
     */
    public Coin randomCoin(GeoPoint location) {
        int i = rand.nextInt(30);
        return new Coin(i, location);
    }


    public Random getRand() {
        return rand;
    }

}