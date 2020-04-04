package ch.epfl.sdp.social;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;

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

    public static void setRemoteToSQLiteAdapter(RemoteToSQLiteAdapter remoteToSQLiteAdapter) {
        ChatActivity.remoteToSQLiteAdapter = remoteToSQLiteAdapter;
    }

    private static RemoteToSQLiteAdapter remoteToSQLiteAdapter;
    
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

        chat = chatRepo.getChat("stupid1@gmail.com", chattingWith);
        sendButton.setOnClickListener(this::onSendClicked);

        // Very important to use the singleton to reduce thinking (Done before activity switch inside friendsListActivity)
        // setRemoteToSQLiteAdapter(new FireStoreToSQLiteAdapter().getInstance()); done in friendsListActivity
        loadExistingMessages();
        messageAdapter.sortMessages();
    }
    public void loadExistingMessages()
    {
        chatRepo.getMessagesReceived("stupid1@gmail.com", chattingWith);
        chatRepo.getMessagesSent("stupid1@gmail.com", chattingWith);
        remoteToSQLiteAdapter.getInstance().setListener(this);
        remoteToSQLiteAdapter.getInstance().sendRemoteServerDataToLocal("stupid1@gmail.com", chattingWith, chat.chat_id);
    }
    public void  onSendClicked(View v)
    {
        Message m = new Message(new Date(), message.getText().toString(), chat.chat_id);
        messageAdapter.add(new MessageDecorator(m, false));
        chatRepo.sendMessage(message.getText().toString(), chatRepo.getChatDirectly(chat.to, chat.from).getChat_id());

        // TODO: clean way to get email of user
        remoteToSQLiteAdapter.getInstance().sendLocalDataToRemoteServer("stupid1@gmail.com",chattingWith,m);
    }

    public static void setChattingWith(String chattingWith) {
        ChatActivity.chattingWith = chattingWith;
    }

    @Override
    public void chatFetched(List<Chat> chat) {
        this.chat = chat.get(0);
    }

    public static final class MessageDecorator{
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
    public void incomingMessageFetchFinished(List<Message> output, boolean isFromServer) {
        messages = output;
        for (Message el: messages)
        {
            // TODO: Add the messages to SQLite and delete them from FireStore
            if (isFromServer) {
                chatRepo.insertMessageFromRemote(new Timestamp(el.getDate()), el.getText(), chat.chat_id);
            }
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
