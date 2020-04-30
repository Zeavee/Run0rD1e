package ch.epfl.sdp.map;

import java.util.Collection;

public interface Renderer {
    void display(Collection<Displayable> displayables);
}
