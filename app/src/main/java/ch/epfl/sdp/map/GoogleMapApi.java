package ch.epfl.sdp.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.UiThread;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sdp.geometry.GeoPoint;

public class GoogleMapApi implements MapApi {
    private final GoogleMap mMap;
    private final Map<Displayable, MapDrawing> entityCircles;

    public GoogleMapApi(GoogleMap googleMap) {
        mMap = googleMap;
        entityCircles = new HashMap<>();
    }

    @Override
    public void moveCameraOnLocation(GeoPoint location) {
        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 14));
        }
    }

    @UiThread
    private Bitmap createSmallCircle(int color) {
        Bitmap output = Bitmap.createBitmap(25,
                25, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle(12.5f, 12.5f,
                12.5f, paint);
        return output;
    }

    @UiThread
    @Override
    public void removeMarkers(Displayable displayable) {
        if (entityCircles.containsKey(displayable)) {
            if (entityCircles.get(displayable).hasMarker())
                entityCircles.get(displayable).getMarker().remove();
            if (entityCircles.get(displayable).hasCircle())
                entityCircles.get(displayable).getArea().remove();
            if (entityCircles.get(displayable).hasPolygon())
                entityCircles.get(displayable).getPolygon().remove();
        }
    }

    @UiThread
    @Override
    public void displaySmallIcon(Displayable displayable, String title, int id) {
        displayMapDrawing(displayable, new MapDrawing(mMap.addMarker(new MarkerOptions().position(new LatLng(displayable.getLocation().getLatitude(), displayable.getLocation().getLongitude())).title(title).icon(BitmapDescriptorFactory.fromResource(id)))));
    }

    @UiThread
    @Override
    public void displayMarkerCircle(Displayable displayable, int color, String title, int aoeRadius) {
        if (aoeRadius < 0) {
            return;
        }
        displayMapDrawing(displayable, new MapDrawing(mMap.addMarker(new MarkerOptions().position(new LatLng(displayable.getLocation().getLatitude(), displayable.getLocation().getLongitude())).title(title).icon(BitmapDescriptorFactory.fromBitmap(createSmallCircle(color)))),
                mMap.addCircle(new CircleOptions().center(new LatLng(displayable.getLocation().getLatitude(), displayable.getLocation().getLongitude())).strokeColor(color).fillColor(color - 0x80000000).radius(aoeRadius).strokeWidth(2f))));
    }

    @Override
    public void displayCircle(Displayable displayable, int strokeColor, int radius, int fillColor) {
        displayMapDrawing(displayable, new MapDrawing(mMap.addCircle(new CircleOptions().center(new LatLng(displayable.getLocation().getLatitude(), displayable.getLocation().getLongitude())).strokeColor(strokeColor).fillColor(fillColor).radius(radius).strokeWidth(2f))));
    }

    @Override
    public void displayPolygon(Displayable displayable, List<LatLng> vertices, int strokeColor, int fillColor) {
        displayMapDrawing(displayable, new MapDrawing(mMap.addPolygon(new PolygonOptions().addAll(vertices).strokeColor(strokeColor).fillColor(fillColor).strokeWidth(2f))));
    }

    private void displayMapDrawing(Displayable displayable, MapDrawing mapDrawing) {
        removeMarkers(displayable);
        entityCircles.put(displayable, mapDrawing);
    }
}