package ch.epfl.sdp.social;

import java.util.List;

public interface WaitOnChatRetrieval {
    void chatFetched(List<Chat> chat);
}
