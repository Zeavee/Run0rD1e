package ch.epfl.sdp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MockMapApi implements MapApi {
    @Override
    public GeoPoint getCurrentLocation() {
        return new GeoPoint(40, 50);
    }

    @Override
    public void updatePosition() {
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

    @Override
    public void displayEntity(Displayable displayable) {

    }
}
