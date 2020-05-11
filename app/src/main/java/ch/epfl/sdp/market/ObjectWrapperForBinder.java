package ch.epfl.sdp.market;

import android.os.Binder;

public class ObjectWrapperForBinder<T> extends Binder {

    private final T mData;

    public ObjectWrapperForBinder(T data) {
        mData = data;
    }

    public T getData() {
        return mData;
    }
}
