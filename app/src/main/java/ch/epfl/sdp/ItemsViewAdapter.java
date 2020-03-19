package ch.epfl.sdp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ch.epfl.sdp.R.id.amount;
import static ch.epfl.sdp.R.id.title;
import static ch.epfl.sdp.R.id.useitem;

public class ItemsViewAdapter extends RecyclerView.Adapter<ItemsViewAdapter.ItemsViewHolder>{
    private Context mContext;
    private Player player;
    private Map<String, Integer> inventory;
    private List<String> mNames = new ArrayList<>();
    private List<Integer> mAmounts = new ArrayList<>();

    public ItemsViewAdapter(Context mContext, Player player) {
        this.mContext = mContext;
        this.player = player;
        this.inventory = player.getItemInventory();
        for(String key: inventory.keySet()) {
            mNames.add(key);
            mAmounts.add(inventory.get(key));
        }
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        holder.name.setText(mNames.get(position));
        holder.amount.setText(String.valueOf(mAmounts.get(position)));
        holder.button.setOnClickListener(v -> {
            player.removeItemInInventory(mNames.get(position));
            int amount = Integer.parseInt(holder.amount.getText().toString());
            if(amount > 0) {
                holder.amount.setText(String.valueOf(amount-1));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder {
//        ImageView image;
        private TextView name;
        private TextView amountOfItem;
        private Button button;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
//            image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(title);
            button = itemView.findViewById(useitem);
            amountOfItem = itemView.findViewById(amount);

        }
    }
}
