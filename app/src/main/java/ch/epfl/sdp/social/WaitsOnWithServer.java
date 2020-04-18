package ch.epfl.sdp.social;

import java.util.List;

public interface WaitsOnWithServer<T> extends WaitsOn<T> {
    void contentFetchedWithServer(List<T> output, boolean isFromServer);
}
