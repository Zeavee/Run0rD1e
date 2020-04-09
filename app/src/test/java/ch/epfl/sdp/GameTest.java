package ch.epfl.sdp;

import android.app.Activity;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.GeoPoint;
import ch.epfl.sdp.map.MapApi;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GameTest {

    @Test
    public void update_ShouldUpdateAllUpdatables() {
        // arrange
        Updatable upd1 = mock(Updatable.class);
        Updatable upd2 = mock(Updatable.class);

        // act
        Game game = new Game(null, null);
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
        Game game = new Game(null, null);
        game.addToUpdateList(null);

        // assert
        assertEquals(0, game.getUpdatables().size());
    }

    @Test
    public void addToUpdateList_ShouldAddUpdatable()
    {
        // act
        Game game = new Game(null, null);
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
        Game game = new Game(null, null);
        game.addToUpdateList(mockUpdatable1);
        game.addToUpdateList(mockUpdatable2);
        game.removeFromUpdateList(mockUpdatable1);
        game.removeFromUpdateList(mockNonExistingUpdatable);

        // assert
        assertEquals(1, game.getUpdatables().size());
    }

    @Test
    public void itemShouldNotBeInListAfterTaken() throws InterruptedException {
        // arrange
        Item healthpack = new Healthpack(new GeoPoint(45, 45), false, 10);
        MapApi mockMapApi = new MockMapApi();
        Activity activity = mock(Activity.class);
        new PlayerManager();
        doAnswer((i) -> null).when(activity).runOnUiThread(any(Runnable.class));
        mockMapApi.initializeApi(null, activity);

        // act

        Game game = new Game(mockMapApi, null);
        game.addToDisplayList(healthpack);
        game.addToUpdateList(healthpack);
        game.initGame();

        // assert
        assertEquals(true, healthpack.isActive());
        Player player = new Player(45, 45, 50, "TestPlayer", "Test@Test.com");
        PlayerManager.addPlayer(player);
        Thread.sleep(1000);
        assertEquals(false, healthpack.isActive());
    }

    @Test
    public void addToDisplayList_ShouldIgnoreNull()
    {
        // act
        Game game = new Game(null, null);
        game.addToDisplayList(null);

        // assert
        assertEquals(0, game.getDisplayables().size());
    }

    @Test
    public void addToDisplayList_ShouldAddDisplayable()
    {
        // act
        Game game = new Game(null, null);
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
        Game game = new Game(null, null);
        game.addToDisplayList(mockDisplayable1);
        game.addToDisplayList(mockDisplayable2);
        game.removeFromDisplayList(mockDisplayable1);
        game.removeFromDisplayList(mockNonExistingDisplayable);

        // assert
        assertEquals(1, game.getDisplayables().size());
    }

    @Test
    public void gameThread_starts()
    {
        Game game = new Game(null, new MockInitializeGame());
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
