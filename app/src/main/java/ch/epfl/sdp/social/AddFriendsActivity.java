package ch.epfl.sdp.social;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.R;
import ch.epfl.sdp.social.friends_firestore.FirestoreFriendsFetcher;
import ch.epfl.sdp.social.friends_firestore.WaitsOnUserFetch;

public class AddFriendsActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerQueryAdapter adapter;
    private List<User> friends_to_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        
        
        recyclerView = findViewById(R.id.recyclerQueryFriends);
        friends_to_add = new ArrayList<>();
        adapter = new RecyclerQueryAdapter(friends_to_add, new FirestoreFriendsFetcher());


        // layoutManager of recyclerView implemented in the xml file
        
        recyclerView.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_addfriend, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // perform firestore search here
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((WaitsOnUserFetch)adapter).updateSearch(newText);
                //adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


}
