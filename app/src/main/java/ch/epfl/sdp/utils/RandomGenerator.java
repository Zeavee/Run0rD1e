package ch.epfl.sdp.utils;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.sdp.entities.shelter_area.ShelterArea;
import ch.epfl.sdp.map.location.GeoPoint;
import ch.epfl.sdp.geometry.Vector;
import ch.epfl.sdp.items.Coin;
import ch.epfl.sdp.items.Healthpack;
import ch.epfl.sdp.items.Item;
import ch.epfl.sdp.items.Phantom;
import ch.epfl.sdp.items.Shield;
import ch.epfl.sdp.items.Shrinker;

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
     *
     * @return the generated GeoPoint
     */
    public GeoPoint randomGeoPoint() {
        double randomLong = rand.nextDouble() * 2 + 45;
        double randomLat = rand.nextDouble() * 2 + 5;
        return new GeoPoint(randomLong, randomLat);
    }

    /**
     * Chooses a random location on a circle of chosen radius
     *
     * @param reference the reference GeoPoint
     * @param radius    the radius of the circle
     * @return the generated GeoPoint
     */
    public GeoPoint randomLocationOnCircle(GeoPoint reference, int radius) {
        Vector vector = Vector.fromPolar(rand.nextDouble() * radius, rand.nextDouble() * Math.PI);
        return reference.asOriginTo(vector);
    }


    /**
     * Creates a health pack with a random amount of health points between 25 and 50
     *
     * @return a random health pack
     */
    public Healthpack randomHealthPack() {
        return new Healthpack(rand.nextInt(25) + 25);
    }


    /**
     * Creates a Shield with random effective time
     *
     * @return a random shield
     */
    public Shield randomShield() {
        return new Shield(rand.nextInt(1) * 10 + 20);
    }

    /**
     * Creates a shrinker with random effective time and radius
     *
     * @return a random shrinker
     */
    public Shrinker randomShrinker() {
        return new Shrinker(rand.nextInt(1), rand.nextInt(5));
    }

    /**
     * Creates a random phantom with random effective time
     *
     * @return a random phantom
     */
    public Phantom randomPhantom() {
        return new Phantom(1 + rand.nextInt(1));
    }


    /**
     * Creates a shelter area around a given location. The Aoe of the shelter area is random
     *
     * @param location the location of the random shelter area
     * @return shelter area with random aoe (between 60 and 70)
     */
    public ShelterArea randomShelterArea(GeoPoint location) {
        double rangeMin = 90.0;
        double rangeMax = 120.0;
        double aoe = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
        return new ShelterArea(location, aoe);
    }

    /**
     * Creates a list of random Items
     *
     * @return a list of random items
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
     *
     * @param location the location of the random coin
     * @return the generated coin
     */
    public Coin randomCoin(GeoPoint location) {
        int i = rand.nextInt(30);
        return new Coin(i, location);
    }


    /**
     * Gets the random instance of the class
     *
     * @return the random instance of the class
     */
    public Random getRand() {
        return rand;
    }

}