package ch.epfl.sdp.database.firebase.api;

public interface OnValueReadyCallback<T> {
    void finish(T value);
}
