package ch.epfl.sdp;

import org.junit.Test;

import ch.epfl.sdp.artificial_intelligence.Boundable;
import ch.epfl.sdp.artificial_intelligence.CartesianPoint;
import ch.epfl.sdp.artificial_intelligence.GenPoint;
import ch.epfl.sdp.artificial_intelligence.Movement;
import ch.epfl.sdp.artificial_intelligence.MovingEntity;
import ch.epfl.sdp.artificial_intelligence.RectangleBounds;
import ch.epfl.sdp.artificial_intelligence.UnboundedArea;

import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;
import static junit.framework.TestCase.assertEquals;

public class ArtificialIntelligenceTest {
    @Test
    public void LinearMovementWorks() {
        MovingEntity movingEntity = new MovingEntity();
        movingEntity.setAcceleration(2);
        Boundable boundable = new UnboundedArea();
        boundable.isInside(null);
        movingEntity.setBounds(boundable);
        GenPoint genPoint = new CartesianPoint(50, 60);
        movingEntity.setPosition(genPoint);
        movingEntity.setMovement(Movement.LINEAR);
        movingEntity.setMoving(true);
        movingEntity.setOrientation(0);
        movingEntity.setVelocity(10);

        assertEquals(2, movingEntity.getAcceleration(), 0.01);
        assertEquals(Movement.LINEAR, movingEntity.getMovement());
        assertEquals(10.0, movingEntity.getVelocity(), 0.01);
        assertEquals(0.0, movingEntity.getOrientation(), 0.01);
        assertEquals(true, movingEntity.isMoving());

        movingEntity.update();

        assertEquals(60, movingEntity.getPosition().getArg1(), 0.01);
        assertEquals(60, movingEntity.getPosition().getArg2(), 0.01);
    }

    @Test
    public void secondConstructorWorks() {
        Boundable boundable = new UnboundedArea();
        MovingEntity movingEntity = new MovingEntity(boundable);
        assertEquals(boundable, movingEntity.getBounds());
        assertEquals(true, boundable.isInside(movingEntity.getPosition()));
    }

    @Test
    public void unimplementedMovement() {
        Boundable boundable = new UnboundedArea();
        MovingEntity movingEntity = new MovingEntity(boundable);
        movingEntity.setMoving(true);
        movingEntity.setMovement(Movement.CURVED);
        movingEntity.update();
        assertEquals(null, movingEntity.getPosition());
        movingEntity.setMovement(Movement.SMOOTH);
        movingEntity.update();
        assertEquals(null, movingEntity.getPosition());
        movingEntity.setMovement(Movement.SINUSOIDAL);
        movingEntity.update();
        assertEquals(null, movingEntity.getPosition());
        movingEntity.setMovement(Movement.CIRCULAR);
        movingEntity.update();
        assertEquals(null, movingEntity.getPosition());

        movingEntity.setOrientation(1);
        movingEntity.setBounds(new RectangleBounds(5, 10));
        movingEntity.setMovement(Movement.RANDOM);
        movingEntity.update();
        assertEquals(null, movingEntity.getPosition());
        assertEquals(1.0, movingEntity.getOrientation(), 0.01);
        movingEntity.setMovement(Movement.CURVED);
        movingEntity.update();
        assertEquals(null, movingEntity.getPosition());
        assertEquals(1.0, movingEntity.getOrientation(), 0.01);
        movingEntity.setMovement(Movement.SMOOTH);
        movingEntity.update();
        assertEquals(null, movingEntity.getPosition());
        assertEquals(1.0, movingEntity.getOrientation(), 0.01);
        movingEntity.setMovement(Movement.SINUSOIDAL);
        movingEntity.update();
        assertEquals(null, movingEntity.getPosition());
        assertEquals(1.0, movingEntity.getOrientation(), 0.01);
        movingEntity.setMovement(Movement.CIRCULAR);
        movingEntity.update();
        assertEquals(null, movingEntity.getPosition());
        assertEquals(1.0, movingEntity.getOrientation(), 0.01);
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
        Boundable rectangleBounds = new RectangleBounds(50, 50);
        MovingEntity movingEntity = new MovingEntity(rectangleBounds);
        movingEntity.setMoving(true);
        movingEntity.setMovement(Movement.LINEAR);
        movingEntity.setVelocity(10);
        for (int i = 0; i < 1000; ++i) {
            movingEntity.update();
            assertEquals(true, rectangleBounds.isInside(movingEntity.getPosition()));
        }
    }
}
