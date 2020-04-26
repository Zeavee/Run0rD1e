package ch.epfl.sdp.database.utils;

public interface OnValueReadyCallback<T> {
    /**
     * Notify the calling-site the completion of the callee method
     *
     * @param value The return value after execution of the callee method
     */
    void finish(T value);
}
