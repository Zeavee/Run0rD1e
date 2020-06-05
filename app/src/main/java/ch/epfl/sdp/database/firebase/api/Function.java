package ch.epfl.sdp.database.firebase.api;

/**
 * An interface that represents a function.
 * @param <T> Input of the function.
 * @param <R> Output of the function.
 */
public interface Function<T, R>  {
    /**
     * This is the function represented by the interface
     * @param t Input of the function.
     * @return Output of the function.
     */
    R methodFromT(T t);
}
