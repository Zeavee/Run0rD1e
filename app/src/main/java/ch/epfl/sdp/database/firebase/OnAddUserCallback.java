package ch.epfl.sdp.database.firebase;

public interface OnAddUserCallback {
    void finish();

    void error(Exception ex);
}
