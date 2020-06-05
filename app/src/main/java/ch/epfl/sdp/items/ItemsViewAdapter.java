package ch.epfl.sdp.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entities.player.PlayerManager;

import static ch.epfl.sdp.R.id.amount;
import static ch.epfl.sdp.R.id.title;
import static ch.epfl.sdp.R.id.useitem;

/**
 * Recycler view adapter for displaying items.
 */
public class ItemsViewAdapter extends RecyclerView.Adapter<ItemsViewAdapter.ItemsViewHolder> {
    private final ItemFactory itemFactory;

    public ItemsViewAdapter() {
        itemFactory = new ItemFactory();
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        Map items = PlayerManager.getInstance().getCurrentUser().getInventory().getItems();
        String itemName = (String) items.keySet().toArray()[position];
        holder.name.setText(itemName);
        holder.amountOfItem.setText(String.valueOf(items.get(itemName)));
        holder.button.setOnClickListener(v -> {
            itemFactory.getItem(itemName).useOn(PlayerManager.getInstance().getCurrentUser());
            if (PlayerManager.getInstance().isSoloMode() || PlayerManager.getInstance().isServer()) {

                // If currentUser is in soloMode or the currentUser is the Server in multiPlayer mode
                // use the item locally and get the effect directly.
                PlayerManager.getInstance().getCurrentUser().getInventory().removeItem(itemName);
            } else {

                // If the currentUser is the Client in multiPlayer mode
                // Just move the item to the usedItem Map and updated in cloud firebase to inform the Server
                PlayerManager.getInstance().getCurrentUser().getInventory().moveItemToUsedItems(itemName);
            }

            // Update the quantity of that item
            holder.amountOfItem.setText(String.valueOf(items.get(itemName)));
        });
    }

    @Override
    public int getItemCount() {
        return PlayerManager.getInstance().getCurrentUser().getInventory().getItems().size();
    }

    static class ItemsViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView amountOfItem;
        private final Button button;

        ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(title);
            button = itemView.findViewById(useitem);
            amountOfItem = itemView.findViewById(amount);

        }
    }
}
