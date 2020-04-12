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
import java.util.List;

import ch.epfl.sdp.R;
import ch.epfl.sdp.social.friends_firestore.WaitsOnUserFetch;

public class AddFriendsActivity extends AppCompatActivity {


    private static RecyclerView recyclerView;
    public static ArrayList<RecyclerQueryAdapter> container = new ArrayList<>(); // for mock testing also
    private static RecyclerQueryAdapter cached_adapter = null;

    // Used for mocking (to be called before being created inside)
    public static void setAdapter(RecyclerQueryAdapter newAdapter) {
        if (newAdapter == null) {
            Log.d("HOOWWWW", "DAFUQQQQQQQQ");
        }
        cached_adapter = newAdapter;
        //recyclerView.setAdapter(newAdapter);
        container.clear();
        container.add(newAdapter);
    }

    private static RecyclerQueryAdapter adapter;
    private List<User> friends_to_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);


        recyclerView = findViewById(R.id.recyclerQueryFriends);
        //friends_to_add = new ArrayList<>();

        // This can be mocked by doing a setAdapter method that injects  new RecyclerQueryAdapter(friends_to_add, new MockFriendsFetcher());
        // inside @Before
        // adapter = new RecyclerQueryAdapter(friends_to_add, new FirestoreFriendsFetcher()); // not compatible with testing


        // layoutManager of recyclerView implemented in the xml file
        // recyclerView.setAdapter(adapter);

        // when it comes alive, it will use this adapter
        useCachedAdapter();
        container.clear();
        container.add(cached_adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

    }

    private void useCachedAdapter() {
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
                ((WaitsOnUserFetch) container.get(0)).updateSearch(newText);
                //adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


}
