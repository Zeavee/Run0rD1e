package ch.epfl.sdp;

public interface AuthenticationController {
    int signIn(String email, String password);
    boolean isSignedIn(String email);
    boolean register(String email, String username, String password, String passwordConf);
    boolean signOut(String email);
    int checkValidity(String email, String password, String passwordConf);
}
