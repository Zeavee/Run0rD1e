package ch.epfl.sdp.database.firebase.api;

public interface ClientDatabaseAPI extends CommonDatabaseAPI {
    void sendHealthPoints(double healthPoints);
    void clearItemBoxes();
    void sendAoeRadius(double aoeRadius);
}
