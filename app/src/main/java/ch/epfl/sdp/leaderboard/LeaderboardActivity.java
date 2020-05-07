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
import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;

public class LeaderboardActivity extends AppCompatActivity {
    private LeaderboardViewModel leaderboardViewModel;
    private CommonDatabaseAPI commonDatabaseAPI;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final LeaderboardAdapter adapter = new LeaderboardAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        commonDatabaseAPI = appContainer.commonDatabaseAPI;

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

        commonDatabaseAPI.syncCloudFirebaseToRoom(leaderboardViewModel);
    }

    public void setupChampions(List<LeaderboardEntity> players) {
        if (players == null || players.size() < 3) {
            return;
        }
        TextView tv_username1 = findViewById(R.id.tv_username1);
        tv_username1.setText(players.get(0).getUsername());
        TextView tv_healthpoint1 = findViewById(R.id.tv_healthpoint1);
        tv_healthpoint1.setText(String.valueOf(players.get(0).getScore()));

        TextView tv_username2 = findViewById(R.id.tv_username2);
        tv_username2.setText(players.get(1).getUsername());
        TextView tv_healthpoint2 = findViewById(R.id.tv_healthpoint2);
        tv_healthpoint2.setText(String.valueOf(players.get(1).getScore()));

        TextView tv_username3 = findViewById(R.id.tv_username3);
        tv_username3.setText(players.get(2).getUsername());
        TextView tv_healthpoint3 = findViewById(R.id.tv_healthpoint3);
        tv_healthpoint3.setText(String.valueOf(players.get(2).getScore()));
    }

}
