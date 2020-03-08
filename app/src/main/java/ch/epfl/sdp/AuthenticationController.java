package ch.epfl.sdp;

public interface AuthenticationController {
    boolean signIn(String email, String password);
    boolean isSignedIn(String email);
    boolean register(String email, String username, String password);
    boolean signOut(String email);
    boolean checkValidity(String email, String password);

    boolean isEmailValid(String email);
    boolean isPasswordValid(String password);
}
