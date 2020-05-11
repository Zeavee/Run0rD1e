package ch.epfl.sdp.market;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.util.Pair;

import java.util.HashMap;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.item.Scan;
import ch.epfl.sdp.item.Shield;
import ch.epfl.sdp.item.Shrinker;

/**
 * Models the market of the game.
 * If the user is within 50 meters of the map, this activity is intended.
 * The backend of this activity is Market, which delegates all calls such as buying items and reducing stocks when a user buys
 */

public class MarketActivity extends AppCompatActivity {

    private Market backend;
    private ImageButton aoeImg;
    private ImageButton scanImg;
    private ImageButton shImg;
    private ImageButton healthImg;
    private final static int CARD_HEIGHT = 20;
    private HashMap<View, Pair<Integer, Class<? extends Item>>> viewsSelected = new HashMap<>();
    private Button buy;
    private HashMap<Integer, Integer> itemToViewMap = new HashMap<>();

    /**
     * Initializes the viewSelected map, which maps view to an integer indicating if it's selected as well as
     * a class variable representing the class of the item. Also, synchronizes backend to match frontend
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        aoeImg = findViewById(R.id.shrinkButton);
        scanImg = findViewById(R.id.scanButton);
        shImg = findViewById(R.id.shieldButton);
        healthImg = findViewById(R.id.emsButton);
        buy = findViewById(R.id.BuyButton);

        aoeImg.setOnClickListener(v -> invertCardView(v));
        scanImg.setOnClickListener(v -> invertCardView(v));
        shImg.setOnClickListener(v -> invertCardView(v));
        healthImg.setOnClickListener(v -> invertCardView(v));
        buy.setOnClickListener(v -> checkoutItems());

        initViewsSelected();
        syncMarketBackendToFrontend();
    }

    /**
     * buys the items that were selected by the user.
     * If the user does not have enough money, they will not be bought
     */
    private void checkoutItems() {
        for (View v : viewsSelected.keySet()) {
            if (viewsSelected.get(v).first == 1) {
                backend.buy(viewsSelected.get(v).second, PlayerManager.getInstance().getCurrentUser());
            }
        }
        Toast.makeText(this.getApplicationContext(), "Transaction finished", Toast.LENGTH_LONG).show();
    }

    /**
     * Maps the views to their corresponding item classes as well as if they are selected
     */
    private void initViewsSelected() {
        viewsSelected.put(aoeImg, new Pair<>(0, Shrinker.class));
        viewsSelected.put(scanImg, new Pair<>(0, Scan.class));
        viewsSelected.put(shImg, new Pair<>(0, Shield.class));
        viewsSelected.put(healthImg, new Pair<>(0, Healthpack.class));
    }

    /**
     * synchronizes the market backend to the frontend
     */
    private void syncMarketBackendToFrontend() {
        backend = ((ObjectWrapperForBinder<Market>) getIntent().getExtras().getBinder("object_value")).getData();
        if (backend == null) finish();
        setupItemToViewMap();
        for (Item i : backend.getStock().keySet()) {
            ((TextView) findViewById(itemToViewMap.get(i.getClass().hashCode()))).setText("Cost: " + backend.getStock().get(i).second + "\n" + "Value: " + (((int) (100 * i.getValue())) / 100.0));
        }
    }

    /**
     * sets up the map responsible for showing the prices of items as well their value
     */
    private void setupItemToViewMap() {
        itemToViewMap.put(Healthpack.class.hashCode(), R.id.costEms);
        itemToViewMap.put(Shield.class.hashCode(), R.id.costShield);
        itemToViewMap.put(Scan.class.hashCode(), R.id.costScan);
        itemToViewMap.put(Shrinker.class.hashCode(), R.id.costShrink);

    }

    /**
     * inverts the card's height when the picture of the item is clicked
     */
    private void invertCardView(View v) {
        int originalValue = viewsSelected.get(v).first;
        Class<? extends Item> itemType = viewsSelected.get(v).second;
        viewsSelected.put(v, new Pair<>(1 - originalValue, itemType));
        ((CardView) (v.getParent())).setCardElevation(originalValue * CARD_HEIGHT);
    }

}
