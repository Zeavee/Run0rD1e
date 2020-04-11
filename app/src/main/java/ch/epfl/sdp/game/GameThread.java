package ch.epfl.sdp.game;

/**
 * Manages the main loop
 */
public class GameThread extends Thread{
    public static final int FPS = 30;
    private double avgFPS;
    private boolean running;
    private Game game;

    private long startTime;
    private long timeMillis;
    private long waitTime;
    private long totalTime = 0;
    private int frameCount = 0;
    private long targetTime = 1000 / FPS;

    public GameThread(Game game){
        this.game = game;
    }

    /**
     * Get the actual state of the loop
     * @return true if it is running, false otherwise
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Set true to start or continue the loop, false to stop it.
     * @param running defines the state of the loop
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Main loop that can be parameterized, started and stoped, manages frames and updates
     */
    @Override
    public void run(){
        // Main loop of the game
        while(running){
            startTime = System.nanoTime();

            // Does this needs synchronization?
            try {
                game.update();
                game.draw();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Wait before refreshing the game
            waitBeforeRefresh();

            // Compute FPS for testing
            computeFPS();
        }
    }

    private void waitBeforeRefresh() {
        timeMillis = (System.nanoTime() - startTime) / 1000000;
        waitTime = targetTime - timeMillis;
        if (waitTime > 0) {
            try {
                this.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void computeFPS() {
        totalTime += System.nanoTime() - startTime;
        frameCount++;

        if(frameCount == FPS){
            avgFPS = 1000 / ((totalTime / frameCount) / 1000000);

            // Reset values
            frameCount = 0;
            totalTime = 0;

            // Print avergage FPS
            System.out.println("Average FPS: " + avgFPS);
        }
    }
}
