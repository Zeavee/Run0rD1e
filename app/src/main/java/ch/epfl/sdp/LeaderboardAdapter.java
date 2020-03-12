package ch.epfl.sdp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>{

    private List<String> mUsernames;
    private List<Double> mHealthPoints;
    private Context mContext;

    public LeaderboardAdapter(Context mContext, List<String> mUsernames, List<Double> mHealthPoints) {
        this.mContext = mContext;
        this.mUsernames = mUsernames;
        this.mHealthPoints = mHealthPoints;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_leaderboard_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ranking.setText(String.valueOf(position+1));
        holder.username.setText(mUsernames.get(position));
        holder.healthPoint.setText(String.valueOf(mHealthPoints.get(position)));
    }

    @Override
    public int getItemCount() {
        return mUsernames.size();
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
