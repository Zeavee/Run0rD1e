package ch.epfl.sdp.database.firebase.api;

public interface SoloDatabaseAPI {
    void updateCurrentUserScore(String currentUserEmail, int generalGameScore);
}
