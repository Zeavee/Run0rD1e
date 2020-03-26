package ch.epfl.sdp.social;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

import ch.epfl.sdp.R;

public class ChatActivity extends AppCompatActivity implements WaitOnChatRetrieval {

    private ImageButton sendButton;
    private EditText message;
    private ListView lv;
    private static ChatRepository chatRepo;
    private static String chattingWith;
    private Chat chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sendButton = findViewById(R.id.sendMessageButton);
        message = findViewById(R.id.messageField);
        lv = findViewById(R.id.messages_view);

        chatRepo = new ChatRepository(this);
        chatRepo.getChat("amro.abdrabo@gmail.com", chattingWith);
    }
    public void  onSendClicked(View v)
    {
        // * TODO MAKE AN ITEM ON THE LIST HAVE TEXT FROM MESSAGE EDIT TEXT*
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
        if (chat == null || chat.isEmpty())
        {
            Chat c = new Chat();
            c.setFrom("amro.abdrabo@gmail.com");
            c.setTo("shaima@abc.com");
            chatRepo.addChat(c);
            this.chat = c;
            return;
        }
        this.chat = chat.get(0);
    }
}
