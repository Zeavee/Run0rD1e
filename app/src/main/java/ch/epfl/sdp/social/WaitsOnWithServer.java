package ch.epfl.sdp.social;

import java.util.List;

public interface WaitsOnWithServer<T>  {
    void contentFetchedWithServer(List<T> output, boolean isFromServer, boolean incoming);
}
