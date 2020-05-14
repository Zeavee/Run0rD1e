package ch.epfl.sdp.geometry;

import android.app.Activity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * A class that shrinks the game area over time
 */
public class AreaShrinker {
    private Area gameArea;
    private final double[] time = {0};
    private double finalTime;
    private TextView timer;
    private Activity context;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private void startShrink() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runTimer(10000, () -> {
                            time[0] += 500;
                            context.runOnUiThread(() -> timer.setText(getRemainingTimeAsString()));
                        });

                gameArea.shrink(0.75);
                gameArea.setFinalTime(20000);
                runTimer(20000, () -> {
                    time[0] += 500;
                    gameArea.setTime(time[0]);
                    context.runOnUiThread(() -> timer.setText(getRemainingTimeAsString()));
                });
                gameArea.finishShrink();
            }
        }, 0, 10000);
    }

    private void runTimer(double finalTime, Runnable runnable) {
        time[0] = 0;
        this.finalTime = finalTime;
        ScheduledFuture<?> update = scheduler.scheduleWithFixedDelay(runnable, 0, 500, TimeUnit.MILLISECONDS);
        scheduler.schedule(() -> {
            update.cancel(true);
        }, (long) finalTime, TimeUnit.MILLISECONDS);
        try {
            scheduler.awaitTermination((long)finalTime, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRemainingTimeAsString() {
        double remainingTime = finalTime - time[0];
        int minutes = (int) remainingTime / 60000;
        int seconds = ((int) remainingTime % 60000) / 1000;
        return minutes + " : " + seconds;
    }

    /**
     * This sets the textView on which we want to display the timer and the activity on which is the timer
     *
     * @param timerShrinking the TextView which will be the timer
     * @param activity       the activity on which the TextView is
     */
    public void setTextViewAndActivity(TextView timerShrinking, Activity activity) {
        timer = timerShrinking;
        context = activity;
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

    private void startIfReady() {
        if (gameArea != null && timer != null) {
            startShrink();
        }
    }
}
