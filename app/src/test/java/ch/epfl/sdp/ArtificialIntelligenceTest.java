package ch.epfl.sdp;

import org.junit.Test;

import ch.epfl.sdp.artificial_intelligence.Boundable;
import ch.epfl.sdp.artificial_intelligence.CartesianPoint;
import ch.epfl.sdp.artificial_intelligence.GenPoint;
import ch.epfl.sdp.artificial_intelligence.Movement;
import ch.epfl.sdp.artificial_intelligence.MovingArtificialEntity;
import ch.epfl.sdp.artificial_intelligence.RectangleBounds;
import ch.epfl.sdp.artificial_intelligence.UnboundedArea;

import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;
import static junit.framework.TestCase.assertEquals;

public class ArtificialIntelligenceTest {
    @Test
    public void LinearMovementWorks() {
        MovingArtificialEntity movingArtificialEntity = new MovingArtificialEntity();
        movingArtificialEntity.setAcceleration(2);
        Boundable boundable = new UnboundedArea();
        boundable.isInside(null);
        movingArtificialEntity.setBounds(boundable);
        GenPoint genPoint = new CartesianPoint(50, 60);
        movingArtificialEntity.setPosition(genPoint);
        movingArtificialEntity.setMovement(Movement.LINEAR);
        movingArtificialEntity.setMoving(true);
        movingArtificialEntity.setOrientation(0);
        movingArtificialEntity.setVelocity(10);

        assertEquals(2, movingArtificialEntity.getAcceleration(), 0.01);
        assertEquals(Movement.LINEAR, movingArtificialEntity.getMovement());
        assertEquals(10.0, movingArtificialEntity.getVelocity(), 0.01);
        assertEquals(0.0, movingArtificialEntity.getOrientation(), 0.01);
        assertEquals(true, movingArtificialEntity.isMoving());

        movingArtificialEntity.update();

        assertEquals(60, movingArtificialEntity.getPosition().getArg1(), 0.01);
        assertEquals(60, movingArtificialEntity.getPosition().getArg2(), 0.01);
    }

    @Test
    public void SinusMovementWorks() {
        MovingArtificialEntity movingArtificialEntity = new MovingArtificialEntity();
        movingArtificialEntity.setAcceleration(2);
        Boundable boundable = new UnboundedArea();
        boundable.isInside(null);
        movingArtificialEntity.setBounds(boundable);
        GenPoint genPoint = new CartesianPoint(10, 10);
        movingArtificialEntity.setPosition(genPoint);
        movingArtificialEntity.setMovement(Movement.SINUSOIDAL);
        movingArtificialEntity.setMoving(true);
        movingArtificialEntity.setOrientation(0);
        movingArtificialEntity.setVelocity(10);
        movingArtificialEntity.sinusAmplitude = 2;
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
    }

    @Test
    public void secondConstructorWorks() {
        Boundable boundable = new UnboundedArea();
        MovingArtificialEntity movingArtificialEntity = new MovingArtificialEntity(boundable);
        assertEquals(boundable, movingArtificialEntity.getBounds());
        assertEquals(true, boundable.isInside(movingArtificialEntity.getPosition()));
    }

    @Test
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
        Boundable rectangleBounds = new RectangleBounds(50, 50, null);
        MovingArtificialEntity movingArtificialEntity = new MovingArtificialEntity(rectangleBounds);
        movingArtificialEntity.setMoving(true);
        movingArtificialEntity.setMovement(Movement.LINEAR);
        movingArtificialEntity.setVelocity(10);
        for (int i = 0; i < 1000; ++i) {
            movingArtificialEntity.update();
            assertEquals(true, rectangleBounds.isInside(movingArtificialEntity.getPosition()));
        }
    }
}
