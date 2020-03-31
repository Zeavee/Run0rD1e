package ch.epfl.sdp.leaderboard;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.epfl.sdp.db.PlayerEntity;
import ch.epfl.sdp.db.AppViewModel;
import ch.epfl.sdp.R;
import ch.epfl.sdp.database.FirestoreUserData;
import ch.epfl.sdp.database.UserDataController;

public class LeaderboardActivity extends AppCompatActivity {
    private AppViewModel appViewModel;
    public UserDataController userDataController;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final LeaderboardAdapter adapter = new LeaderboardAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        // Add an observer on the LiveData.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        appViewModel.getLeaderboard().observe(this, users -> {
            // Update the cached copy of the words in the adapter.
            adapter.setLeaderboard(users);
        });
        appViewModel.getLeaderboard().observe(this, users -> setupChampions(users));

        userDataController = new FirestoreUserData();
        userDataController.syncCloudFirebaseToRoom(appViewModel);
    }

    private void setupChampions(List<PlayerEntity> players) {
        if (players == null || players.size() < 3) {
            return;
        }
        TextView tv_username1 = findViewById(R.id.tv_username1);
        tv_username1.setText(players.get(0).getUsername());
        TextView tv_healthpoint1 = findViewById(R.id.tv_healthpoint1);
        tv_healthpoint1.setText(String.valueOf(players.get(0).getHealthpoint()));

        TextView tv_username2 = findViewById(R.id.tv_username2);
        tv_username2.setText(players.get(1).getUsername());
        TextView tv_healthpoint2 = findViewById(R.id.tv_healthpoint2);
        tv_healthpoint2.setText(String.valueOf(players.get(1).getHealthpoint()));

        TextView tv_username3 = findViewById(R.id.tv_username3);
        tv_username3.setText(players.get(2).getUsername());
        TextView tv_healthpoint3 = findViewById(R.id.tv_healthpoint3);
        tv_healthpoint3.setText(String.valueOf(players.get(2).getHealthpoint()));
    }

}
