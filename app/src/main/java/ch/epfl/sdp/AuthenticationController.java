package ch.epfl.sdp;

public interface AuthenticationController {
    boolean signIn(String email, String password);
    boolean isSignedIn();
    boolean register(String email, String username, String password);
    boolean signOut();
    boolean checkValidity(String email, String password);
}
