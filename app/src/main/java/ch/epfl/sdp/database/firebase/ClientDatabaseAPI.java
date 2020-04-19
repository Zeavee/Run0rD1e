package ch.epfl.sdp.database.firebase;

public interface ClientDatabaseAPI extends CommonDatabaseAPI {
    void sendHealthPoints(double healthPoints);
    void sendAoeRadius(double aoeRadius);
}
