package ch.epfl.sdp.social;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.UserForFirebase;
import ch.epfl.sdp.item.ItemsViewAdapter;

public class FriendsListActivity extends AppCompatActivity {
    private Friend friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        friends = new Friend(new UserForFirebase("tempUsername", "tempEmail@email.com"));
        friends.addFriend(new Friend(new UserForFirebase("tempUsername2", "tempEmail2@email.com")));
        friends.addFriend(new Friend(new UserForFirebase("tempUsername3", "tempEmail3@email.com")));

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());*/
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.friends_recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        MyFriendsRecyclerViewAdapter adapter = new MyFriendsRecyclerViewAdapter(friends.getFriends(), friend -> {

        });
        adapter.setOnItemClickListener((position, v) -> {
            Intent intent = new Intent(FriendsListActivity.this , ChatActivity.class);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Friend friend);
    }
}
