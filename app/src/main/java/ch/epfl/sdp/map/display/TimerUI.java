package ch.epfl.sdp.map.display;

/**
 * This interface should be implemented by activities that want to display a timer
 */
public interface TimerUI {
    /**
     * This methods shows the remaining time on the timer
     *
     * @param timeAsString a string that represents the remaining time
     */
    void displayTime(String timeAsString);
}
