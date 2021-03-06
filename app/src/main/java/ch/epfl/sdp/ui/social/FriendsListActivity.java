package ch.epfl.sdp.ui.social;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.R;
import ch.epfl.sdp.utils.AppContainer;
import ch.epfl.sdp.utils.MyApplication;
import ch.epfl.sdp.utils.WaitsOn;
import ch.epfl.sdp.social.conversation.SocialRepository;
import ch.epfl.sdp.database.room.social.User;

/**
 * This activity shows the friends list of the user
 */
public class FriendsListActivity extends AppCompatActivity implements WaitsOn<User> {

    // To get the user info
    private String current_email_id;
    private List<User> friends;


    @Override
    protected void onResume() {
        super.onResume();
        SocialRepository.setContextActivity(this);
        SocialRepository.currentEmail = current_email_id;
        SocialRepository chatRepo = SocialRepository.getInstance();
        chatRepo.fetchFriends(new User(current_email_id));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        current_email_id = appContainer.authenticationAPI.getCurrentUserEmail();

        SocialRepository.setContextActivity(this);
        SocialRepository.currentEmail = current_email_id; // injection point here

        SocialRepository.getInstance().fetchFriends(new User(current_email_id));
    }

    private void initRecyclerView(List<User> friends) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.friends_recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        MyFriendsRecyclerViewAdapter adapter = new MyFriendsRecyclerViewAdapter(friends);
        adapter.setOnItemClickListener((position, v) -> {
            Intent intent = new Intent(FriendsListActivity.this, ChatActivity.class);
            intent.putExtra("chattingWith", friends.get(position).getEmail());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void contentFetched(List<User> friends) {
        initRecyclerView(friends);
        this.friends = friends;
        Log.d("RESUMED ", "size   " + friends.size());
    }

    /**
     * This method is used when we click on the button for adding a friend
     *
     * @param v the view on which we clicked
     */
    public void onAddFriendClicked(View v) {
        startActivity(new Intent(FriendsListActivity.this, AddFriendsActivity.class));
    }

    /**
     * This method returns the list of friends of the user
     *
     * @return the list of friends of the user
     */
    public List<User> getFriends() {
        return new ArrayList<>(friends);
    }

}
