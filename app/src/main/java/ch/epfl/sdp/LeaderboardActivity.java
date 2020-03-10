package ch.epfl.sdp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    private List<String> mUsernames = new ArrayList<>();
    private List<Double> mHealthPoints = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        mUsernames.add("aaaaa");
        mHealthPoints.add(100D);

        mUsernames.add("bbbbb");
        mHealthPoints.add(90.9D);

        mUsernames.add("ccccc");
        mHealthPoints.add(80D);

        mUsernames.add("ddddd");
        mHealthPoints.add(77D);

        mUsernames.add("eeeee");
        mHealthPoints.add(70D);

        mUsernames.add("fffff");
        mHealthPoints.add(69D);

        mUsernames.add("ggggg");
        mHealthPoints.add(66.6D);

        mUsernames.add("hhhhh");
        mHealthPoints.add(60.6D);

        initLeaderboardView();
    }

    private void initLeaderboardView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LeaderboardAdapter adapter = new LeaderboardAdapter(mUsernames, mHealthPoints);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
