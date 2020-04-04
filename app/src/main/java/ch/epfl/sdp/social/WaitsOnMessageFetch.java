package ch.epfl.sdp.social;

import java.util.List;

public interface WaitsOnMessageFetch {
    void incomingMessageFetchFinished(List<Message> output, boolean isFromServer);
    void outgoingMessageFetchFinished(List<Message> output);
}
