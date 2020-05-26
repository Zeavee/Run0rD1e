package ch.epfl.sdp.game;

/**
 * Manages the main loop.
 */
public class GameThread extends Thread {
    public static final int FPS = 24;
    private boolean running;
    private final Game game;
    private long startTime;
    private long totalTime = 0;
    private int frameCount = 0;
    private boolean exceptionFlag = false;

    /**
     * Creates a thread for the game loop.
     *
     * @param game The current game.
     */
    public GameThread(Game game) {
        this.game = game;
    }


    /**
     * Get the actual state of the loop.
     *
     * @return true if it is running, false otherwise.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Set true to start or continue the loop, false to stop it.
     *
     * @param running defines the state of the loop.
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Main loop that can be parameterized, started and stopped, manages frames and updates.
     */
    @Override
    public void run() {
        // Main loop of the game
        while (running) {
            startTime = System.nanoTime();

            // Does this needs synchronization?
            try {
                game.update();
                game.draw();
            } catch (Exception e) {
                exceptionFlag = true;
                e.printStackTrace();
            }

            // Wait before refreshing the game
            waitBeforeRefresh();

            // Compute FPS for testing
            computeFPS();
        }
    }

    /**
     * Wait some time if the frame is finished before the target time.
     */
    private void waitBeforeRefresh() {
        long timeMillis = (System.nanoTime() - startTime) / 1000000;
        long targetTime = 1000 / FPS;
        long waitTime = targetTime - timeMillis;
        if (waitTime > 0) {
            try {
                sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Utility method to compute the average FPS.
     */
    private void computeFPS() {
        totalTime += System.nanoTime() - startTime;
        frameCount++;

        if (frameCount == FPS) {
            double avgFPS = 10000.0 / ((1.0 * totalTime / frameCount) / 1000000.0);

            // Reset values
            frameCount = 0;
            totalTime = 0;

            // Print avergage FPS
            System.out.println("Average FPS: " + avgFPS);
        }
    }

    /**
     * Returns the exception flag of the game thread
     *
     * @return the exception flag of the game thread
     */
    public boolean getExceptionFlag() {
        return exceptionFlag;
    }
}