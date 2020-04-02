package ch.epfl.sdp.social;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import ch.epfl.sdp.R;

public class AddFriendsActivity extends AppCompatActivity {

    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        rv = findViewById(R.id.recyclerQueryFriends);
    }
}
