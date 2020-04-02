package ch.epfl.sdp.social;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import ch.epfl.sdp.R;

public class AddFriendsActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerQueryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        recyclerView = findViewById(R.id.recyclerQueryFriends);
        adapter = new RecyclerQueryAdapter();

        // layoutManager of recyclerView implemented in the xml file
        recyclerView.setAdapter(adapter);


    }
}
