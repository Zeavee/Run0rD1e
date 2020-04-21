package ch.epfl.sdp.database.firebase.callback;

public interface OnValueReadyCallback<T> {
    void finish(T value);

    void error(Exception ex);
}
