package ch.epfl.sdp.leaderboard;

import android.app.usage.EventStats;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.room.LeaderboardEntity;
import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;

public class GeneralLeaderboardActivity extends AppCompatActivity {
    private GeneralLeaderboardViewModel generalLeaderboardViewModel;
    private CommonDatabaseAPI commonDatabaseAPI;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final GeneralLeaderboardAdapter adapter = new GeneralLeaderboardAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        commonDatabaseAPI = appContainer.commonDatabaseAPI;

        // Get a new or existing ViewModel from the ViewModelProvider.
        generalLeaderboardViewModel = new ViewModelProvider(this).get(GeneralLeaderboardViewModel.class);

        // Add an observer on the LiveData.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        generalLeaderboardViewModel.getLeaderboard().observe(this, users -> { adapter.setLeaderboard(skipFirst(users, 3)); });
        generalLeaderboardViewModel.getLeaderboard().observe(this, users -> setupChampions(users));

        addGeneralGameScoreListener();
    }
    /**
     * Leaderboard presentation in the useItem List it will start showing from 4.
     * */

    private List<LeaderboardEntity> skipFirst(List<LeaderboardEntity> original, int skipFirst) {
        List<LeaderboardEntity> result = new ArrayList<>();
        for(int i = skipFirst; i < original.size(); i++) {
            result.add(original.get(i));
        }

        return result;
    }

    public void setupChampions(List<LeaderboardEntity> players) {
        TextView tv_username = null;
        TextView tv_healthpoint = null;
        if(players == null) {
            return;
        }

        for(int i = 0; i < players.size() && i < 3; i++) {
            switch(i) {
                case 0:
                    tv_username = findViewById(R.id.tv_username1);
                    tv_healthpoint = findViewById(R.id.tv_healthpoint1);
                    break;
                case 1:
                    tv_username = findViewById(R.id.tv_username2);
                    tv_healthpoint = findViewById(R.id.tv_healthpoint2);
                    break;
                case 2:
                    tv_username = findViewById(R.id.tv_username3);
                    tv_healthpoint = findViewById(R.id.tv_healthpoint3);
                    break;
            }
            tv_username.setText(players.get(i).getUsername());
            tv_healthpoint.setText(String.valueOf(players.get(i).getScore()));
        }
    }

    private void addGeneralGameScoreListener() {
        commonDatabaseAPI.generalGameScoreListener(value -> {
            if(value.isSuccessful()) {
                for(UserForFirebase userForFirebase: value.getResult()) {
                    LeaderboardEntity user = new LeaderboardEntity(userForFirebase.getEmail(), userForFirebase.getUsername(), userForFirebase.getGeneralScore());
                    generalLeaderboardViewModel.insertToLeaderboard(user);
                }
            }
        });
    }

}
