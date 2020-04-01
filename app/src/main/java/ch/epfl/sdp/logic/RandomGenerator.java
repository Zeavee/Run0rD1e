package ch.epfl.sdp.logic;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.sdp.artificial_intelligence.CartesianPoint;
import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.artificial_intelligence.GenPoint;
import ch.epfl.sdp.artificial_intelligence.LocalBounds;
import ch.epfl.sdp.artificial_intelligence.RectangleBounds;
import ch.epfl.sdp.artificial_intelligence.UnboundedArea;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Scan;
import ch.epfl.sdp.item.Shield;
import ch.epfl.sdp.item.Shrinker;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapApi;

public class RandomGenerator {
   private Random rand;
   private ArrayList alpha_numerics;

   public RandomGenerator(){
       rand = new Random();

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

     public GenPoint randomGenPoint(int a, int b) {
       CartesianPoint point = new CartesianPoint(rand.nextInt(a), rand.nextInt(b));
       return point;
     }

     public Healthpack randomHealthPack() {
       GeoPoint A = randomGeoPoint();
       Healthpack h = new Healthpack(A, false, rand.nextInt(25)+25);
       return h;
     }

     public Shield randomShield() {
       GeoPoint A = randomGeoPoint();
       Shield s = new Shield(A, false, rand.nextDouble()*10+20);
       return s;
     }

     public Shrinker randomShrinker() {
       GeoPoint A =  randomGeoPoint();
       Shrinker s = new Shrinker(A, false, rand.nextDouble(), rand.nextDouble());
       return s;
     }

     public Scan randomScan() {
       MapApi map = null;
       Scan s = new Scan(randomGeoPoint(), false, rand.nextDouble(), map);
       return s;
     }

     public Player randomPlayer() {
       GeoPoint g = randomGeoPoint();
       Player p = new Player(g.getLongitude(), g.getLatitude(), rand.nextDouble()+50, randomString(10), randomEmail());
       return p;
     }

     public Enemy randomEnemy() {
         GeoPoint g1 = randomGeoPoint();
         Player p1 = new Player(g1.getLongitude(), g1.getLatitude(), rand.nextDouble()+50, randomString(10), randomEmail());
         GeoPoint g2 = randomGeoPoint();
         Player p2 = new Player(g2.getLongitude(), g2.getLatitude(), rand.nextDouble()+50, randomString(10), randomEmail());
         GeoPoint g3 = randomGeoPoint();
         Player p3 = new Player(g3.getLongitude(), g3.getLatitude(), rand.nextDouble()+50, randomString(10), randomEmail());
         GeoPoint g4 = randomGeoPoint();
         Player p4 = new Player(g4.getLongitude(), g4.getLatitude(), rand.nextDouble()+50, randomString(10), randomEmail());
         GeoPoint g5 = randomGeoPoint();
         Player p5 = new Player(g5.getLongitude(), g5.getLatitude(), rand.nextDouble()+50, randomString(10), randomEmail());

         PlayerManager playerManager = PlayerManager.getInstance();
         playerManager.addPlayer(p1);
         playerManager.addPlayer(p2);
         playerManager.addPlayer(p3);
         playerManager.addPlayer(p4);
         playerManager.addPlayer(p5);

         int randBound = rand.nextInt(20);
         int randomDmg = rand.nextInt(randBound);
         float randomdps = rand.nextFloat();
         float randomDetectionDistance = rand.nextFloat()*10 + 50;
         RectangleBounds r = new RectangleBounds(10, 10, randomGeoPoint());

         LocalBounds l = new LocalBounds(r, randomGenPoint(1,5));
         UnboundedArea randomArea = new UnboundedArea();

         Enemy e = new Enemy(randomDmg, randomdps, randomDetectionDistance, 50, l, randomArea);
         return e;
     }
}
