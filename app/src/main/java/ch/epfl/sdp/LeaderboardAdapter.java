package ch.epfl.sdp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.epfl.sdp.db.LeaderboardEntity;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>{
    private List<LeaderboardEntity> mUsers;

    public LeaderboardAdapter() {
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_leaderboard_listitem, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        if (mUsers != null) {
            holder.ranking.setText(String.valueOf(position+1));
            holder.username.setText(mUsers.get(position).getUsername());
            holder.score.setText(String.valueOf(mUsers.get(position).getScore()));
        } else {
            // Covers the case of data not being ready yet.
            holder.ranking.setText("");
            holder.username.setText("");
            holder.score.setText("");
        }

    }

    @Override
    public int getItemCount() {
        if (mUsers != null)
            return mUsers.size();
        else return 0;
    }

    void setLeaderboard(List<LeaderboardEntity> mUsers) {
        this.mUsers = mUsers;
        notifyDataSetChanged();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder {
        TextView ranking;
        TextView username;
        TextView score;

        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            ranking = itemView.findViewById(R.id.ranking);
            username = itemView.findViewById(R.id.username);
            score = itemView.findViewById(R.id.score);
        }
    }
}
