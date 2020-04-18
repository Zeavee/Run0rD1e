package ch.epfl.sdp.social;

import java.util.List;

public interface WaitsOn<T> {
    void contentFetched(List<T> list);
}
