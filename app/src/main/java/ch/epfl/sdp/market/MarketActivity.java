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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.item.Phantom;
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
    private ImageButton phantomImg;
    private ImageButton shImg;
    private final static int CARD_HEIGHT = 30;
    private ImageButton healthImg;
    private TextView money;
    private final HashMap<View, Pair<Integer, Class<? extends Item>>> viewsSelected = new HashMap<>();
    private final HashMap<Integer, Integer> itemToViewMap = new HashMap<>();

    /**
     * Initializes the viewSelected map, which maps view to an integer indicating if it's selected as well as
     * a class variable representing the class of the item. Also, synchronizes backend to match frontend
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        aoeImg = findViewById(R.id.shrinkButton);
        phantomImg = findViewById(R.id.phantomButton);
        shImg = findViewById(R.id.shieldButton);
        healthImg = findViewById(R.id.emsButton);
        Button buy = findViewById(R.id.BuyButton);
        money = findViewById(R.id.textMoney);

        aoeImg.setOnClickListener(this::invertCardView);
        phantomImg.setOnClickListener(this::invertCardView);
        shImg.setOnClickListener(this::invertCardView);
        healthImg.setOnClickListener(this::invertCardView);
        buy.setOnClickListener(v -> checkoutItems());

        updateMoneyShown();
        initViewsSelected();
        syncMarketBackendToFrontend();
    }

    private void updateMoneyShown() {
        String newText = "Money: " + PlayerManager.getInstance().getCurrentUser().getMoney();
        money.setText(newText);
    }

    /**
     * buys the items that were selected by the user.
     * If the user does not have enough money, they will not be bought
     */
    private void checkoutItems() {
        List<String> purchased = new ArrayList<>();
        List<String> failed = new ArrayList<>();
        for (View v : viewsSelected.keySet()) {
            if (viewsSelected.get(v).first == 1) {
                addToCart(failed, purchased, viewsSelected.get(v).second);
            }
        }
        updateMoneyShown();
        Toast.makeText(this.getApplicationContext(), parseList(purchased, true) + "\n" + parseList(failed, false), Toast.LENGTH_SHORT).show();
    }

    // adds an item given by argument "second" to the cart if user has enough money
    private void addToCart(List<String> failed, List<String> purchased, Class<? extends Item> second) {
        boolean transactionSuccessful = backend.buy(second, PlayerManager.getInstance().getCurrentUser());
        String nameItem = second.getSimpleName();
        if (transactionSuccessful) purchased.add(nameItem);
        else failed.add(nameItem);
    }

    // displays in format  "purchased: item1, item2
    //                      could not purchase: item3, item4"
    private String parseList(List<String> arg, boolean purchased) {
        String toStr = arg.toString();
        String listUpper = toStr.substring(1, toStr.length() - 1).toUpperCase();
        if (purchased) return arg.isEmpty() ? "" : "purchased " + listUpper;
        else return arg.isEmpty() ? "" : "could not purchase " + listUpper;
    }

    /**
     * Maps the views to their corresponding item classes as well as if they are selected
     */
    private void initViewsSelected() {
        viewsSelected.put(aoeImg, new Pair<>(0, Shrinker.class));
        viewsSelected.put(phantomImg, new Pair<>(0, Phantom.class));
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
            String newText = "Cost: " + backend.getStock().get(i).second + "\n" + "Value: " + (((int) (100 * i.getValue())) / 100.0);
            ((TextView) findViewById(itemToViewMap.get(i.getClass().hashCode()))).setText(newText);
        }
    }

    /**
     * sets up the map responsible for showing the prices of items as well their value
     */
    private void setupItemToViewMap() {
        itemToViewMap.put(Healthpack.class.hashCode(), R.id.costEms);
        itemToViewMap.put(Shield.class.hashCode(), R.id.costShield);
        itemToViewMap.put(Phantom.class.hashCode(), R.id.costPhantom);
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
