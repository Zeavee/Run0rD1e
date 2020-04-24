package ch.epfl.sdp.login;

public interface OnAuthCallback {
    /**
     * Notify the calling-site the completion of the callee methods
     */
    void finish();

    /**
     * Notify the calling-size the exception of the callee methods
     * @param ex The exception of the callee methods
     */
    void error(Exception ex);
}
