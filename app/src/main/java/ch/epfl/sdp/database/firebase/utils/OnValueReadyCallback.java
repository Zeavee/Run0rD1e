package ch.epfl.sdp.database.firebase.utils;

public interface OnValueReadyCallback<T> {
    void finish(T value);
}
