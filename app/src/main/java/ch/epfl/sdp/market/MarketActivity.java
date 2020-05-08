package ch.epfl.sdp.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.util.Pair;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.item.Market;
import ch.epfl.sdp.item.Scan;
import ch.epfl.sdp.item.Shield;
import ch.epfl.sdp.item.Shrinker;

public class MarketActivity extends AppCompatActivity {

    private Market backend;
    private ImageView aoeImg;
    private ImageView scanImg;
    private ImageView shImg;
    private ImageView healthImg;
    private HashMap<Integer, Pair<Integer, Integer>> itemToViewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        aoeImg = findViewById(R.id.aeoImg);
        scanImg = findViewById(R.id.scanImg);
        shImg = findViewById(R.id.shieldImg);
        healthImg = findViewById(R.id.emsImg);

        aoeImg.setOnClickListener(v -> sinkCardView(v));
        scanImg.setOnClickListener(v -> sinkCardView(v));
        shImg.setOnClickListener(v -> sinkCardView(v));
        healthImg.setOnClickListener(v -> sinkCardView(v));

        syncMarketBackendToFrontend();
    }

    private void syncMarketBackendToFrontend() {
        backend = (Market) (getIntent().getParcelableExtra("backend"));
        if (backend == null) return;
        setupItemToViewMap();
        for (Item i: backend.getStock().keySet()){
            ((TextView)findViewById(itemToViewMap.get(i.getClass().hashCode()).first)).setText("Cost: " + backend.getStock().get(i).second);
            ((TextView)findViewById(itemToViewMap.get(i.getClass().hashCode()).second)).setText("Value: " + i.getValue());
        }
    }

    private void setupItemToViewMap() {
        itemToViewMap = new HashMap<>();
        itemToViewMap.put(Healthpack.class.hashCode(), new Pair<>(R.id.costEms, R.id.valEms));
        itemToViewMap.put(Shield.class.hashCode(), new Pair<>(R.id.costShield, R.id.valShield));
        itemToViewMap.put(Scan.class.hashCode(), new Pair<>(R.id.costScan, R.id.valScan));
        itemToViewMap.put(Shrinker.class.hashCode(), new Pair<>(R.id.costShrinker, R.id.valShrinker));

    }

    private void sinkCardView(View v) {
        ((CardView)(v.getParent().getParent())).setCardElevation(0);
    }
}
