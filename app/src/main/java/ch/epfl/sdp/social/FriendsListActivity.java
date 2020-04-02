package ch.epfl.sdp.social;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.item.ItemsViewAdapter;
import ch.epfl.sdp.login.AuthenticationController;
import ch.epfl.sdp.login.FirebaseAuthentication;

public class FriendsListActivity extends AppCompatActivity implements WaitsOnFriendFetch {
    private ChatRepository chatRepo;

    // To get the user info
    private static String current_email_id= "stupid1@gmail.com";

    // This should be called from the Authentication class's signIn/signUp success callback
    public static void setChatEmailID(String email_id)
    {
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

        current_email_id= "stupid1@gmail.com";
        chatRepo = ChatRepository.createRepo(this);
        chatRepo.setContextActivity(this);
        ChatActivity.setRemoteToSQLiteAdapter(new FireStoreToSQLiteAdapter().getInstance()); // placed here for mock testing purposes
        //auth = new FirebaseAuthentication(null);
        //auth.getClass()
        //user_cur =  new User(FirebaseAuth.getInstance().getCurrentUser().getEmail(), ,
        //usr_amr = new User("amro.abdrabo@gmail.com", "amro", "abdo");

        //try {
            //chatRepo.addUser(new User("abd1@g"));
            //chatRepo.addUser(new User("abd1@g"));
        //}
        //catch (SQLiteConstraintException e)
       // {
          //  Log.d("loloo", e.getMessage() + " fuckkkkkk");
           // if (e.getMessage().contains("UNIQUE"))
            //{
             //   Log.d("lolz", " got fucked by unique");
            //}
        //}

        //usr_shaima = new User("shaima@abc.com", "shaima", "hhhhh");
        try {
            Log.d("The current enmail ISSS", current_email_id);
            //chatRepo.addUser(usr_amr);
            //chatRepo.addUser(usr_shaima);
            //chatRepo.addFriends(usr_amr, usr_shaima);
            chatRepo.fetchFriends(new User(current_email_id));
        }
        catch(Exception e){
            System.out.println("NOOOO" +e.getMessage());
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
            ChatActivity.setChattingWith(friends.get(position).getEmail());
            Intent intent = new Intent(FriendsListActivity.this , ChatActivity.class);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void friendsFetched(List<User> friends) {
        initRecyclerView(friends);
        Log.d("RESUMED ", "size   " + friends.size());
    }

    public void onAddFriendClicked(View v)
    {
        startActivity(new Intent(FriendsListActivity.this, AddFriendsActivity.class));
    }
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(User friend);
    }
}
