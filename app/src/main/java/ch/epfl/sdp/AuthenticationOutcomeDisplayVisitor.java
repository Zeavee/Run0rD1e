package ch.epfl.sdp;

/**
 * This interface is meant to separate the display of the authentication outcome, whether it be through a toast, notification, etc.
 * The outcome is either a success or a failure
 */
public interface AuthenticationOutcomeDisplayVisitor {

    // Handles the on-screen confirmation of the user's successful authentication
    void onSuccessfulAuthentication();

    // Handles the on-screen confirmation of the user's failed authentication
    void onFailedAuthentication();
}
