package ch.epfl.sdp;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.artificial_intelligence.Boundable;
import ch.epfl.sdp.artificial_intelligence.CartesianPoint;
import ch.epfl.sdp.artificial_intelligence.Enemy;
import ch.epfl.sdp.artificial_intelligence.GenPoint;
import ch.epfl.sdp.artificial_intelligence.LocalBounds;
import ch.epfl.sdp.artificial_intelligence.MovementType;
import ch.epfl.sdp.artificial_intelligence.MovingArtificialEntity;
import ch.epfl.sdp.artificial_intelligence.PointConverter;
import ch.epfl.sdp.artificial_intelligence.RectangleBounds;
import ch.epfl.sdp.artificial_intelligence.UnboundedArea;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapApi;
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
    }

    @Test
    public void LinearMovementWorks() {
        MovingArtificialEntity movingArtificialEntity = new Enemy();
        movingArtificialEntity.getMovement().setAcceleration(2);
        Boundable boundable = new UnboundedArea();
        boundable.isInside(null);
        movingArtificialEntity.setBounds(boundable);
        GenPoint genPoint = new CartesianPoint(50, 60);
        movingArtificialEntity.setPosition(genPoint);
        movingArtificialEntity.getMovement().setMovementType(MovementType.LINEAR);
        movingArtificialEntity.setMoving(true);
        movingArtificialEntity.getMovement().setOrientation(0);
        movingArtificialEntity.getMovement().setVelocity(10);

        assertEquals(2, movingArtificialEntity.getMovement().getAcceleration(), 0.01);
        assertEquals(MovementType.LINEAR, movingArtificialEntity.getMovement().getMovementType());
        assertEquals(10.0, movingArtificialEntity.getMovement().getVelocity(), 0.01);
        assertEquals(0.0, movingArtificialEntity.getMovement().getOrientation(), 0.01);
        assertEquals(true, movingArtificialEntity.isMoving());

        movingArtificialEntity.update();

        assertEquals(60, movingArtificialEntity.getPosition().getArg1(), 0.01);
        assertEquals(60, movingArtificialEntity.getPosition().getArg2(), 0.01);
    }

    // TODO Redo sinusoidal movement test
  /*  @Test
    public void SinusMovementWorks() {
        MovingArtificialEntity movingArtificialEntity = new Enemy();
        movingArtificialEntity.getMovement().setAcceleration(2);
        Boundable boundable = new UnboundedArea();
        movingArtificialEntity.setBounds(boundable);
        movingArtificialEntity.setPosition(PointConverter.GeoPointToGenPoint(MapsActivity.mapApi.getCurrentLocation()));
        movingArtificialEntity.getMovement().setMovementType(MovementType.SINUSOIDAL);
        movingArtificialEntity.setMoving(true);
        movingArtificialEntity.getMovement().setOrientation(0);
        movingArtificialEntity.getMovement().setVelocity(10);
        movingArtificialEntity.getMovement().setSinusAmplitude(2);;
        movingArtificialEntity.sinusAngleStep = 2 * Math.PI / 4;

        movingArtificialEntity.update();
        assertEquals(20, movingArtificialEntity.getPosition().getArg1(), 0.01);
        assertEquals(12, movingArtificialEntity.getPosition().getArg2(), 0.01);

        movingArtificialEntity.update();
        assertEquals(30, movingArtificialEntity.getPosition().getArg1(), 0.01);
        assertEquals(10, movingArtificialEntity.getPosition().getArg2(), 0.01);

        movingArtificialEntity.update();
        assertEquals(40, movingArtificialEntity.getPosition().getArg1(), 0.01);
        assertEquals(8, movingArtificialEntity.getPosition().getArg2(), 0.01);

        movingArtificialEntity.update();
        assertEquals(50, movingArtificialEntity.getPosition().getArg1(), 0.01);
        assertEquals(10, movingArtificialEntity.getPosition().getArg2(), 0.01);
    }*/

    @Test
    public void secondConstructorWorks() {
        Boundable boundable = new UnboundedArea();
        MovingArtificialEntity movingArtificialEntity = new Enemy();
        movingArtificialEntity.setBounds(boundable);
        assertEquals(boundable, movingArtificialEntity.getBounds());
        assertEquals(true, boundable.isInside(movingArtificialEntity.getPosition()));
    }

   /* @Test
    public void unimplementedMovement() {
        Boundable boundable = new UnboundedArea();
        MovingArtificialEntity movingArtificialEntity = new MovingArtificialEntity(boundable);
        movingArtificialEntity.setMoving(true);
        movingArtificialEntity.setMovement(Movement.CURVED);
        movingArtificialEntity.update();
        assertEquals(null, movingArtificialEntity.getPosition());
        movingArtificialEntity.setMovement(Movement.SMOOTH);
        movingArtificialEntity.update();
        assertEquals(null, movingArtificialEntity.getPosition());
        movingArtificialEntity.setMovement(Movement.CIRCULAR);
        movingArtificialEntity.update();
        assertEquals(null, movingArtificialEntity.getPosition());

        movingArtificialEntity.setOrientation(1);
        movingArtificialEntity.setBounds(new RectangleBounds(5, 10, null));
        movingArtificialEntity.setMovement(Movement.RANDOM);
        movingArtificialEntity.update();
        assertEquals(null, movingArtificialEntity.getPosition());
        assertEquals(1.0, movingArtificialEntity.getOrientation(), 0.01);
        movingArtificialEntity.setMovement(Movement.CURVED);
        movingArtificialEntity.update();
        assertEquals(null, movingArtificialEntity.getPosition());
        assertEquals(1.0, movingArtificialEntity.getOrientation(), 0.01);
        movingArtificialEntity.setMovement(Movement.SMOOTH);
        movingArtificialEntity.update();
        assertEquals(null, movingArtificialEntity.getPosition());
        assertEquals(1.0, movingArtificialEntity.getOrientation(), 0.01);
        movingArtificialEntity.setMovement(Movement.CIRCULAR);
        movingArtificialEntity.update();
        assertEquals(null, movingArtificialEntity.getPosition());
        assertEquals(1.0, movingArtificialEntity.getOrientation(), 0.01);
    }*/

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
        GeoPoint location = new GeoPoint(40,50);
        Boundable rectangleBounds = new RectangleBounds(50, 50);
        LocalBounds patrolBounds = new LocalBounds(rectangleBounds, PointConverter.GeoPointToGenPoint(location));
        MovingArtificialEntity movingArtificialEntity = new Enemy(patrolBounds, new UnboundedArea());
        movingArtificialEntity.setBounds(rectangleBounds);
        movingArtificialEntity.setMoving(true);
        movingArtificialEntity.getMovement().setMovementType(MovementType.LINEAR);
        movingArtificialEntity.getMovement().setVelocity(10);
        movingArtificialEntity.setLocation(location);

        map.setCurrentLocation(location);
        //PointConverter.GenPointToGeoPoint(new CartesianPoint(-1,-1), MapsActivity.mapApi.getCurrentLocation());

        for (int i = 0; i < 1000; ++i) {
            movingArtificialEntity.update();
            assertEquals(true, patrolBounds.isInside(movingArtificialEntity.getPosition()));
        }
    }
}
