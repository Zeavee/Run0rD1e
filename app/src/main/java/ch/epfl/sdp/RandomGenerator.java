package ch.epfl.sdp;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.sdp.artificial_intelligence.CartesianPoint;
import ch.epfl.sdp.artificial_intelligence.GenPoint;
import ch.epfl.sdp.artificial_intelligence.PolarPoint;

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
       if (a == 0) {
           a = 1;
       } else if (a < 0) {
           a = -1 * a;
       }

       if (b == 0) {
           b = 1;
       } else if(b < 0) {
           b = -1* b;
       }

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
       Player p = new Player(g.longitude(), g.latitude(), rand.nextDouble()+50, randomString(10), randomEmail());
       return p;
     }
}
