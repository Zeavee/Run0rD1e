package ch.epfl.sdp.item;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.map.GeoPoint;

public abstract class TimedItem extends Item implements Updatable {
    protected int counter;
    private final int FPS = 30;

    public TimedItem(String name, String description, int countTime) {
        super(name, description);
        counter = countTime*FPS;
    }

    public void use(){
        Game.addToUpdateList(this);
    }

    public abstract void stopUsing();

    public int getRemainingTime(){
        return counter/FPS;
    }

    @Override
    public void update() {
        if(counter > 0){
            --counter;
        }else{
            stopUsing();
            Game.removeFromUpdateList(this);
        }
    }
}
