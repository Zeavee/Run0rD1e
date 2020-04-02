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

public class ChatActivity extends AppCompatActivity implements WaitOnChatRetrieval, WaitsOnMessageFetch {

    private ImageButton sendButton;
    private EditText message;
    private ListView lv;
    private static ChatRepository chatRepo;
    private static String chattingWith;
    private Chat chat;
    private List<Message> messages;
    private MessageAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sendButton = findViewById(R.id.sendMessageButton);
        message = findViewById(R.id.messageField);
        lv = findViewById(R.id.messages_view);

        messageAdapter = new MessageAdapter(this, chattingWith);
        lv.setAdapter(messageAdapter);

        chatRepo = ChatRepository.createRepo(this);
        chatRepo.setContextActivity(this);
        //chatRepo.addChat(new Chat("amro.abdrabo@gmail.com", chattingWith));
        //chatRepo.addChat(new Chat(chattingWith, "amro.abdrabo@gmail.com"));

        chatRepo.getChat("stupid1@gmail.com", chattingWith);
        sendButton.setOnClickListener(this::onSendClicked);
        loadExistingMessages();
    }
    public void loadExistingMessages()
    {
        FireStoreToSQLiteAdapter.setListener(this);
        FireStoreToSQLiteAdapter.sendFireStoreDataToLocal("stupid1@gmail.com", chattingWith);
        chatRepo.getMessagesReceived("stupid1@gmail.com", chattingWith);
        chatRepo.getMessagesSent("stupid1@gmail.com", chattingWith);
    }
    public void  onSendClicked(View v)
    {
        Message m = new Message(new Date(), message.getText().toString());
        messageAdapter.add(new MessageDecorator(m, false));
        chatRepo.sendMessage(message.getText().toString(), this.chat.getChat_id());
        // TODO: clean way to get email of user
        FireStoreToSQLiteAdapter.sendLocalDataToFireStore("stupid1@gmail.com",chattingWith,m);
    }
   /* public static ChatRepository getChatRepo()
    {
         return chatRepo;
    }*/

    public static void setChattingWith(String chattingWith) {
        ChatActivity.chattingWith = chattingWith;
    }

    @Override
    public void chatFetched(List<Chat> chat) {
        // this if will be unnecessary since the chat is added when the user adds a friend
        if (chat == null || chat.isEmpty())
        {
            Chat c = new Chat("stupid1@gmail.com", chattingWith);
            System.out.println("effff4839859000000038ffffffff");
            //c.setFrom("amro.abdrabo@gmail.com");
            //c.setTo("shaima@abc.com");
            chatRepo.addChat(c);
            this.chat = c;
            return;
        }
        this.chat = chat.get(0);
    }

    public static class MessageDecorator{
        private Message m;
        private boolean incoming;
        public MessageDecorator(Message m, boolean incoming)
        {
            this.m = m;
            this.incoming = incoming;
        }

        public Message getM() {
            return m;
        }

        public boolean isIncoming() {
            return incoming;
        }
    }
    @Override
    public void incomingMessageFetchFinished(List<Message> output) {
        messages = output;
        for (Message el: messages)
        {
            messageAdapter.add(new MessageDecorator(el, true));
        }
    }

    @Override
    public void outgoingMessageFetchFinished(List<Message> output) {
        messages = output;
        for (Message el: messages)
        {
            messageAdapter.add(new MessageDecorator(el, false));
        }
    }
}
