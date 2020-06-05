package ch.epfl.sdp.geometry;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import ch.epfl.sdp.map.TimerUI;

/**
 * A class that shrinks the game area over time.
 */
public class AreaShrinker {
    private Area gameArea;
    private final long[] time = {0};
    private long finalTime;
    private TimerUI timerUI;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final long timeBeforeShrinking;
    private final long shrinkingDuration;
    private final double shrinkFactor;
    private final long tick = 500;
    private boolean isStarted;
    private final Timer timer = new Timer();
    private ScheduledFuture<?> update1;
    private ScheduledFuture<?> update2;

    /**
     * Constructor for the area shrinker
     *
     * @param timeBeforeShrinking the time between two shrinks
     * @param shrinkingDuration   the duration of the shrinking
     * @param shrinkFactor        this factor tells the size of the new area
     */
    public AreaShrinker(long timeBeforeShrinking, long shrinkingDuration, double shrinkFactor) {
        this.timeBeforeShrinking = timeBeforeShrinking;
        this.shrinkingDuration = shrinkingDuration;
        this.shrinkFactor = shrinkFactor;
    }

    /**
     * The area starts shrinking, and stops after the shrinking duration.
     */
    private void startShrink() {
        isStarted = true;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update1 = runTimer(timeBeforeShrinking, () -> {
                    time[0] += tick;
                    gameArea.setRemainingTimeString(getRemainingTimeAsString());
                    timerUI.displayTime(getRemainingTimeAsString());
                });

                gameArea.shrink(shrinkFactor);
                gameArea.setFinalTime(shrinkingDuration);
                update2 = runTimer(shrinkingDuration, () -> {
                    time[0] += tick;
                    gameArea.setTime(time[0]);
                    gameArea.setRemainingTimeString(getRemainingTimeAsString());
                    timerUI.displayTime(getRemainingTimeAsString());
                });
                gameArea.finishShrink();
            }
        }, 0, 1);
    }

    /**
     * Configure a specific timer for the area shrinker.
     * @param finalTime The duration of the timer.
     * @param runnable The action to execute when timer is on.
     */
    private ScheduledFuture<?> runTimer(long finalTime, Runnable runnable) {
        time[0] = 0;
        this.finalTime = finalTime;
        ScheduledFuture<?> update = scheduler.scheduleWithFixedDelay(runnable, 0, 500, TimeUnit.MILLISECONDS);
        scheduler.schedule(() -> {
            update.cancel(true);
        }, finalTime, TimeUnit.MILLISECONDS);
        try {
            scheduler.awaitTermination(finalTime, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return update;
    }

    /**
     * Get the remaining time as a string.
     * @return The remaining time as a string.
     */
    private String getRemainingTimeAsString() {
        double remainingTime = finalTime - time[0];
        int minutes = (int) remainingTime / 60000;
        int seconds = ((int) remainingTime % 60000) / 1000;
        return minutes + ":" + seconds;
    }

    /**
     * This sets the textView on which we want to display the timer and the activity on which is the timer
     *
     * @param timerUI this will display the remaining time before start or end of the shrinking
     */
    public void setTimerUI(TimerUI timerUI) {
        this.timerUI = timerUI;
        startIfReady();
    }

    /**
     * This sets the game area we want to shrink over time
     *
     * @param gameArea the game area we will shrink
     */
    public void setGameArea(Area gameArea) {
        this.gameArea = gameArea;
        startIfReady();
    }

    /**
     * Start shrinking if everything is ok.
     */
    private void startIfReady() {
        if (gameArea != null && timerUI != null && !isStarted) {
            startShrink();
        }
    }

    /**
     * This method permits the client to show the timer
     */
    public void showRemainingTime(String remainingTime) {
        timerUI.displayTime(remainingTime);
    }

    /**
     * This method clears the timers of the area shrinker
     */
    public void clear() {
        timer.cancel();
        if (update1 != null)
            update1.cancel(true);
        if (update2 != null)
            update2.cancel(true);
    }
}
