package ch.epfl.sdp.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.util.Pair;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.R;
import ch.epfl.sdp.dependencies.MyApplication;
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
    private HashMap<View, Pair<Integer, Class<? extends Item> >> viewsSelected = new HashMap<>();
    private Button buy;
    private HashMap<Integer, Pair<Integer, Integer>> itemToViewMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        ((MyApplication)getApplication()).appContainer.marketActivity = this;

        aoeImg = findViewById(R.id.aeoImg);
        scanImg = findViewById(R.id.scanImg);
        shImg = findViewById(R.id.shieldImg);
        healthImg = findViewById(R.id.emsImg);
        buy = findViewById(R.id.buyButton);

        aoeImg.setOnClickListener(v -> invertCardView(v));
        scanImg.setOnClickListener(v -> invertCardView(v));
        shImg.setOnClickListener(v -> invertCardView(v));
        healthImg.setOnClickListener(v -> invertCardView(v));
        buy.setOnClickListener(v -> checkoutItems());

        initViewsSelected();
        syncMarketBackendToFrontend();
    }

    private void checkoutItems() {
        for (View v: viewsSelected.keySet()){
            if (viewsSelected.get(v).first == 1) {
                backend.buy(viewsSelected.get(v).second, PlayerManager.getCurrentUser());
            }
        }
        Toast.makeText(this.getApplicationContext(), "Transaction finished", Toast.LENGTH_LONG).show();
    }

    private void initViewsSelected() {
        viewsSelected.put(aoeImg, new Pair<Integer, Class<? extends Item>>(0, Shrinker.class));
        viewsSelected.put(scanImg, new Pair<Integer, Class<? extends Item>>(0, Scan.class));
        viewsSelected.put(shImg, new Pair<Integer, Class<? extends Item>>(0, Shield.class));
        viewsSelected.put(healthImg, new Pair<Integer, Class<? extends Item>>(0, Healthpack.class));
    }

    private void syncMarketBackendToFrontend() {
        backend = ((MyApplication)getApplication()).appContainer.marketBackend;
        if (backend == null) return;
        setupItemToViewMap();
        for (Item i: backend.getStock().keySet()){
            ((TextView)findViewById(itemToViewMap.get(i.getClass().hashCode()).first)).setText("Cost: " + backend.getStock().get(i).second);
            ((TextView)findViewById(itemToViewMap.get(i.getClass().hashCode()).second)).setText("Value: " + ((int)(100*i.getValue()))/100.0);
        }
    }

    private void setupItemToViewMap() {
        itemToViewMap.put(Healthpack.class.hashCode(), new Pair<>(R.id.costEms, R.id.valEms));
        itemToViewMap.put(Shield.class.hashCode(), new Pair<>(R.id.costShield, R.id.valShield));
        itemToViewMap.put(Scan.class.hashCode(), new Pair<>(R.id.costScan, R.id.valScan));
        itemToViewMap.put(Shrinker.class.hashCode(), new Pair<>(R.id.costShrinker, R.id.valShrinker));

    }

    private void invertCardView(View v) {
        int originalValue = viewsSelected.get(v).first;
        Class<? extends Item> itemType = viewsSelected.get(v).second;
        viewsSelected.put(v, new Pair<>(1- originalValue,itemType));
        ((CardView)(v.getParent().getParent())).setCardElevation(originalValue*20);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MyApplication)getApplication()).appContainer.marketActivity = null;
    }

    public Market getBackend() {
        return backend;
    }
}
