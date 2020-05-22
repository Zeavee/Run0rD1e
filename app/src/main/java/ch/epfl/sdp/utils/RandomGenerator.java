package ch.epfl.sdp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.entity.ShelterArea;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.RectangleArea;
import ch.epfl.sdp.geometry.UnboundedArea;
import ch.epfl.sdp.geometry.Vector;
import ch.epfl.sdp.item.Coin;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.item.Scan;
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
     * Method which generates an arrayList of alphaNumerics
     * @return
     */
    public ArrayList generateAlphaNumerics() {
        ArrayList alpha_numerics = new ArrayList();

        for (char i = 'a'; i < 'z'; ++i) {
            alpha_numerics.add(i);
        }

        for (char i = 'A'; i < 'Z'; ++i) {
            alpha_numerics.add(i);
        }

        for (char i = '0'; i < '9'; ++i) {
            alpha_numerics.add(i);
        }
        return alpha_numerics;
    }

    /**
     * Method which generates a random string of given length
     * @param length
     * @return the generated string
     */
    public String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        if (length > 0) {
            for (int i = 0; i < length; ++i) {
                sb.append((char) rand.nextInt());
            }
        }
        return sb.toString();
    }


    /**
     * Method which generates a valid String (for Player names, ...)
     * @param length
     * @return the generated string
     */
    public String randomValidString(int length) {
        StringBuilder sb = new StringBuilder();
        ArrayList alpha_numerics = generateAlphaNumerics();
        if (length > 0) {
            for (int i = 0; i < length; ++i) {
                sb.append(alpha_numerics.get(rand.nextInt(alpha_numerics.size() - 1)));
            }
        }
        return sb.toString();
    }

    /**
     * Method which generates a random email
     * @return the generated email
     */
    public String randomEmail() {
        StringBuilder sb = new StringBuilder();

        sb.append(randomValidString(rand.nextInt(20)));
        sb.append('@');
        sb.append(randomValidString(rand.nextInt(15)));
        sb.append('.');
        randomValidString(rand.nextInt(3));

        return sb.toString();
    }

    /**
     * Creates a random GeoPoint
     * @return the generated GeoPoint
     */
    public GeoPoint randomGeoPoint() {
        double randomLong = rand.nextDouble() * 2 + 5;
        double randomLat = rand.nextDouble() * 2 + 45;
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
     * Creates a random GeoPoint between two specific bounds
     * @param a
     * @param b
     * @return the generated GeoPoint
     */
    public GeoPoint randomGeoPointBetweenTwoBounds(int a, int b) {
        GeoPoint point = new GeoPoint(rand.nextInt(a), rand.nextInt(b));
        return point;
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
        Shrinker s = new Shrinker(2+rand.nextInt(1), Math.min(rand.nextDouble(), 0.1));
        return s;
    }

    /**
     * Creates a random scan with random effective time
     * @return
     */
    public Scan randomScan() {
        Scan s = new Scan(1+rand.nextInt(1));
        return s;
    }

    /**
     * Creates a random Player
     * @return
     */
    public Player randomPlayer() {
        GeoPoint g = randomGeoPoint();
        Player p = new Player(g.getLongitude(), g.getLatitude(), rand.nextDouble() + 50, randomString(10), randomEmail());
        return p;
    }

    /**
     * Creates a ShelterArea around a given location. The Aoe of the ShelterArea is random
     * @param location
     * @return shelterArea with random aoe (between 60 and 70)
     */
    public ShelterArea randomShelterArea(GeoPoint location) {
        double rangeMin = 90.0;
        double rangeMax = 150.0;
        double aoe = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
        ShelterArea s = new ShelterArea(location, aoe);
        return s;
    }

    /**
     * Creates a list of random Items
     * @return
     */
    public List<Item> randomItemsList() {
        ArrayList<Item> result = new ArrayList<>();
        result.add(randomHealthPack());
        result.add(randomScan());
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

    /**
     * Creates a random GeoPoint around another GeoPoint
     * @param location
     * @return the generated GeoPoint
     */
    public GeoPoint randomGeoPointAroundLocation(GeoPoint location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double leftLimit = -0.001;
        double rightLimit = 0.012;
        double randomExt = leftLimit + rand.nextDouble() * (rightLimit - leftLimit);
        double newLat = latitude + randomExt;
        double anotherRandomExt = leftLimit + rand.nextDouble() * (rightLimit - leftLimit);
        double newLong = longitude + anotherRandomExt;
        return new GeoPoint(newLong, newLat);
    }
}