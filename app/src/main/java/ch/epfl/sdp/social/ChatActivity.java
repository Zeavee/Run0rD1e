package ch.epfl.sdp.social;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.List;

import ch.epfl.sdp.R;

public class ChatActivity extends AppCompatActivity implements WaitOnChatRetrieval, AsyncResponse {

    private ImageButton sendButton;
    private EditText message;
    private ListView lv;
    private static ChatRepository chatRepo;
    private static String chattingWith;
    private Chat chat;
    private List<String> messages;
    private MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sendButton = findViewById(R.id.sendMessageButton);
        message = findViewById(R.id.messageField);
        lv = findViewById(R.id.messages_view);

        messageAdapter = new MessageAdapter(this);
        lv.setAdapter(messageAdapter);

        chatRepo = ChatRepository.createRepo(this);
        chatRepo.setContextActivity(this);
        chatRepo.getChat("amro.abdrabo@gmail.com", chattingWith);
        sendButton.setOnClickListener(this::onSendClicked);
        loadExistingMessages();
    }
    public void loadExistingMessages()
    {
        chatRepo.getMessages("amro.abdrabo@gmail.com", "shaima@abc.com");
    }
    public void  onSendClicked(View v)
    {
        messageAdapter.add(new Message(new Date(), message.getText().toString()));
        chatRepo.sendMessage(message.getText().toString(), this.chat.getChat_id());
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
            System.out.println("effff4839859000000038ffffffff");
            c.setFrom("amro.abdrabo@gmail.com");
            c.setTo("shaima@abc.com");
            chatRepo.addChat(c);
            this.chat = c;
            return;
        }
        this.chat = chat.get(0);
    }

    @Override
    public void messageFetchFinished(List<String> output) {
        messages = output;
        for (String el: messages)
        {
            messageAdapter.add(new Message(new Date(), el));
        }
    }
}
