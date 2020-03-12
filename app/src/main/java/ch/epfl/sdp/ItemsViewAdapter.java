package ch.epfl.sdp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemsViewAdapter extends RecyclerView.Adapter<ItemsViewAdapter.viewHolder>{
    private Context mContext;
    private ArrayList<String> mNames;
    private ArrayList<Integer> mAmounts;

    public ItemsViewAdapter(Context mContext, ArrayList<String> mNames, ArrayList<Integer> mAmounts) {
        this.mContext = mContext;
        this.mNames = mNames;
        this.mAmounts = mAmounts;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.name.setText(mNames.get(position));
        holder.amount.setText(String.valueOf(mAmounts.get(position)));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(holder.amount.getText().toString());
                holder.amount.setText(String.valueOf(amount-1));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
//        ImageView image;
        TextView name;
        TextView amount;
        Button button;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
//            image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.title);
            button = itemView.findViewById(R.id.useitem);
            amount = itemView.findViewById(R.id.amount);

        }
    }
}
