package ch.epfl.sdp;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.artificial_intelligence.Boundable;
import ch.epfl.sdp.artificial_intelligence.CartesianPoint;
import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.artificial_intelligence.GenPoint;
import ch.epfl.sdp.artificial_intelligence.LinearMovement;
import ch.epfl.sdp.artificial_intelligence.LocalBounds;
import ch.epfl.sdp.artificial_intelligence.MovingArtificialEntity;
import ch.epfl.sdp.artificial_intelligence.PointConverter;
import ch.epfl.sdp.artificial_intelligence.RectangleBounds;
import ch.epfl.sdp.artificial_intelligence.SinusoidalMovement;
import ch.epfl.sdp.artificial_intelligence.UnboundedArea;
import ch.epfl.sdp.logic.RandomGenerator;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapsActivity;

import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;
import static junit.framework.TestCase.assertEquals;

public class MovingArtificialEntityTest {
    MockMapApi map;

    @Before
    public void setup(){
        map = new MockMapApi();
        MapsActivity.setMapApi(map);
        map.setCurrentLocation(new GeoPoint(40, 50));
    }

    @Test
    public void LinearMovementWorks() {
        MovingArtificialEntity movingArtificialEntity = new Enemy();
        movingArtificialEntity.setBounds(new UnboundedArea());
        LinearMovement movement = new LinearMovement(new CartesianPoint(50, 60));
        movingArtificialEntity.setMovement(movement);

        movement.setVelocity(10);
        movement.setAcceleration(2);

        assertEquals(2, movingArtificialEntity.getMovement().getAcceleration(), 0.01);
        assertEquals(10.0, movingArtificialEntity.getMovement().getVelocity(), 0.01);
        assertEquals(0.0, movingArtificialEntity.getMovement().getOrientation(), 0.01);
        assertEquals(true, movingArtificialEntity.isMoving());

        movingArtificialEntity.update();

        assertEquals(60, movingArtificialEntity.getPosition().getArg1(), 0.01);
        assertEquals(60, movingArtificialEntity.getPosition().getArg2(), 0.01);
    }

    @Test
    public void SinusMovementWorks() {
        GenPoint initialPosition = PointConverter.GeoPointToGenPoint(map.getCurrentLocation());
        MovingArtificialEntity movingArtificialEntity = new Enemy();
        movingArtificialEntity.setBounds(new UnboundedArea());
        SinusoidalMovement movement = new SinusoidalMovement(initialPosition, 2, 2 * Math.PI / 4);

        movement.setVelocity(10);
        movingArtificialEntity.setMovement(movement);

        movingArtificialEntity.update();
        assertEquals(10 + initialPosition.getArg1(), movingArtificialEntity.getPosition().getArg1(), 0.01);
        assertEquals(2 + initialPosition.getArg2(), movingArtificialEntity.getPosition().getArg2(), 0.01);

        movingArtificialEntity.update();
        assertEquals(20 + initialPosition.getArg1(), movingArtificialEntity.getPosition().getArg1(), 0.01);
        assertEquals(2 + initialPosition.getArg2(), movingArtificialEntity.getPosition().getArg2(), 0.01);

        movingArtificialEntity.update();
        assertEquals(30 + initialPosition.getArg1(), movingArtificialEntity.getPosition().getArg1(), 0.01);
        assertEquals(0 + initialPosition.getArg2(), movingArtificialEntity.getPosition().getArg2(), 0.01);

        movingArtificialEntity.update();
        assertEquals(40 + initialPosition.getArg1(), movingArtificialEntity.getPosition().getArg1(), 0.01);
        assertEquals(0 + initialPosition.getArg2(), movingArtificialEntity.getPosition().getArg2(), 0.01);
    }

    @Test
    public void secondConstructorWorks() {
        Boundable boundable = new UnboundedArea();
        MovingArtificialEntity movingArtificialEntity = new Enemy();
        movingArtificialEntity.setBounds(boundable);
        assertEquals(boundable, movingArtificialEntity.getBounds());
        assertEquals(true, boundable.isInside(movingArtificialEntity.getPosition()));
    }

    @Test
    public void conversionsWork() {
        GenPoint cartesianPoint = new CartesianPoint(2, 2);
        assertEquals(2.0, cartesianPoint.toCartesian().getArg1(), 0.01);
        assertEquals(2.0, cartesianPoint.toPolar().toCartesian().getArg1(), 0.01);
        assertEquals(sqrt(8), cartesianPoint.toPolar().getArg1(), 0.01);
        assertEquals(toRadians(45), cartesianPoint.toPolar().getArg2(), 0.01);
        assertEquals(sqrt(8), cartesianPoint.toPolar().toPolar().getArg1(), 0.01);
    }

    @Test
    public void entityDoesNotGetOutOfBoundsWithLinear() {
        GeoPoint entityLocation = new GeoPoint(40, 50);
        GenPoint entityPos = PointConverter.GeoPointToGenPoint(entityLocation);
        Boundable rectangleBounds = new RectangleBounds(50, 50, new RandomGenerator().randomGeoPoint());
        LocalBounds patrolBounds = new LocalBounds(rectangleBounds, entityPos);
        MovingArtificialEntity movingArtificialEntity = new Enemy(patrolBounds, rectangleBounds);
        LinearMovement movement = new LinearMovement(entityPos);

        movement.setVelocity(10);
        movingArtificialEntity.setMovement(movement);


        map.setCurrentLocation(entityLocation);
        //PointConverter.GenPointToGeoPoint(new CartesianPoint(-1,-1), MapsActivity.mapApi.getCurrentLocation());

        for (int i = 0; i < 1000; ++i) {
            movingArtificialEntity.update();
            assertEquals(true, patrolBounds.isInside(movingArtificialEntity.getPosition()));
        }
    }
}