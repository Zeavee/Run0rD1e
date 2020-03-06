package ch.epfl.sdp;

public interface AuthenticationController {
    public boolean signIn(String id, String password);
    public boolean register(String id, String password);
    public boolean signOut();
}
