package ch.epfl.sdp.database.firebase.api;

/**
 * An interface that represents a function.
 * @param <T> Input of the function.
 * @param <R> Output of the function.
 */
public interface Function<T, R>  {
    R methodFromT(T t);
}
