package ch.epfl.sdp.database.firebase;

public interface ClientDatabaseAPI extends UserDataController {
    void sendHealthPoints(double healthPoints);
    void sendAoeRadius(double aoeRadius);
}
