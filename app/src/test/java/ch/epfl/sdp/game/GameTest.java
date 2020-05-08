package ch.epfl.sdp.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.MockMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GameTest {

    @Before
    public void setup() {
        MockMap mockMap = new MockMap();
        Game.getInstance().setMapApi(mockMap);
        Game.getInstance().setRenderer(mockMap);
        Game.getInstance().clearGame();
    }

    @Before
    public void teardown(){
        Game.getInstance().destroyGame();
        Game.getInstance().clearGame();
    }


    @Test
    public void firstConstructorTest() {
        Game g = Game.getInstance();
        assertNotNull(g);
    }

    @Test
    public void update_ShouldUpdateAllUpdatables() {
        // arrange
        Updatable upd1 = mock(Updatable.class);
        Updatable upd2 = mock(Updatable.class);

        // act
        Game.getInstance().addToUpdateList(upd1);
        Game.getInstance().addToUpdateList(upd2);
        Game.getInstance().update();

        // assert
        verify(upd1).update();
        verify(upd2).update();
    }

    @Test
    public void addToUpdateList_ShouldAddUpdatable() {
        // act
        Game.getInstance().addToUpdateList(mock(Updatable.class));

        // assert
        assertEquals(1, Game.getInstance().getUpdatables().size());
    }

    @Test
    public void removeFromUpdateList_ShouldRemoveUpdatable() {
        // arrange
        Updatable mockUpdatable1 = mock(Updatable.class);
        Updatable mockUpdatable2 = mock(Updatable.class);
        Updatable mockNonExistingUpdatable = mock(Updatable.class);

        // act
        Game.getInstance().addToUpdateList(mockUpdatable1);
        Game.getInstance().addToUpdateList(mockUpdatable2);
        Game.getInstance().removeFromUpdateList(mockUpdatable1);
        Game.getInstance().removeFromUpdateList(mockNonExistingUpdatable);

        // assert
        assertEquals(1, Game.getInstance().getUpdatables().size());
    }

    @Test
    public void addToDisplayList_ShouldAddDisplayable() {
        // act
        Game.getInstance().addToDisplayList(mock(Displayable.class));

        // assert
        assertEquals(1, Game.getInstance().getDisplayables().size());
    }

    @Test
    public void removeFromDisplayList_ShouldRemoveDisplayable() {
        // arrange
        Displayable mockDisplayable1 = mock(Displayable.class);
        Displayable mockDisplayable2 = mock(Displayable.class);
        Displayable mockNonExistingDisplayable = mock(Displayable.class);

        // act
        Game.getInstance().addToDisplayList(mockDisplayable1);
        Game.getInstance().addToDisplayList(mockDisplayable2);
        Game.getInstance().removeFromDisplayList(mockDisplayable1);
        Game.getInstance().removeFromDisplayList(mockNonExistingDisplayable);

        // assert
        assertEquals(1, Game.getInstance().getDisplayables().size());
    }

    @Test
    public void gameThread_runs() {
        Game.getInstance().initGame();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Game.getInstance().destroyGame();

        // assert
        Assert.assertFalse(Game.getInstance().isRunning());
    }
}