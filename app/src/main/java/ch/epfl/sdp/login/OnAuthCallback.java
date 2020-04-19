package ch.epfl.sdp.login;

public interface OnAuthCallback {
    void finish();

    void error(Exception ex);
}
