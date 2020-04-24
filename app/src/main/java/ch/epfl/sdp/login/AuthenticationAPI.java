package ch.epfl.sdp.login;

/**
 * The interface with the method related to the firebase authentication
 */
public interface AuthenticationAPI {
    /**
     * Sign in to the firebase using email and password
     *
     * @param email The email of the CurrentUser
     * @param password The password of the CurrentUser
     * @param callback Callback after complete signIn to the firebase
     */
    void signIn(String email, String password, OnAuthCallback callback);

    /**
     * Register to the firebase using email and password
     *
     * @param email The unique email of the new User
     * @param password The password of the new User
     * @param callback CallBack after complete registering to the firebase
     */
    void register(String email, String password, OnAuthCallback callback);

    /**
     * Get the email of the CurrentUser
     *
     * @return The email of the CurrentUser
     */
    String getCurrentUserEmail();

    /**
     * Sign out from the firebase for CurrentUser
     */
    void signOut();
}
