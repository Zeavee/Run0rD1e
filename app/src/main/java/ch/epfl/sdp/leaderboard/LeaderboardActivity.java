package ch.epfl.sdp.leaderboard;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.epfl.sdp.R;
import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.CommonFirestoreDatabaseAPI;
import ch.epfl.sdp.database.room.LeaderboardEntity;

public class LeaderboardActivity extends AppCompatActivity {
    private LeaderboardViewModel leaderboardViewModel;
    public CommonDatabaseAPI commonDatabaseAPI;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final LeaderboardAdapter adapter = new LeaderboardAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        leaderboardViewModel = new ViewModelProvider(this).get(LeaderboardViewModel.class);

        // Add an observer on the LiveData.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        leaderboardViewModel.getLeaderboard().observe(this, users -> {
            // Update the cached copy of the words in the adapter.
            adapter.setLeaderboard(users);
        });
        leaderboardViewModel.getLeaderboard().observe(this, users -> setupChampions(users));

        commonDatabaseAPI = new CommonFirestoreDatabaseAPI();
        commonDatabaseAPI.syncCloudFirebaseToRoom(leaderboardViewModel);
    }

    private void setupChampions(List<LeaderboardEntity> users) {
        if (users == null || users.size() < 3) {
            return;
        }
        TextView tv_username1 = findViewById(R.id.tv_username1);
        tv_username1.setText(users.get(0).getUsername());
        TextView tv_healthpoint1 = findViewById(R.id.tv_healthpoint1);
        tv_healthpoint1.setText(String.valueOf(users.get(0).getScore()));

        TextView tv_username2 = findViewById(R.id.tv_username2);
        tv_username2.setText(users.get(1).getUsername());
        TextView tv_healthpoint2 = findViewById(R.id.tv_healthpoint2);
        tv_healthpoint2.setText(String.valueOf(users.get(1).getScore()));

        TextView tv_username3 = findViewById(R.id.tv_username3);
        tv_username3.setText(users.get(2).getUsername());
        TextView tv_healthpoint3 = findViewById(R.id.tv_healthpoint3);
        tv_healthpoint3.setText(String.valueOf(users.get(2).getScore()));
    }

}
