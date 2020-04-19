package ch.epfl.sdp.login;

public interface AuthenticationAPI {
    void signIn(String email, String password, OnAuthCallback callback);

    void register(String email, String password, OnAuthCallback callback);

    void signOut();
}
