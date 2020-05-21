package ch.epfl.sdp.game;

public interface StartGameController {
    /**
     *  Implemented by Solo, Server and Client and used in GoogleLocationFinder,
     *  After fetching the location of the CurrentUser (instead of the default 0, 0) from device for the first time we start the whole game
     *  For Solo: It means populate the Enemies, itemboxes locally and start the gameThread
     *  For Server: It means populate the Enemies and itemboxes on cloud firebase and start the gameThread
     *  For Client: It means fetch the Enemies and itemboxes from cloud firebase and start the gameThread.
     */
    void start();
}
