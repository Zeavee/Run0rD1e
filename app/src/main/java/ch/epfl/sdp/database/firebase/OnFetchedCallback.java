package ch.epfl.sdp.database.firebase;

public interface OnFetchedCallback<T> {
    void finish(T value);

    void error(Exception ex);
}
