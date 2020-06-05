package ch.epfl.sdp.ui.map;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entities.player.Player;
import ch.epfl.sdp.entities.player.PlayerManager;

/**
 *  An Adapter of the recyclerView in CurrentGameLeaderBoard UI
 */
public class CurrentGameLeaderBoardAdapter extends RecyclerView.Adapter<CurrentGameLeaderBoardAdapter.CurrentGameLeaderBoardViewHolder> {
    @NonNull
    @Override
    public CurrentGameLeaderBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_ingame_leaderboard_listitem, parent, false);
        return new CurrentGameLeaderBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentGameLeaderBoardViewHolder holder, int position) {
        Player player = PlayerManager.getInstance().getPlayersSortByIngameScore().get(position);

        holder.inGameRanking.setText(String.valueOf(position + 1));
        holder.username.setText(player.getUsername());
        holder.inGameScore.setText(String.valueOf(player.score.getCurrentGameScore(player)));
        if (player.status.getHealthPoints() == 0) {
            holder.isAlive.setText(R.string.playerStatusDead);
        } else {
            holder.isAlive.setText(R.string.playerStatusAlive);
        }
    }

    @Override
    public int getItemCount() {
        return PlayerManager.getInstance().getPlayersSortByIngameScore().size();
    }

    /**
     * The CurrentGameLeaderBoardViewHolder describes an item view and metadata about its place within the RecyclerView
     */
    static class CurrentGameLeaderBoardViewHolder extends RecyclerView.ViewHolder {
        final TextView inGameRanking;
        final TextView username;
        final TextView inGameScore;
        final TextView isAlive;

        CurrentGameLeaderBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            inGameRanking = itemView.findViewById(R.id.ingame_leaderboard_ranking);
            username = itemView.findViewById(R.id.ingame_leaderboard_username);
            inGameScore = itemView.findViewById(R.id.ingame_leaderboard_score);
            isAlive = itemView.findViewById(R.id.ingame_leaderboard_isalive);
        }
    }
}