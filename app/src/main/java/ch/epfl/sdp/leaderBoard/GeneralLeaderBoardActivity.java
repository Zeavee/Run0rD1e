package ch.epfl.sdp.leaderBoard;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.entityForFirebase.UserForFirebase;
import ch.epfl.sdp.database.room.GeneralLeaderBoardEntity;
import ch.epfl.sdp.database.room.GeneralLeaderBoardViewModel;
import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;

/**
 * An activity used to show the general leaderBoard which ranks the accumulated scores of all the users who have played this game
 * The activity uses the data stored in the local Room database, so the general leaderBoard can be shown in offline mode.
 * The local Room database is updated according to cloud firebase fireStore in real time using a listener.
 */
public class GeneralLeaderBoardActivity extends AppCompatActivity {
    private GeneralLeaderBoardViewModel generalLeaderboardViewModel;
    private CommonDatabaseAPI commonDatabaseAPI;
    private static final int CHAMPION_NUM = 3;

    private TextView tv_username1, tv_generalScore1, tv_username2, tv_generalScore2, tv_username3, tv_generalScore3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_leaderboard);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final GeneralLeaderBoardAdapter adapter = new GeneralLeaderBoardAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        commonDatabaseAPI = appContainer.commonDatabaseAPI;

        // Get a new or existing ViewModel from the ViewModelProvider.
        generalLeaderboardViewModel = new ViewModelProvider(this).get(GeneralLeaderBoardViewModel.class);

        // Add an observer on the LiveData.
        // The onChanged() method fires when the observed data (stored in local Room database) changes and the activity is in the foreground.
        generalLeaderboardViewModel.getGeneralLeaderBoard().observe(this, this::setupChampions);
        generalLeaderboardViewModel.getGeneralLeaderBoard().observe(this, users -> {
            if (users.size() > 3) adapter.setLeaderBoard(users.subList(CHAMPION_NUM, users.size()));
        });

        tv_username1 = findViewById(R.id.tv_username1);
        tv_generalScore1 = findViewById(R.id.tv_generalScore1);
        tv_username2 = findViewById(R.id.tv_username2);
        tv_generalScore2 = findViewById(R.id.tv_generalScore2);
        tv_username3 = findViewById(R.id.tv_username3);
        tv_generalScore3 = findViewById(R.id.tv_generalScore3);
        // Add the general score listener to update the change of the score from cloud firebase to local room database at real time
        addGeneralGameScoreListener();
    }

    /**
     * Set the champions (the first three) information on general leaderBoard, including username and generalScore
     *
     * @param users A list of leaderBoardEntity from local Room database which contains username and general scores of all the users who have played this game
     */
    private void setupChampions(List<GeneralLeaderBoardEntity> users) {
        if (users == null) {
            return;
        }

        for (int i = 0; i < users.size() && i < 3; i++) {
            String username = users.get(i).getUsername();
            String generalScore = String.valueOf(users.get(i).getGeneralScore());
            switch (i) {
                case 0:
                    tv_username1.setText(username);
                    tv_generalScore1.setText(generalScore);
                    break;
                case 1:
                    tv_username2.setText(username);
                    tv_generalScore2.setText(generalScore);
                    break;
                case 2:
                    tv_username3.setText(username);
                    tv_generalScore3.setText(generalScore);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Add a listener to the general score of all the users in the cloud firebase
     * Once the general score of some users changed, we updated our local Room database accordingly
     */
    private void addGeneralGameScoreListener() {
        commonDatabaseAPI.generalGameScoreListener(value -> {
            if (value.isSuccessful()) {
                for (UserForFirebase userForFirebase : value.getResult()) {
                    GeneralLeaderBoardEntity user = new GeneralLeaderBoardEntity(userForFirebase.getEmail(), userForFirebase.getUsername(), userForFirebase.getGeneralScore());
                    generalLeaderboardViewModel.insertToGeneralLeaderBoard(user);
                }
            }
        });
    }

}