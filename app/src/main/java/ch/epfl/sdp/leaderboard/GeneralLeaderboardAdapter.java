package ch.epfl.sdp.leaderboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import ch.epfl.sdp.R;
import ch.epfl.sdp.database.room.LeaderboardEntity;

public class GeneralLeaderboardAdapter extends RecyclerView.Adapter<GeneralLeaderboardAdapter.GeneralLeaderboardViewHolder>{
    private List<LeaderboardEntity> mUsers;

    @NonNull
    @Override
    public GeneralLeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_leaderboard_listitem, parent, false);
        return new GeneralLeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GeneralLeaderboardViewHolder holder, int position) {
        if (mUsers != null) {
            holder.ranking.setText(String.valueOf(position+1));
            holder.username.setText(mUsers.get(position).getUsername());
            holder.generalScore.setText(String.valueOf(mUsers.get(position).getScore()));
        } else {
            // Covers the case of data not being ready yet.
            holder.ranking.setText("");
            holder.username.setText("");
            holder.generalScore.setText("");
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

    public class GeneralLeaderboardViewHolder extends RecyclerView.ViewHolder {
        TextView ranking;
        TextView username;
        TextView generalScore;

        public GeneralLeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            ranking = itemView.findViewById(R.id.leaderboard_ranking);
            username = itemView.findViewById(R.id.leaderboard_username);
            generalScore = itemView.findViewById(R.id.leaderboard_generalscore);
        }
    }
}
