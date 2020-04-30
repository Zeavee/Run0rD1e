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

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.geometry.GeoPoint;

public class GoogleMapApi implements MapApi {
    private GoogleMap mMap;
    private Map<Displayable, MapDrawing> entityCircles;

    public GoogleMapApi(GoogleMap googleMap) {
        mMap = googleMap;
        entityCircles = new HashMap<>();
    }

    @Override
    public void moveCameraOnLocation(GeoPoint location) {
        LatLng currentLocationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocationLatLng, 14));
    }

    @UiThread
    private Bitmap createSmallCircle(int color) {
        Bitmap output = Bitmap.createBitmap(25,
                25, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle(25 / 2, 25 / 2,
                25 / 2, paint);
        return output;
    }

    @UiThread
    @Override
    public void removeMarkers(Displayable displayable) {
            if (entityCircles.containsKey(displayable)) {
                if (entityCircles.get(displayable).hasMarker())
                    entityCircles.get(displayable).getMarker().remove();
                if (entityCircles.get(displayable).hasCircle())
                    entityCircles.get(displayable).getAoe().remove();
            }
    }

    @UiThread
    @Override
    public void displaySmallIcon(Displayable displayable, String title, int id) {
            removeMarkers(displayable);
            LatLng position = new LatLng(displayable.getLocation().getLatitude(), displayable.getLocation().getLongitude());
            entityCircles.put(displayable, new MapDrawing(mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title(title)
                    .icon(BitmapDescriptorFactory.fromResource(id)))));
    }

    @UiThread
    @Override
    public void displayMarkerCircle(Displayable displayable, int color, String title, int aoeRadius) {
        removeMarkers(displayable);
        LatLng position = new LatLng(displayable.getLocation().getLatitude(), displayable.getLocation().getLongitude());
        entityCircles.put(displayable, new MapDrawing(mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(title)
                .icon(BitmapDescriptorFactory.fromBitmap(createSmallCircle(color)))),
                mMap.addCircle(new CircleOptions()
                        .center(position)
                        .strokeColor(color)
                        .fillColor(color-0x80000000)
                        .radius(aoeRadius)
                        .strokeWidth(1f))));
    }
}
