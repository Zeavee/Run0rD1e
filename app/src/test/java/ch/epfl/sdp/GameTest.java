package ch.epfl.sdp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.MapsActivity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GameTest {

    @Before
    public void setup() {
        MockMapApi mockMapApi = new MockMapApi();
        MapsActivity.setMapApi(mockMapApi);
    }

    @Test
    public void update_ShouldUpdateAllUpdatables() {
        // arrange
        Updatable upd1 = mock(Updatable.class);
        Updatable upd2 = mock(Updatable.class);

        // act
        Game game = new Game();
        game.addToUpdateList(upd1);
        game.addToUpdateList(upd2);
        game.update();

        // assert
        verify(upd1).update();
        verify(upd2).update();
    }

    @Test
    public void addToUpdateList_ShouldAddUpdatable()
    {
        // act
        Game game = new Game();
        game.addToUpdateList(mock(Updatable.class));

        // assert
        assertEquals(1, game.getUpdatables().size());
    }

    @Test
    public void removeFromUpdateList_ShouldRemoveUpdatable()
    {
        // arrange
        Updatable mockUpdatable1 = mock(Updatable.class);
        Updatable mockUpdatable2 = mock(Updatable.class);
        Updatable mockNonExistingUpdatable = mock(Updatable.class);

        // act
        Game game = new Game();
        game.addToUpdateList(mockUpdatable1);
        game.addToUpdateList(mockUpdatable2);
        game.removeFromUpdateList(mockUpdatable1);
        game.removeFromUpdateList(mockNonExistingUpdatable);

        // assert
        assertEquals(1, game.getUpdatables().size());
    }

    @Test
    public void addToDisplayList_ShouldAddDisplayable()
    {
        // act
        Game game = new Game();
        game.addToDisplayList(mock(Displayable.class));

        // assert
        assertEquals(1, game.getDisplayables().size());
    }

    @Test
    public void removeFromDisplayList_ShouldRemoveDisplayable()
    {
        // arrange
        Displayable mockDisplayable1 = mock(Displayable.class);
        Displayable mockDisplayable2 = mock(Displayable.class);
        Displayable mockNonExistingDisplayable = mock(Displayable.class);

        // act
        Game game = new Game();
        game.addToDisplayList(mockDisplayable1);
        game.addToDisplayList(mockDisplayable2);
        game.removeFromDisplayList(mockDisplayable1);
        game.removeFromDisplayList(mockNonExistingDisplayable);

        // assert
        assertEquals(1, game.getDisplayables().size());
    }

    @Test
    public void gameThread_runs()
    {
        Game game = new Game();
        game.initGame();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.destroyGame();

        // assert
        Assert.assertFalse(game.gameThread.isRunning());
    }
}