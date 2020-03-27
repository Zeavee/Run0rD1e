package ch.epfl.sdp.social;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.epfl.sdp.R;
import ch.epfl.sdp.item.ItemsViewAdapter;

public class FriendsListActivity extends AppCompatActivity implements WaitsOnFriendFetch {
    private User usr_amr;
    private User usr_shaima;
    private ChatRepository chatRepo;

    /*public static ChatRepository getChatRepo() {
        return chatRepo;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        chatRepo = ChatRepository.createRepo(this);
        usr_amr = new User("amro.abdrabo@gmail.com", "amro", "abdo");
        usr_shaima = new User("shaima@abc.com", "shaima", "hhhhh");
        try {
            chatRepo.addUser(usr_amr);
            chatRepo.addUser(usr_shaima);
            chatRepo.addFriends(usr_amr, usr_shaima);
            chatRepo.fetchFriends(usr_amr);
        }
        catch(Exception e){
            System.out.println("hihi hihi" +e.getMessage());
        }
        //friends = new Friend(new UserForFirebase("tempUsername", "tempEmail@email.com"));
        //friends.addFriend(new Friend(new UserForFirebase("tempUsername2", "tempEmail2@email.com")));
        //friends.addFriend(new Friend(new UserForFirebase("tempUsername3", "tempEmail3@email.com")));

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());*/
        //friendsFetched(friends.getFriends());
        //initRecyclerView();
    }

    private void initRecyclerView(List<User> friends) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.friends_recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        MyFriendsRecyclerViewAdapter adapter = new MyFriendsRecyclerViewAdapter(friends, friend -> {

        });
        adapter.setOnItemClickListener((position, v) -> {
            ChatActivity.setChattingWith(friends.get(0).getEmail());
            Intent intent = new Intent(FriendsListActivity.this , ChatActivity.class);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void friendsFetched(List<User> friends) {
        initRecyclerView(friends);
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(User friend);
    }
}
