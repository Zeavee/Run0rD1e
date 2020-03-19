package ch.epfl.sdp;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.game.GameThread;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GameTest {
    @Test
    public void update_ShouldUpdateAllUpdatables()
    {
        // arrange
        Updatable upd1 = mock(Updatable.class);
        Updatable upd2 = mock(Updatable.class);

        // act
        Game game = new Game(null);
        game.addToUpdateList(upd1);
        game.addToUpdateList(upd2);
        game.update();

        // assert
        verify(upd1).update();
        verify(upd2).update();
    }

    @Test
    public void addToUpdateList_ShouldIgnoreNull()
    {
        // act
        Game game = new Game(null);
        game.addToUpdateList(null);

        // assert
        Assert.assertEquals(0, game.getUpdatables().size());
    }

    @Test
    public void addToUpdateList_ShouldAddUpdatable()
    {
        // act
        Game game = new Game(null);
        game.addToUpdateList(mock(Updatable.class));

        // assert
        Assert.assertEquals(1, game.getUpdatables().size());
    }

    @Test
    public void removeFromUpdateList_ShouldRemoveUpdatable()
    {
        // arrange
        Updatable mockUpdatable1 = mock(Updatable.class);
        Updatable mockUpdatable2 = mock(Updatable.class);
        Updatable mockNonExistingUpdatable = mock(Updatable.class);

        // act
        Game game = new Game(null);
        game.addToUpdateList(mockUpdatable1);
        game.addToUpdateList(mockUpdatable2);
        game.removeFromUpdateList(mockUpdatable1);
        game.removeFromUpdateList(mockNonExistingUpdatable);

        // assert
        Assert.assertEquals(1, game.getUpdatables().size());
    }

    @Test
    public void draw_shouldDisplayAllDisplayables() {
        // arrange
        Displayable mockDisplayable1 = mock(Displayable.class);
        Displayable mockDisplayable2 = mock(Displayable.class);
        MapApi mockMapApi = mock(MapApi.class);

        // act
        Game game = new Game(mockMapApi);
        game.addToDisplayList(mockDisplayable1);
        game.addToDisplayList(mockDisplayable2);
        game.draw();

        // assert
        verify(mockMapApi, Mockito.times(2)).displayEntity(any(Displayable.class));
    }

    @Test
    public void addToDisplayList_ShouldIgnoreNull()
    {
        // act
        Game game = new Game(null);
        game.addToDisplayList(null);

        // assert
        Assert.assertEquals(0, game.getDisplayables().size());
    }

    @Test
    public void addToDisplayList_ShouldAddDisplayable()
    {
        // act
        Game game = new Game(null);
        game.addToDisplayList(mock(Displayable.class));

        // assert
        Assert.assertEquals(1, game.getDisplayables().size());
    }

    @Test
    public void removeFromDisplayList_ShouldRemoveDisplayable()
    {
        // arrange
        Displayable mockDisplayable1 = mock(Displayable.class);
        Displayable mockDisplayable2 = mock(Displayable.class);
        Displayable mockNonExistingDisplayable = mock(Displayable.class);

        // act
        Game game = new Game(null);
        game.addToDisplayList(mockDisplayable1);
        game.addToDisplayList(mockDisplayable2);
        game.removeFromDisplayList(mockDisplayable1);
        game.removeFromDisplayList(mockNonExistingDisplayable);

        // assert
        Assert.assertEquals(1, game.getDisplayables().size());
    }

    @Test
    public void fakeTest()
    {
        Game mockGame = mock(Game.class);
        GameThread thread = new GameThread(mockGame);
        thread.setRunning(true);
        thread.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.setRunning(false);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
