package ch.epfl.sdp.social.Conversation;

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
import ch.epfl.sdp.social.*;
import ch.epfl.sdp.dependencies.DependencyProvider;
import ch.epfl.sdp.social.socialDatabase.Message;
import ch.epfl.sdp.social.socialDatabase.Chat;
/**
 * @brief this activity shows the conversation of the current user and another user
 */
public class ChatActivity extends AppCompatActivity implements WaitsOnWithServer<Message> {

    private ImageButton sendButton;
    private EditText message;
    private ListView lv;
    private String chattingWith;
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

        chattingWith = getIntent().getStringExtra("chattingWith");

        messageAdapter = new MessageAdapter(this, chattingWith);
        lv.setAdapter(messageAdapter);

        SocialRepository.setContextActivity(this);
        chat = SocialRepository.getInstance().getChat(DependencyProvider.email, chattingWith);

        sendButton.setOnClickListener(this::onSendClicked);
        loadExistingMessages();
    }

    private void loadExistingMessages()
    {
        SocialRepository chatRepo = SocialRepository.getInstance();
        chatRepo.getMessagesReceived(DependencyProvider.email, chattingWith);
        chatRepo.getMessagesSent(DependencyProvider.email, chattingWith);
        FireStoreToSQLiteAdapter.getInstance().setListener(this);
        FireStoreToSQLiteAdapter.getInstance().sendRemoteServerDataToLocal(DependencyProvider.email, chattingWith, chat.chat_id);
    }

    private void  onSendClicked(View v)
    {
        Message m = new Message(new Date(), message.getText().toString(), chat.chat_id);
        messageAdapter.add(new MessageDecorator(m, false));
        SocialRepository chatRepo = SocialRepository.getInstance();
        chatRepo.storeMessage(message.getText().toString(), chatRepo.getChat(chat.to, chat.from).getChat_id());
        DependencyProvider.remoteToSQLiteAdapter.sendLocalDataToRemoteServer(DependencyProvider.email,chattingWith,m);
    }


    /**
     * @brief decorates a message with a flag that indicates whether the message was incoming or outgoing
     */
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
                SocialRepository.getInstance().insertMessageFromRemote(new Timestamp(el.getDate()), el.getText(), chat.chat_id);
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
