package ch.epfl.sdp.utils;

/**
 * A callback
 * @param <T>
 */
public interface OnValueReadyCallback<T> {
    /**
     * Notify the calling-site the completion of the callee method
     *
     * @param value The return value after execution of the callee method
     */
    void finish(T value);
}
