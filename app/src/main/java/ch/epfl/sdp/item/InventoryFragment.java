package ch.epfl.sdp.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entity.Player;

public class InventoryFragment extends Fragment {
    private Player player;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = getView().findViewById(R.id.items_recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        ItemsViewAdapter adpater = new ItemsViewAdapter(getActivity(), player);
        recyclerView.setAdapter(adpater);
    }
}


