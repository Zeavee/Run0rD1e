package ch.epfl.sdp.game;

/**
 * Manages the main loop
 */
public class GameThread extends Thread{
    private int FPS = 30;
    private double avgFPS;
    private boolean running;
    private Game game;

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
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / FPS;

        // Main loop of the game
        while(running){
            startTime = System.nanoTime();

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            // TODO syschronization
            game.update();
            game.draw();

            // Wait before refreshing the game
            try {
                this.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Compute FPS for testing
            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if(frameCount == FPS){
                avgFPS = 1000 / ((totalTime / frameCount)) / 1000000;

                // Reset values
                frameCount = 0;
                totalTime = 0;

                // Print avergage FPS
                System.out.println(avgFPS);
            }
        }
    }
}
