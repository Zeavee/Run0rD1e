package ch.epfl.sdp.social;

import java.util.List;

interface WaitsOnMessageFetch {
    void incomingMessageFetchFinished(List<Message> output);
    void outgoingMessageFetchFinished(List<Message> output);
}
