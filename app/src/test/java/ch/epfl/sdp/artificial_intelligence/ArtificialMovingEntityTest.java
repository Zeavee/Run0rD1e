package ch.epfl.sdp.artificial_intelligence;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.geometry.Area;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.geometry.LocalArea;
import ch.epfl.sdp.geometry.RectangleArea;
import ch.epfl.sdp.geometry.UnboundedArea;
import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.map.MockMap;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ArtificialMovingEntityTest {
    MockMap map;

    @Before
    public void setup(){
        map = new MockMap();
        Game.getInstance().setMapApi(map);
        PlayerManager.getInstance().setCurrentUser(new Player(40, 50, 10, "owner", "owner@owner.com"));
    }

    @Test
    public void LinearMovementWorks() {
        ArtificialMovingEntity artificialMovingEntity = new Enemy();
        //artificialMovingEntity.setLocalArea(new UnboundedArea());
        LinearMovement movement = new LinearMovement();
        artificialMovingEntity.setMovement(movement);

        movement.setVelocity(10);
        movement.setAcceleration(2);

        assertEquals(2, artificialMovingEntity.getMovement().getAcceleration(), 0.01);
        assertEquals(10.0, artificialMovingEntity.getMovement().getVelocity(), 0.01);
        assertEquals(0.0, artificialMovingEntity.getMovement().getOrientation(), 0.01);
        assertEquals(true, artificialMovingEntity.isMoving());

        artificialMovingEntity.update();

        assertEquals(60, artificialMovingEntity.getLocation().getX(), 0.01);
        assertEquals(60, artificialMovingEntity.getLocation().getY(), 0.01);
    }

    @Test
    public void SinusMovementWorks() {
        GeoPoint initialPosition = new GeoPoint(40, 50);
        ArtificialMovingEntity artificialMovingEntity = new Enemy();
        //artificialMovingEntity.setLocalArea(new UnboundedArea());
        SinusoidalMovement movement = new SinusoidalMovement(initialPosition, 2, 2 * Math.PI / 4);

        movement.setVelocity(10);
        artificialMovingEntity.setMovement(movement);

        artificialMovingEntity.update();
        assertEquals(10 + initialPosition.getX(), artificialMovingEntity.getLocation().getX(), 0.01);
        assertEquals(0 + initialPosition.getY(), artificialMovingEntity.getLocation().getY(), 0.01);

        artificialMovingEntity.update();
        assertEquals(20 + initialPosition.getX(), artificialMovingEntity.getLocation().getX(), 0.01);
        assertEquals(2 + initialPosition.getY(), artificialMovingEntity.getLocation().getY(), 0.01);

        artificialMovingEntity.update();
        assertEquals(30 + initialPosition.getX(), artificialMovingEntity.getLocation().getX(), 0.01);
        assertEquals(2 + initialPosition.getY(), artificialMovingEntity.getLocation().getY(), 0.01);

        artificialMovingEntity.update();
        assertEquals(40 + initialPosition.getX(), artificialMovingEntity.getLocation().getX(), 0.01);
        assertEquals(0 + initialPosition.getY(), artificialMovingEntity.getLocation().getY(), 0.01);
    }

    @Test
    public void secondConstructorWorks() {
        Area area = new UnboundedArea();
        ArtificialMovingEntity artificialMovingEntity = new Enemy();
        //artificialMovingEntity.setLocalArea(area);
        assertEquals(area, artificialMovingEntity.getLocalArea());
        //assertEquals(true, area.isInside(artificialMovingEntity.getLocation()));
    }

    @Test
    public void entityDoesNotGetOutOfBoundsWithLinear() {
        GeoPoint entityLocation = new GeoPoint(40, 50);
        GeoPoint entityPos = entityLocation;
        Area rectangleBounds = new RectangleArea(50, 50);
        LocalArea patrolBounds = new LocalArea(rectangleBounds, entityPos);
        ArtificialMovingEntity artificialMovingEntity = new Enemy(0, patrolBounds, rectangleBounds);
        LinearMovement movement = new LinearMovement();

        movement.setVelocity(10);
        artificialMovingEntity.setMovement(movement);

        for (int i = 0; i < 1000; ++i) {
            artificialMovingEntity.update();
            assertTrue(patrolBounds.isInside(artificialMovingEntity.getLocation()));
        }
    }

    @Test
    public void setPositionWorks() {
        GeoPoint position = new GeoPoint(10, 10);
        Movement movement = new LinearMovement();
        ArtificialMovingEntity artificialMovingEntity = new ArtificialMovingEntity(movement, new UnboundedArea(), true) {
            @Override
            public void displayOn(MapApi mapApi) {

            }
        };
        artificialMovingEntity.setPosition(position);
        assertEquals(position, artificialMovingEntity.getLocation());
    }
}