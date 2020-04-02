package ch.epfl.sdp.social;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.epfl.sdp.R;

public class RecyclerQueryAdapter  extends RecyclerView.Adapter<RecyclerQueryAdapter.ViewHolder> implements Filterable {

    private List<String> friendsList;

    //movies but without being filtered
    private List<String> friendsAll;

    public RecyclerQueryAdapter(List<String> moviesList) {
        this.friendsList = moviesList;
        this.friendsAll = new ArrayList<>(moviesList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item_query, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.usernameTextView.setText(String.valueOf(position));
        holder.emailTextView.setText(friendsList.get(position));


    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {

        // run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<String> filteredList = new ArrayList<>();
            if (constraint.toString().isEmpty())
            {
                filteredList.addAll(friendsAll);
            }else {
                for (String friend: friendsAll)
                {
                    if (friend.toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        filteredList.add(friend);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        // runs on UI thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            friendsList.clear();
            friendsList.addAll((Collection<? extends String>) results.values);
            Log.d("CONSTAINT IS", constraint.toString());
            Log.d("SIZE isssssssss  ", " "+friendsList.size());
            for (int i=0;i< friendsList.size();++i) {
                Log.d("VALUES OF LIST ARE ", i+" "+friendsList.get(i));
            }
            notifyDataSetChanged();
        }
    };
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private ImageView imageView;
        private TextView emailTextView, usernameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView5);
            emailTextView = itemView.findViewById(R.id.textViewEmail);
            usernameTextView = itemView.findViewById(R.id.textViewUsername);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View v) {
                    friendsList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    return true;
                }
            });
        }

        /// here is where you add that the user become friends in both firestore and sqlite
        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), friendsList.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    }
}
