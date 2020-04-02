package ch.epfl.sdp.item;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entity.Player;

public class InventoryActivity extends AppCompatActivity {
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        initPlayer();
    }

    private void initPlayer() {
        //TODO Later this player should come from the local storage
        player = new Player(22, 22, 50, "admin", "admin@gmail.com");
        player.getInventory().setItemQuantity("Healthpack", 2);
        player.getInventory().setItemQuantity("Shield", 5);
        player.getInventory().setItemQuantity("Shrinker", 6);
        player.getInventory().setItemQuantity("Scan", 5);
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.items_recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        ItemsViewAdapter adpater = new ItemsViewAdapter(this, player);
        recyclerView.setAdapter(adpater);
    }
}

