package ch.epfl.sdp.item;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.map.GeoPoint;

public abstract class TimedItem extends Item implements Updatable {
    protected int counter;
    private final int FPS = 30;

    public TimedItem(GeoPoint location, String name, boolean isTaken, String description, int countTime) {
        super(location, name, isTaken, description);
        counter = countTime*FPS;
    }

    public int getRemainingTime(){
        return counter/FPS;
    }

    @Override
    public void update() {
        if(counter > 0){
            --counter;
        }else{
            Game.removeFromUpdateList(this);
        }
    }
}
