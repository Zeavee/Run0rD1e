package ch.epfl.sdp;

import android.app.Activity;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SetupLeaderboard {
    public void setupLeaderboardView(Activity activity, List<UserForFirebase> mUserForFirebases) {
        RecyclerView recyclerView = activity.findViewById(R.id.recycler_view);
        LeaderboardAdapter adapter = new LeaderboardAdapter(mUserForFirebases);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }

    public void setupChampions(Activity activity, List<UserForFirebase> mUserForFirebases) {
        TextView tv_username1 = activity.findViewById(R.id.tv_username1);
        tv_username1.setText(mUserForFirebases.get(0).getUsername());
        TextView tv_healthpoint1 = activity.findViewById(R.id.tv_healthpoint1);
        tv_healthpoint1.setText(String.valueOf(mUserForFirebases.get(0).getHealthPoints()));

        TextView tv_username2 = activity.findViewById(R.id.tv_username2);
        tv_username2.setText(mUserForFirebases.get(1).getUsername());
        TextView tv_healthpoint2 = activity.findViewById(R.id.tv_healthpoint2);
        tv_healthpoint2.setText(String.valueOf(mUserForFirebases.get(1).getHealthPoints()));

        TextView tv_username3 = activity.findViewById(R.id.tv_username3);
        tv_username3.setText(mUserForFirebases.get(2).getUsername());
        TextView tv_healthpoint3 = activity.findViewById(R.id.tv_healthpoint3);
        tv_healthpoint3.setText(String.valueOf(mUserForFirebases.get(2).getHealthPoints()));
    }
}
