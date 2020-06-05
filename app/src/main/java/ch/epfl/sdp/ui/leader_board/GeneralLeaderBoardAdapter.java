package ch.epfl.sdp.ui.leader_board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.room.leader_board.GeneralLeaderBoardEntity;

/**
 * An adapter of the recyclerView in GeneralLeaderBoard UI
 */
public class GeneralLeaderBoardAdapter extends RecyclerView.Adapter<GeneralLeaderBoardAdapter.GeneralLeaderBoardViewHolder> {
    private List<GeneralLeaderBoardEntity> mUsers;

    @NonNull
    @Override
    public GeneralLeaderBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_general_leaderboard_listitem, parent, false);
        return new GeneralLeaderBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GeneralLeaderBoardViewHolder holder, int position) {
        holder.ranking.setText(String.valueOf(position + 4));
        holder.username.setText(mUsers.get(position).getUsername());
        holder.generalScore.setText(String.valueOf(mUsers.get(position).getGeneralScore()));
    }

    @Override
    public int getItemCount() {
        if (mUsers != null)
            return mUsers.size();
        else return 0;
    }

    /**
     * When the general score of any user changes in the local Room database, this method will be called to reset the general leaderBoard
     *
     * @param mUsers The list of the leaderBoardEntity which contains the general score of all the users who have played this game
     */
    void setLeaderBoard(List<GeneralLeaderBoardEntity> mUsers) {
        this.mUsers = mUsers;
        notifyDataSetChanged();
    }

    /**
     * The GeneralLeaderBoardViewHolder describes an item view and metadata about its place within the RecyclerView
     */
    static class GeneralLeaderBoardViewHolder extends RecyclerView.ViewHolder {
        final TextView ranking;
        final TextView username;
        final TextView generalScore;

        GeneralLeaderBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            ranking = itemView.findViewById(R.id.generalLeaderBoard_ranking);
            username = itemView.findViewById(R.id.generalLeaderBoard_username);
            generalScore = itemView.findViewById(R.id.generalLeaderBoard_score);
        }
    }
}
