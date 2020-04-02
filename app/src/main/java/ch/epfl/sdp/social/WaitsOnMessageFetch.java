package ch.epfl.sdp.social;

import java.util.List;

interface WaitsOnMessageFetch {
    void messageFetchFinished(List<Message> output);
}
