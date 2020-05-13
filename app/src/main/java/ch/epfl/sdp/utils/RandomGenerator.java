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

public class RandomGenerator {
   private static Random rand = new Random();
   private ArrayList alpha_numerics;

   public RandomGenerator(){
       alpha_numerics = new ArrayList();

       for (char i = 'a'; i < 'z'; ++i){
           alpha_numerics.add(i);
       }

       for (char i = 'A'; i < 'Z'; ++i){
           alpha_numerics.add(i);
       }

       for (char i = '0'; i < '9'; ++i){
           alpha_numerics.add(i);
       }
   }

    public String randomString(int length){
        StringBuilder sb = new StringBuilder();

            if(length > 0){
                for (int i = 0; i < length; ++i){
                    sb.append((char)rand.nextInt()) ;
                }
        }
            return sb.toString();
    }

    public String randomValidString(int length){
        StringBuilder sb = new StringBuilder();

        if(length > 0){
            for (int i = 0; i < length; ++i){
                sb.append(alpha_numerics.get(rand.nextInt(alpha_numerics.size() - 1))) ;
            }
        }
        return sb.toString();
    }

    public String randomEmail(){
        StringBuilder sb = new StringBuilder();

        sb.append(randomValidString(rand.nextInt(20)));
        sb.append('@');
        sb.append(randomValidString(rand.nextInt(15)));
        sb.append('.');
        randomValidString(rand.nextInt(3));

        return sb.toString();
    }

     public GeoPoint randomGeoPoint() {
       double randomLong = rand.nextDouble()*2 + 5;
       double randomLat = rand.nextDouble()*2 + 45;
       return new GeoPoint(randomLong, randomLat);
     }

     // Chooses a random location on a circle of chosen radius
     public static GeoPoint randomLocationOnCircle(GeoPoint reference, int radius){
         Vector vector = Vector.fromPolar(radius, rand.nextDouble() * Math.PI);
         return reference.asOriginTo(vector);
     }

    public GeoPoint randomCartesianPoint(int a, int b) {
        GeoPoint point = new GeoPoint(rand.nextInt(a), rand.nextInt(b));
        return point;
    }

     public Healthpack randomHealthPack() {
       Healthpack h = new Healthpack(rand.nextInt(25)+25);
       return h;
     }

     public Shield randomShield() {
       Shield s = new Shield(rand.nextInt(1)*10+20);
       return s;
     }

     public Shrinker randomShrinker() {
       Shrinker s = new Shrinker( rand.nextInt(1), rand.nextDouble());
       return s;
     }

     public Scan randomScan() {
       Scan s = new Scan( rand.nextInt(1));
       return s;
     }

     public Player randomPlayer() {
       GeoPoint g = randomGeoPoint();
       Player p = new Player(g.getLongitude(), g.getLatitude(), rand.nextDouble()+50, randomString(10), randomEmail());
       return p;
     }

     public Enemy randomEnemy() {
         // TODO Remove useless lines
         List<Player> players = new ArrayList<>();

         GeoPoint g1 = randomGeoPoint();
         players.add(new Player(g1.getLongitude(), g1.getLatitude(), rand.nextDouble()+50, randomString(10), randomEmail()));
         GeoPoint g2 = randomGeoPoint();
         players.add(new Player(g2.getLongitude(), g2.getLatitude(), rand.nextDouble()+50, randomString(10), randomEmail()));
         GeoPoint g3 = randomGeoPoint();
         players.add(new Player(g3.getLongitude(), g3.getLatitude(), rand.nextDouble()+50, randomString(10), randomEmail()));
         GeoPoint g4 = randomGeoPoint();
         players.add(new Player(g4.getLongitude(), g4.getLatitude(), rand.nextDouble()+50, randomString(10), randomEmail()));
         GeoPoint g5 = randomGeoPoint();
         players.add(new Player(g5.getLongitude(), g5.getLatitude(), rand.nextDouble()+50, randomString(10), randomEmail()));

         addToPlayerManager(players);

         int randBound = rand.nextInt(20);
         int randomDmg = rand.nextInt(randBound+1);
         float randomdps = rand.nextFloat();
         float randomDetectionDistance = rand.nextFloat()*10 + 50;
         RectangleArea r = new RectangleArea(10, 10, randomCartesianPoint(1, 5));
         UnboundedArea areaMax = new UnboundedArea();
         Enemy e = new Enemy(0, randomDmg, randomdps, randomDetectionDistance, 50, r, areaMax);
         return e;
     }

     private void addToPlayerManager(List<Player> players){
         for (Player player: players) {
             PlayerManager.getInstance().addPlayer(player);
         }
     }

     public ShelterArea randomShelterArea() {
       GeoPoint l = this.randomGeoPoint();
       double aoe = rand.nextDouble();
       ShelterArea s = new ShelterArea(l,  aoe);
       PlayerManager.getInstance().clear();
       Player p = this.randomPlayer();
       for (int i = 0; i < 3; i++) {
           while (p.getLocation().distanceTo(l) < aoe) {
               PlayerManager.getInstance().addPlayer(p);
           }
       }
       PlayerManager.getInstance().addPlayer(new Player(l.getLongitude(), l.getLatitude(), 10, "in", "in@in.com"));
       s.shelter();
       return s;
     }
    public List<Item> randomItemsList() {
        ArrayList<Item> result = new ArrayList<>();
        result.add(randomHealthPack());
        result.add(randomScan());
        result.add(randomShield());
        result.add(randomShrinker());
        return result;
    }

     public Coin randomCoin(GeoPoint location) {
       int i = rand.nextInt(30);
       return new Coin(i, location);
     }

     public GeoPoint randomGeoPointAroundLocation(GeoPoint location) {
       double latitude = location.getLatitude();
       double longitude = location.getLongitude();
       double leftLimit = -0.1;
       double rightLimit = 0.12;
       double randomExt = leftLimit + rand.nextDouble()*(rightLimit - leftLimit);
       double newLat = latitude + randomExt;
       double anotherRandomExt = leftLimit + rand.nextDouble()*(rightLimit - leftLimit);
       double newLong = longitude + anotherRandomExt;
       return new GeoPoint(newLong, newLat);
     }
}
