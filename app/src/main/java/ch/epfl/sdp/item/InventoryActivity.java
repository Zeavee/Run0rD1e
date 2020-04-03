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

        initInventory();
    }

    private void initInventory() {
        //TODO Later this player should come from the local storage
        player = new Player(22, 22, 50, "admin", "admin@gmail.com");
        player.getInventory().setItemQuantity(new Healthpack(10), 2);
        player.getInventory().setItemQuantity(new Shield(10), 5);
        player.getInventory().setItemQuantity(new Shrinker(10,10), 6);
        player.getInventory().setItemQuantity(new Scan(10), 5);
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


