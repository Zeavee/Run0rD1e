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
import ch.epfl.sdp.dependencies.DependencyProvider;

public class ChatActivity extends AppCompatActivity implements WaitsOnWithServer<Message> {

    private ImageButton sendButton;
    private EditText message;
    private ListView lv;
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

        ChatRepository.setContextActivity(this);

        chat = ChatRepository.getInstance().getChat("stupid1@gmail.com", chattingWith);
        sendButton.setOnClickListener(this::onSendClicked);

        // Very important to use the singleton to reduce thinking (Done before activity switch inside friendsListActivity)
        // setRemoteToSQLiteAdapter(new FireStoreToSQLiteAdapter().getInstance()); done in friendsListActivity
        loadExistingMessages();
    }
    private void loadExistingMessages()
    {
        ChatRepository chatRepo = ChatRepository.getInstance();
        chatRepo.getMessagesReceived("stupid1@gmail.com", chattingWith);
        chatRepo.getMessagesSent("stupid1@gmail.com", chattingWith);
        FireStoreToSQLiteAdapter.getInstance().setListener(this);
        FireStoreToSQLiteAdapter.getInstance().sendRemoteServerDataToLocal("stupid1@gmail.com", chattingWith, chat.chat_id);
    }
    private void  onSendClicked(View v)
    {
        Message m = new Message(new Date(), message.getText().toString(), chat.chat_id);
        messageAdapter.add(new MessageDecorator(m, false));
        ChatRepository chatRepo = ChatRepository.getInstance();
        chatRepo.storeMessage(message.getText().toString(), chatRepo.getChat(chat.to, chat.from).getChat_id());

        // TODO: clean way to get email of user
        DependencyProvider.remoteToSQLiteAdapter.sendLocalDataToRemoteServer("stupid1@gmail.com",chattingWith,m);
    }

    public static void setChattingWith(String chattingWith) {
        ChatActivity.chattingWith = chattingWith;
    }


    static final class MessageDecorator{
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
    public void contentFetchedWithServer(List<Message> output, boolean isFromServer) {
        messages = output;
        for (Message el: messages)
        {
            // TODO: Add the messages to SQLite and delete them from FireStore
            if (isFromServer) {
                ChatRepository.getInstance().insertMessageFromRemote(new Timestamp(el.getDate()), el.getText(), chat.chat_id);
            }
            messageAdapter.add(new MessageDecorator(el, true));
        }
    }

    @Override
    public void contentFetched(List<Message> output) {
        messages = output;
        for (Message el: messages)
        {
            messageAdapter.add(new MessageDecorator(el, false));
        }
    }
}
