package ch.epfl.sdp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        player.setItemInventory("Healthpack", 2);
        player.setItemInventory("Shield", 5);
        player.setItemInventory("Shrinker", 6);
        player.setItemInventory("Scan", 5);
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


