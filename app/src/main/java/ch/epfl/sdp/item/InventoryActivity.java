package ch.epfl.sdp.item;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;

public class InventoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        initRecyclerView();
    }

    private void initInventory() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.items_recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        ItemsViewAdapter adpater = new ItemsViewAdapter(this);
        recyclerView.setAdapter(adpater);
    }
}


