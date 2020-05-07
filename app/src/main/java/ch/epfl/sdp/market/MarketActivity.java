package ch.epfl.sdp.market;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.item.Shield;

public class MarketActivity extends AppCompatActivity {

    private SeekBar health;
    private SeekBar shield;
    private Button buy;
    private TextView priceHealth;
    private TextView priceShield;
    private final static int SHIELD_COST_PER_MINUTE = 2;
    private final static int HEALTH_COST_PER_MINUTE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        health = findViewById(R.id.healthSeek);
        shield = findViewById(R.id.shieldSeek);
        buy = findViewById(R.id.buyButton);
        priceHealth = findViewById(R.id.priceHealth);
        priceShield = findViewById(R.id.priceShield);
        buy.setOnClickListener(v->buyItems());

        health.setOnSeekBarChangeListener(new SeekBarCustomListener(priceHealth, HEALTH_COST_PER_MINUTE));
        shield.setOnSeekBarChangeListener(new SeekBarCustomListener(priceShield, SHIELD_COST_PER_MINUTE));
    }

    /**
     * buys items such as health (max 100 health) or shield (max 20 minutes)
     */
    private void buyItems(){
        int bank = PlayerManager.getCurrentUser().getMoney();
        int assetsTotal = health.getProgress()+shield.getProgress();
        if (assetsTotal > bank){
            Toast.makeText(MarketActivity.this, "Insufficient funds", Toast.LENGTH_LONG);
        }else {
            if (shield.getProgress() > 0) {
                PlayerManager.getCurrentUser().getInventory().addItem(new Shield(60 * shield.getProgress()));
            }
            double currentHealth = PlayerManager.getCurrentUser().getHealthPoints();
            PlayerManager.getCurrentUser().setHealthPoints(Math.max(100,currentHealth+health.getProgress()));
            PlayerManager.getCurrentUser().removeMoney(assetsTotal);
            Toast.makeText(MarketActivity.this, "Transaction successful", Toast.LENGTH_LONG);
        }
    }
}
class SeekBarCustomListener implements SeekBar.OnSeekBarChangeListener{

    private TextView priceTag;
    private int unitCost;

    public SeekBarCustomListener(TextView v, int unitCost) {
        this.priceTag = v;
        this.unitCost = unitCost;
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        priceTag.setText("Cost: "+ Integer.toString(unitCost*progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }
}