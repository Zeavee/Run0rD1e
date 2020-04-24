package ch.epfl.sdp.social;

import java.util.List;

/**
 * This interface is a callback that waits for some list to be filled
 * @param <T> The type of the elements of the list we wait on
 */
public interface WaitsOn<T> {
    /**
     * The method we need to call when we finished to fill the list
     * @param list the list we filled
     */
    void contentFetched(List<T> list);
}
