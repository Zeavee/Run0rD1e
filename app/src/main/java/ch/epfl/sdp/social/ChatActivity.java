package ch.epfl.sdp.social;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ch.epfl.sdp.R;

public class ChatActivity extends AppCompatActivity {

    private static ChatRepository chatRepo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatRepo = new ChatRepository(this);
    }

    public static ChatRepository getChatRepo()
    {
        return chatRepo;
    }
}
