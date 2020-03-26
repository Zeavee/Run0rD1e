package ch.epfl.sdp.leaderboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.UserForFirebase;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>{

    private List<UserForFirebase> mUserForFirebases;


    public LeaderboardAdapter(List<UserForFirebase> mUserForFirebases) {
        this.mUserForFirebases = mUserForFirebases;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_leaderboard_listitem, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        holder.ranking.setText(String.valueOf(position+1));
        holder.username.setText(mUserForFirebases.get(position).getUsername());
        holder.healthPoint.setText(String.valueOf(mUserForFirebases.get(position).getHealthPoints()));
    }

    @Override
    public int getItemCount() {
        return mUserForFirebases.size();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder {
        TextView ranking;
        TextView username;
        TextView healthPoint;

        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            ranking = itemView.findViewById(R.id.ranking);
            username = itemView.findViewById(R.id.username);
            healthPoint = itemView.findViewById(R.id.healthPoint);
        }
    }
}
