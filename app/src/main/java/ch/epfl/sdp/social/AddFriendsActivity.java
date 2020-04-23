package ch.epfl.sdp.social;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ch.epfl.sdp.R;
import ch.epfl.sdp.social.RemoteUsers.RemoteFriendFetcher;

public class AddFriendsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerQueryAdapter cached_adapter;
    private static RemoteFriendFetcher server;

    public static void setServer(RemoteFriendFetcher server) {
        AddFriendsActivity.server = server;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        
        recyclerView = findViewById(R.id.recyclerQueryFriends);
        
        cached_adapter = new RecyclerQueryAdapter();
        recyclerView.setAdapter(cached_adapter);
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
                server.getFriendsFromServer(newText, cached_adapter);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


}
