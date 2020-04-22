package ch.epfl.sdp.database.firebase.utils;

public class CustumResult<T>{

    private T result;
    private boolean isSuccessful;
    private Exception exception;

    public CustumResult(T result, boolean isSuccessful, Exception exception) {
        this.result = result;
        this.isSuccessful = isSuccessful;
        this.exception = exception;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
