package ch.epfl.sdp;

import org.junit.Test;

import java.util.ArrayList;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.geometry.GeoPoint;
import ch.epfl.sdp.map.Displayable;
import ch.epfl.sdp.map.MapApi;

public class MockMapApi implements MapApi {
    // Used for tests
    private ArrayList<Displayable> displayables = new ArrayList<>();

    public ArrayList<Displayable> getDisplayables() {
        return displayables;
    }

    @Override
    public void moveCameraOnLocation(GeoPoint location) {

    }

    @Override
    public void displaySmallIcon(Displayable displayable, String title, int id) {
        displayables.add(displayable);
    }

    @Override
    public void displayMarkerCircle(Displayable displayable, int yellow, String other_player, int i) {
        displayables.add(displayable);
    }

    @Override
    public void removeMarkers(Displayable displayable) {
        displayables.remove(displayable);
    }

    @Test
    public void unDisplayEntity() {
        Player player1 = new Player(6.149290, 46.212470, 50,
                "Skyris", "test@email.com"); //player position is in Geneva
        this.removeMarkers(player1);
    }
}
