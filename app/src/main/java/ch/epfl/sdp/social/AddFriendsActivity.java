package ch.epfl.sdp.social;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import ch.epfl.sdp.R;
import ch.epfl.sdp.dependencies.AppContainer;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.social.RemoteUsers.RemoteFriendFetcher;

public class AddFriendsActivity extends AppCompatActivity {

    private RecyclerQueryAdapter cached_adapter;
    private RemoteFriendFetcher server;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        server = appContainer.remoteUserFetch;

        RecyclerView recyclerView = findViewById(R.id.recyclerQueryFriends);

        cached_adapter = new RecyclerQueryAdapter(appContainer.authenticationAPI.getCurrentUserEmail());
        recyclerView.setAdapter(cached_adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

        findViewById(R.id.addFriendsBackButton).setOnClickListener((v) -> startActivity(new Intent(AddFriendsActivity.this, FriendsListActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_addfriend, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // perform FireStore search for users here
            @Override
            public boolean onQueryTextChange(String newText) {
                server.getFriendsFromServer(newText, cached_adapter);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


}
