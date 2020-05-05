package ch.epfl.sdp.map;

import java.util.Collection;

/**
 * An interface that permits to render object on the map
 */
public interface Renderer {
    /**
     * A method that display a list of object on the map
     *
     * @param displayables the list we want to display
     */
    void display(Collection<Displayable> displayables);

    /**
     * A method that undisplay an item from the map
     *
     * @param displayable
     */
    void unDisplay(Displayable displayable);
}
