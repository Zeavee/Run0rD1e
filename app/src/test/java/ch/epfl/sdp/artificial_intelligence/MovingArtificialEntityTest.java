package ch.epfl.sdp.artificial_intelligence;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.map.MockMapApi;
import ch.epfl.sdp.geometry.Area;
import ch.epfl.sdp.geometry.CartesianPoint;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.LocalArea;
import ch.epfl.sdp.geometry.PointConverter;
import ch.epfl.sdp.geometry.RectangleArea;
import ch.epfl.sdp.geometry.UnboundedArea;
import ch.epfl.sdp.map.MapsActivity;

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
        movingArtificialEntity.setArea(new UnboundedArea());
        LinearMovement movement = new LinearMovement(new CartesianPoint(50, 60));
        movingArtificialEntity.setMovement(movement);

        movement.setVelocity(10);
        movement.setAcceleration(2);

        assertEquals(2, movingArtificialEntity.getMovement().getAcceleration(), 0.01);
        assertEquals(10.0, movingArtificialEntity.getMovement().getVelocity(), 0.01);
        assertEquals(0.0, movingArtificialEntity.getMovement().getOrientation(), 0.01);
        assertEquals(true, movingArtificialEntity.isMoving());

        movingArtificialEntity.update();

        assertEquals(60, movingArtificialEntity.getPosition().getX(), 0.01);
        assertEquals(60, movingArtificialEntity.getPosition().getY(), 0.01);
    }

    @Test
    public void SinusMovementWorks() {
        CartesianPoint initialPosition = PointConverter.geoPointToCartesianPoint(map.getCurrentLocation());
        MovingArtificialEntity movingArtificialEntity = new Enemy();
        movingArtificialEntity.setArea(new UnboundedArea());
        SinusoidalMovement movement = new SinusoidalMovement(initialPosition, 2, 2 * Math.PI / 4);

        movement.setVelocity(10);
        movingArtificialEntity.setMovement(movement);

        movingArtificialEntity.update();
        assertEquals(10 + initialPosition.getX(), movingArtificialEntity.getPosition().getX(), 0.01);
        assertEquals(0 + initialPosition.getY(), movingArtificialEntity.getPosition().getY(), 0.01);

        movingArtificialEntity.update();
        assertEquals(20 + initialPosition.getX(), movingArtificialEntity.getPosition().getX(), 0.01);
        assertEquals(2 + initialPosition.getY(), movingArtificialEntity.getPosition().getY(), 0.01);

        movingArtificialEntity.update();
        assertEquals(30 + initialPosition.getX(), movingArtificialEntity.getPosition().getX(), 0.01);
        assertEquals(2 + initialPosition.getY(), movingArtificialEntity.getPosition().getY(), 0.01);

        movingArtificialEntity.update();
        assertEquals(40 + initialPosition.getX(), movingArtificialEntity.getPosition().getX(), 0.01);
        assertEquals(0 + initialPosition.getY(), movingArtificialEntity.getPosition().getY(), 0.01);
    }

    @Test
    public void secondConstructorWorks() {
        Area area = new UnboundedArea();
        MovingArtificialEntity movingArtificialEntity = new Enemy();
        movingArtificialEntity.setArea(area);
        assertEquals(area, movingArtificialEntity.getArea());
        assertEquals(true, area.isInside(movingArtificialEntity.getPosition()));
    }

    @Test
    public void entityDoesNotGetOutOfBoundsWithLinear() {
        GeoPoint entityLocation = new GeoPoint(40, 50);
        CartesianPoint entityPos = PointConverter.geoPointToCartesianPoint(entityLocation);
        Area rectangleBounds = new RectangleArea(50, 50);
        LocalArea patrolBounds = new LocalArea(rectangleBounds, entityPos);
        MovingArtificialEntity movingArtificialEntity = new Enemy(0, patrolBounds, rectangleBounds);
        LinearMovement movement = new LinearMovement(entityPos);

        movement.setVelocity(10);
        movingArtificialEntity.setMovement(movement);


        map.setCurrentLocation(entityLocation);
//        for (int i = 0; i < 1000; ++i) {
//            movingArtificialEntity.update();
//            assertEquals(false, patrolBounds.isInside(movingArtificialEntity.getPosition()));
//        }
    }
}