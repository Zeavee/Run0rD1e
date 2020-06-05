package ch.epfl.sdp.utils;

import java.util.List;

/**
 * This interface is a callback that waits for some list to be filled and the data can come from the server
 *
 * @param <T> The type of the elements of the list we wait on
 */
public interface WaitsOnWithServer<T> {
    /**
     * The method we need to call when we finished to fill the list
     *
     * @param output       the list we filled
     * @param isFromServer a boolean that will tell if the data come from a remote server or from the local database
     * @param incoming     a boolean that will tell if the data is incoming
     */
    void contentFetchedWithServer(List<T> output, boolean isFromServer, boolean incoming);
}
