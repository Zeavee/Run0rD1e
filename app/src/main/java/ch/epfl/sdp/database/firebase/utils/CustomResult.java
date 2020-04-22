package ch.epfl.sdp.database.firebase.utils;

/**
 * The class used to store the information after the executing of the callee method
 * @param <T> The type parameter of the result
 */
public class CustomResult<T>{

    private T result;
    private boolean isSuccessful;
    private Exception exception;

    /**
     * Construct a CustomResult instance
     * @param result The result after executing of the callee method
     * @param isSuccessful The boolean value indicate whether the executing of the callee method is succeeded or not
     * @param exception The possible exception after executing of the callee method
     */
    public CustomResult(T result, boolean isSuccessful, Exception exception) {
        this.result = result;
        this.isSuccessful = isSuccessful;
        this.exception = exception;
    }

    /**
     * Get the result after executing of the callee method
     *
     * @return The result after executing of the callee method
     */
    public T getResult() {
        return result;
    }

    /**
     * Set the result after executing of the callee method
     *
     * @param result The result after executing of the callee method
     */
    public void setResult(T result) {
        this.result = result;
    }

    /**
     * Check whether the executing of the callee method is succeeded or not
     *
     * @return A boolean value indicate whether the executing of the callee method is succeeded or not
     */
    public boolean isSuccessful() {
        return isSuccessful;
    }

    /**
     * Set whether the executing of the callee method is succeeded or not
     *
     * @param successful A boolean value indicate whether the executing of the callee method is succeeded or not
     */
    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    /**
     * Get the possible exception after executing of the callee method
     *
     * @return The possible exception after executing of the callee method
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Set the possible exception after executing of the callee method
     * @param exception The possible exception after executing of the callee method
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }
}
