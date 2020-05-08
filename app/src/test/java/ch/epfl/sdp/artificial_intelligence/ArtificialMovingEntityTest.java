package ch.epfl.sdp.artificial_intelligence;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entity.Enemy;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.GameThread;
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
    ArtificialMovingEntity ame;

    @Before
    public void setup(){
        map = new MockMap();
        Game.getInstance().setMapApi(map);
        PlayerManager.getInstance().setCurrentUser(new Player(40, 50, 10, "owner", "owner@owner.com"));

        ame = new ArtificialMovingEntity() {
            @Override
            public void displayOn(MapApi mapApi) {

            }
        };
    }

    @Test
    public void LinearMovementWorks() {
        ArtificialMovingEntity artificialMovingEntity = new Enemy();
        GeoPoint location = new GeoPoint(0,0);
        artificialMovingEntity.setLocation(location);


        LinearMovement movement = new LinearMovement();
        artificialMovingEntity.setMovement(movement);

        movement.setVelocity(10);
        movement.setAcceleration(2);

        assertEquals(2, artificialMovingEntity.getMovement().getAcceleration(), 0.01);
        assertEquals(10.0/GameThread.FPS, artificialMovingEntity.getMovement().getVelocity(), 0.01);
        assertEquals(0.0, artificialMovingEntity.getMovement().getOrientation(), 0.01);
        assertEquals(true, artificialMovingEntity.isMoving());

        artificialMovingEntity.update();

        assertEquals(0.41, artificialMovingEntity.getLocation().getX() - location.getX(), 0.01);
        assertEquals(0, artificialMovingEntity.getLocation().getY(), 0.01);
    }

    @Test
    public void SinusMovementWorks() {
        GeoPoint location = new GeoPoint(40, 50);
        ame.setLocation(location);
        SinusoidalMovement movement = new SinusoidalMovement(2, 2 * Math.PI / 4);
        movement.setVelocity(100);
        ame.setMovement(movement);

        ame.update();
        assertEquals(4.17 + location.getX(), ame.getLocation().getX(), 0.01);
        assertEquals(0 + location.getY(), ame.getLocation().getY(), 0.01);

        ame.update();
        assertEquals(8.34 + location.getX(), ame.getLocation().getX(), 0.01);
        assertEquals(2 + location.getY(), ame.getLocation().getY(), 0.01);

        ame.update();
        assertEquals(12.50 + location.getX(), ame.getLocation().getX(), 0.01);
        assertEquals(2 + location.getY(), ame.getLocation().getY(), 0.01);

        ame.update();
        assertEquals(16.67+ location.getX(), ame.getLocation().getX(), 0.01);
        assertEquals(0 + location.getY(), ame.getLocation().getY(), 0.01);
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
        Area rectangleBounds = new RectangleArea(1, 1);
        LocalArea localArea = new LocalArea(rectangleBounds, entityLocation);
        LinearMovement movement = new LinearMovement();

        ame.setLocation(entityLocation);
        ame.setMovement(movement);
        ame.setLocalArea(localArea);

        movement.setVelocity(10);
        ame.setMovement(movement);

        for (int i = 0; i < 1000; ++i) {
            ame.update();
            assertTrue(localArea.isInside(ame.getLocation()));
        }
    }
}