package ch.epfl.sdp;

import android.content.res.AssetManager;
import android.net.Uri;

import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class CustomTileProvider implements TileProvider {
    private static final int TILE_WIDTH = 256;
    private static final int TILE_HEIGHT = 256;
    private static final int BUFFER_SIZE = 16 * 1024;
    private static final int MAX_HORIZONTAL_TILE_IDX = 21;
    private static final int MAX_VERTICAL_TILE_IDX = 6;

    private AssetManager mAssets;

    public CustomTileProvider(AssetManager assets) {
        mAssets = assets;
    }

    @Override
    public Tile getTile(int x, int y, int zoom) {
        byte[] image = readTileImage(x, y, zoom);
        return image == null ? null : new Tile(TILE_WIDTH, TILE_HEIGHT, image);
    }

    // zoom unused for now
    private byte[] readTileImage(int x, int y, int zoom) {
        if (x > MAX_HORIZONTAL_TILE_IDX || y > MAX_VERTICAL_TILE_IDX) return null;
        InputStream in = null;
        ByteArrayOutputStream buffer = null;
        try {
            in = mAssets.open(String.format("tile_%d_%d.jpg", x,y));
            buffer = new ByteArrayOutputStream();
            return writeTilesIntoArray(buffer, in);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in!= null ) {
                try { in.close(); }
                catch (Exception ignored) { }
            }
            if (buffer != null){
                try  { buffer.close(); }
                catch (Exception ignored) { }
            }
        }
    }

    private byte[] writeTilesIntoArray(ByteArrayOutputStream buffer, InputStream in) throws IOException
    {
        int nRead;
        byte[] data = new byte[BUFFER_SIZE];
        while ((nRead = in.read(data, 0, BUFFER_SIZE)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

}