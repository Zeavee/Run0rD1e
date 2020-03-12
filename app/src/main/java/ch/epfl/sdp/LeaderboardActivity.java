package ch.epfl.sdp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

        leaderboardData();
        initLeaderboardView();
        setChampions();
    }

    private void leaderboardData() {
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

        mUsernames.add("IIIII");
        mHealthPoints.add(60.0D);

        mUsernames.add("JJJJJ");
        mHealthPoints.add(55.0D);
    }

    private void initLeaderboardView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LeaderboardAdapter adapter = new LeaderboardAdapter(this, mUsernames, mHealthPoints);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setChampions() {
        TextView tv_username1 = findViewById(R.id.tv_username1);
        tv_username1.setText(mUsernames.get(0));
        TextView tv_healthpoint1 = findViewById(R.id.tv_healthpoint1);
        tv_healthpoint1.setText(String.valueOf(mHealthPoints.get(0)));

        TextView tv_username2 = findViewById(R.id.tv_username2);
        tv_username2.setText(mUsernames.get(1));
        TextView tv_healthpoint2 = findViewById(R.id.tv_healthpoint2);
        tv_healthpoint2.setText(String.valueOf(mHealthPoints.get(1)));

        TextView tv_username3 = findViewById(R.id.tv_username3);
        tv_username3.setText(mUsernames.get(2));
        TextView tv_healthpoint3 = findViewById(R.id.tv_healthpoint3);
        tv_healthpoint3.setText(String.valueOf(mHealthPoints.get(2)));
    }
}
