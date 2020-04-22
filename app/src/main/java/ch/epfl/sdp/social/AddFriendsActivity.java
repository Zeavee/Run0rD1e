package ch.epfl.sdp.social;

import android.os.Bundle;
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

    private static RecyclerView recyclerView;
    public static ArrayList<RecyclerQueryAdapter> container = new ArrayList<>(); // for mock testing also
    private static RecyclerQueryAdapter cached_adapter = null;
    private static RemoteFriendFetcher server;

    // Used for mocking (to be called before being created inside)
    public static void setAdapter(RecyclerQueryAdapter newAdapter) {
        cached_adapter = newAdapter;
        container.clear();
        container.add(newAdapter);
    }

    public static void setServer(RemoteFriendFetcher server) {
        AddFriendsActivity.server = server;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);


        recyclerView = findViewById(R.id.recyclerQueryFriends);
        
        // when it comes alive, it will use this adapter
        useCachedAdapter();
        container.clear();
        container.add(cached_adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        
    }
    
    private void useCachedAdapter()
    {
        recyclerView.setAdapter(cached_adapter);
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
                server.getFriendsFromServer(newText, container.get(0));
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


}
