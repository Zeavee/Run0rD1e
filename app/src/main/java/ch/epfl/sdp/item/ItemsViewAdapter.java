package ch.epfl.sdp.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entity.PlayerManager;

import static ch.epfl.sdp.R.id.amount;
import static ch.epfl.sdp.R.id.title;
import static ch.epfl.sdp.R.id.useitem;

public class ItemsViewAdapter extends RecyclerView.Adapter<ItemsViewAdapter.ItemsViewHolder>{
    private Context mContext;

    public ItemsViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        Map items = PlayerManager.getCurrentUser().getInventory().getItems();
        Item item = (Item) (items.keySet().toArray())[position];
        holder.name.setText(item.getName());
        holder.amountOfItem.setText(String.valueOf(items.get(item)));
        holder.button.setOnClickListener(v -> {
            item.use();
            PlayerManager.getCurrentUser().getInventory().removeItem(item);

            // Update the quantity of that item
            holder.amountOfItem.setText(String.valueOf(items.get(item)));
        });
    }

    @Override
    public int getItemCount() {
        return PlayerManager.getCurrentUser().getInventory().getItems().size();
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder {
//        ImageView image;
        private TextView name;
        private TextView amountOfItem;
        private Button button;

    public ItemsViewHolder(@NonNull View itemView) {
        super(itemView);
//      image = itemView.findViewById(R.id.image_view);
        name = itemView.findViewById(title);
        button = itemView.findViewById(useitem);
        amountOfItem = itemView.findViewById(amount);

        }
    }
}
