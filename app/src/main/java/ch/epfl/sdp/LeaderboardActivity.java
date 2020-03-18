package ch.epfl.sdp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private List<UserForFirebase> mUserForFirebases = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        firebaseFirestore = FirebaseFirestore.getInstance();

        loadDataFromFirebase();
    }

    private void loadDataFromFirebase() {

        firebaseFirestore.collection("Users")
            .orderBy("healthPoints", Query.Direction.DESCENDING)
            .addSnapshotListener((queryDocumentSnapshots, e) -> {
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                    UserForFirebase userForFirebase = documentSnapshot.toObject(UserForFirebase.class);
                    mUserForFirebases.add(userForFirebase);
                }
                setUpLeaderboardView(mUserForFirebases);
                setChampions(mUserForFirebases);
            });
    }

    private void setUpLeaderboardView(List<UserForFirebase> mUserForFirebases) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LeaderboardAdapter adapter = new LeaderboardAdapter(mUserForFirebases);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setChampions(List<UserForFirebase> mUserForFirebases) {
        TextView tv_username1 = findViewById(R.id.tv_username1);
        tv_username1.setText(mUserForFirebases.get(0).getUsername());
        TextView tv_healthpoint1 = findViewById(R.id.tv_healthpoint1);
        tv_healthpoint1.setText(String.valueOf(mUserForFirebases.get(0).getHealthPoints()));

        TextView tv_username2 = findViewById(R.id.tv_username2);
        tv_username2.setText(mUserForFirebases.get(1).getUsername());
        TextView tv_healthpoint2 = findViewById(R.id.tv_healthpoint2);
        tv_healthpoint2.setText(String.valueOf(mUserForFirebases.get(1).getHealthPoints()));

        TextView tv_username3 = findViewById(R.id.tv_username3);
        tv_username3.setText(mUserForFirebases.get(2).getUsername());
        TextView tv_healthpoint3 = findViewById(R.id.tv_healthpoint3);
        tv_healthpoint3.setText(String.valueOf(mUserForFirebases.get(2).getHealthPoints()));
    }

}
