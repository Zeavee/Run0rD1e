package ch.epfl.sdp.database.authentication;

import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;

/**
 * The interface with the method related to the firebase authentication
 */
public interface AuthenticationAPI {
    /**
     * Sign in to the firebase using email and password
     *
     * @param email                The email of the CurrentUser
     * @param password             The password of the CurrentUser
     * @param onValueReadyCallback Callback after signing in to the firebase
     */
    void signIn(String email, String password, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

    /**
     * Register to the firebase with email and password
     *
     * @param email                The unique email of the new User
     * @param password             The password of the new User
     * @param onValueReadyCallback CallBack after registering to the firebase
     */
    void register(String email, String password, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback);

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
