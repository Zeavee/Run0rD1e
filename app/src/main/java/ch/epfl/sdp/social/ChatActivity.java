package ch.epfl.sdp.social;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

import ch.epfl.sdp.R;

public class ChatActivity extends AppCompatActivity implements WaitOnChatRetrieval {

    private ImageButton sendButton;
    private EditText message;
    private static ChatRepository chatRepo;
    private static String chattingWith;
    private List<Chat> chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sendButton = findViewById(R.id.sendMessageButton);
        message = findViewById(R.id.messageField);

        chatRepo = new ChatRepository(this);
        chatRepo.getChat("amro.abdrabo@gmail.com", chattingWith);
    }
    public void  onSendClicked(View v)
    {
        chatRepo.addChat(new Chat());
    }
    public static ChatRepository getChatRepo()
    {
        return chatRepo;
    }

    public static void setChattingWith(String chattingWith) {
        ChatActivity.chattingWith = chattingWith;
    }

    @Override
    public void chatFetched(List<Chat> chat) {
        this.chat = chat;
    }
}
