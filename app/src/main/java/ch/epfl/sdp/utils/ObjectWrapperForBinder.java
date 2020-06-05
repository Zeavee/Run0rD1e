package ch.epfl.sdp.utils;

import android.os.Binder;

/**
 * This class is used to pass an object between activities
 * This is the reason it extends Binder
 *
 * @param <T> the type of the object passed
 */
public class ObjectWrapperForBinder<T> extends Binder {

    private final T mData;

    /**
     * The constructor for the wrapper
     *
     * @param data the data we want to pass to another activity
     */
    public ObjectWrapperForBinder(T data) {
        mData = data;
    }

    /**
     * This method gets the data we passed
     *
     * @return the data we passed
     */
    public T getData() {
        return mData;
    }
}
