package ch.epfl.sdp.database.firebase.callback;

public interface OnAddUserCallback {
    void finish();

    void error(Exception ex);
}
