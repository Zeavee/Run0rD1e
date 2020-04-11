package ch.epfl.sdp.artificial_intelligence;

public class UnboundedArea implements Area {
    @Override
    public boolean isInside(GenPoint genPoint) {
        return true;
    }
}
