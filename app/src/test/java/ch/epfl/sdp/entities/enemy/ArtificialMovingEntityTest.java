package ch.epfl.sdp.entities.enemy;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.entities.enemy.ArtificialMovingEntity;
import ch.epfl.sdp.entities.enemy.Enemy;
import ch.epfl.sdp.entities.enemy.artificial_intelligence.LinearMovement;
import ch.epfl.sdp.entities.player.Player;
import ch.epfl.sdp.entities.player.PlayerManager;
import ch.epfl.sdp.entities.enemy.artificial_intelligence.SinusoidalMovement;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.GameThread;
import ch.epfl.sdp.geometry.area.Area;
import ch.epfl.sdp.map.location.GeoPoint;
import ch.epfl.sdp.geometry.area.RectangleArea;
import ch.epfl.sdp.map.display.MapApi;
import ch.epfl.sdp.map.MockMap;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertSame;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

public class ArtificialMovingEntityTest {
    private MockMap map;
    private ArtificialMovingEntity ame;

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
    public void testLinearMovementIsLinear() {
        ArtificialMovingEntity artificialMovingEntity = new Enemy();
        GeoPoint location = new GeoPoint(50,40);
        artificialMovingEntity.setLocation(location);

        LinearMovement movement = new LinearMovement();
        artificialMovingEntity.setMovement(movement);

        movement.setVelocity(10);

        assertEquals(10.0/GameThread.FPS, artificialMovingEntity.getMovement().getVelocity(), 0.01);
        assertEquals(0.0, artificialMovingEntity.getMovement().getOrientation(), 0.01);
        assertTrue(artificialMovingEntity.isMoving());

        artificialMovingEntity.update();

        assertEquals(0.41, artificialMovingEntity.getLocation().getX() - location.getX(), 0.01);
        assertEquals(0, artificialMovingEntity.getLocation().getY() - location.getY(), 0.01);
    }

    @Test
    public void testSinusMovementIsSinusoidal() {
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
    public void testEntityDoesNotGetOutOfBoundsWithLinearMovement() {
        GeoPoint location = new GeoPoint(40, 50);
        setArtificialMovingEntity(location, location);

        for (int i = 0; i < 1000; ++i) {
            ame.update();
            assertTrue(ame.getLocalArea().isInside(ame.getLocation()));
        }
    }

    @Test
    public void testEntityDoesNotMovesWhenOutsideArea(){
        setArtificialMovingEntity(new GeoPoint(40, 50), new GeoPoint(0, 0));
        GeoPoint entityLocation = ame.getLocation();

        for (int i = 0; i < 1000; ++i) {
            ame.update();
            assertFalse(ame.getLocalArea().isInside(ame.getLocation()));
            assertSame(ame.getLocation(), entityLocation);
        }
    }

    @Test
    public void testEntityMovesWhenOutsideAreaIfForceMoveIsTrue(){
        setArtificialMovingEntity(new GeoPoint(40, 50), new GeoPoint(0, 0));
        ame.setForceMove(true);
        GeoPoint oldLocation = ame.getLocation();

        for (int i = 0; i < 1000; ++i) {
            ame.update();
            assertFalse(ame.getLocalArea().isInside(ame.getLocation()));
            assertNotSame(ame.getLocation(), oldLocation);
            oldLocation = ame.getLocation();
        }
    }

    private void setArtificialMovingEntity(GeoPoint entityLocation, GeoPoint areaLocation){
        Area rectangleArea = new RectangleArea(1, 1, areaLocation);

        ame.setLocation(entityLocation);
        ame.setLocalArea(rectangleArea);

        LinearMovement movement = new LinearMovement();
        movement.setVelocity(10);
        ame.setMovement(movement);
    }
}