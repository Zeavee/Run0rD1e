package ch.epfl.sdp.social;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.epfl.sdp.R;
import ch.epfl.sdp.social.friends_firestore.FriendsRepositery;

public class FriendsListActivity extends AppCompatActivity implements WaitsOn<User> {
    private ChatRepository chatRepo;

    // To get the user info
    private static String current_email_id;

    // This should be called from the Authentication class's signIn/signUp success callback
    public static void setChatEmailID(String email_id) {
        current_email_id = email_id;
    }


    @Override
    protected void onResume() {

        super.onResume();
        chatRepo.setContextActivity(this);
        chatRepo.fetchFriends(new User(current_email_id));
        //chatRepo.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        current_email_id = "stupid1@gmail.com";
        chatRepo.setContextActivity(this);

        // TODO (ACTUALLY NOT BUT SINCE HIGHLIGHTING IS HARD I USED TODO) these two statements can be changed once FriendsListActivity is created
        AddFriendsActivity.setAdapter(new RecyclerQueryAdapter()); // placed here for mock testing purposes
        AddFriendsActivity.setServer(new FriendsRepositery());

        try {
            chatRepo.fetchFriends(new User(current_email_id));
        } catch (Exception e) {
            System.out.println("NOOOO" + e.getMessage());
        }
    }

    private void initRecyclerView(List<User> friends) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.friends_recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        MyFriendsRecyclerViewAdapter adapter = new MyFriendsRecyclerViewAdapter(friends, friend -> {

        });
        adapter.setOnItemClickListener((position, v) -> {
            ChatActivity.setChattingWith(friends.get(position).getEmail());
            Intent intent = new Intent(FriendsListActivity.this, ChatActivity.class);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void contentFetched(List<User> friends) {
        initRecyclerView(friends);
        Log.d("RESUMED ", "size   " + friends.size());
    }

    public void onAddFriendClicked(View v) {
        startActivity(new Intent(FriendsListActivity.this, AddFriendsActivity.class));
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(User friend);
    }
}
