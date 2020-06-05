package ch.epfl.sdp.database.firebase.api;

public interface Function<T, R>  {
    R methodFromT(T t);
}
