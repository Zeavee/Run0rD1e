package ch.epfl.sdp.database;

import ch.epfl.sdp.item.ItemBox;

public interface ClientDatabaseAPI extends UserDataController {
    void sendHealthPoints(double healthPoints);
    void sendAoeRadius(double aoeRadius);
}
