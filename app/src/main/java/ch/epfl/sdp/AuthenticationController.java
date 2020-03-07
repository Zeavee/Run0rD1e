package ch.epfl.sdp;

public interface AuthenticationController {
    boolean signIn(String id, String password);
    boolean register(String id, String username, String password);
    boolean signOut();
    boolean checkValidity(String id, String password);
    Player  signedInPlayer();
}
