package ch.epfl.sdp.database.firebase.utils;

public interface OnValueReadyCallback<T> {
    /**
     * Notify the calling-site the completion of the callee method
     *
     * @param value The return value after executing the callee method
     */
    void finish(T value);
}
