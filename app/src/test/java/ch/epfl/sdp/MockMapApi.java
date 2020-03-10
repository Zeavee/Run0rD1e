package ch.epfl.sdp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.location.Location;

import java.util.List;

public class MockMapApi implements MapApi {
    @Override
    public Location getCurrentLocation() {
        Location l = new Location("FakeProvider");
        l.setTime(10);
        l.setElapsedRealtimeNanos(10);
        l.setAccuracy(10);
        l.setLongitude(40);
        l.setLatitude(50);
        l.setSpeed(10);
        l.setAltitude(1000);
        return l;
    }

    @Override
    public void updatePosition() {
    }

    @Override
    public void displayEnemies(List<Enemy> enemies) {

    }

    @Override
    public void moveCameraOnCurrentLocation() {

    }

    @Override
    public Bitmap createSmallCircle(int color) {
        Bitmap output = Bitmap.createBitmap(25, 25, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle(25 / 2, 25 / 2, 25 / 2, paint);
        return output;
    }
}
