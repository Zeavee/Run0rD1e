package ch.epfl.sdp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>{

    private List<User> mUsers;


    public LeaderboardAdapter(List<User> mUsers) {
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_leaderboard_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ranking.setText(String.valueOf(position+1));
        holder.username.setText(mUsers.get(position).getUsername());
        holder.healthPoint.setText(String.valueOf(mUsers.get(position).getHealthPoints()));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ranking;
        TextView username;
        TextView healthPoint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ranking = itemView.findViewById(R.id.ranking);
            username = itemView.findViewById(R.id.username);
            healthPoint = itemView.findViewById(R.id.healthPoint);
        }
    }
}
