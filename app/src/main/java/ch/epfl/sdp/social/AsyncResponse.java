package ch.epfl.sdp.social;

import java.util.List;

interface AsyncResponse {
    void messageFetchFinished(List<String> output);
}
