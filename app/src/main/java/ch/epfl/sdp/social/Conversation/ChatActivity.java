package ch.epfl.sdp.social.Conversation;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.epfl.sdp.R;
import ch.epfl.sdp.dependencies.MyApplication;
import ch.epfl.sdp.social.WaitsOnWithServer;
import ch.epfl.sdp.social.socialDatabase.Chat;
import ch.epfl.sdp.social.socialDatabase.Message;

/**
 * @brief this activity shows the conversation of the current user and another user
 */
public class ChatActivity extends AppCompatActivity implements WaitsOnWithServer<Message> {

    private ImageButton sendButton;
    private EditText message;
    private ListView lv;
    private String chattingWith;
    private Chat chatFromCurrent;
    private Chat chatFromFriend;
    private List<Message> messages;
    private MessageAdapter messageAdapter;
    private RemoteToSQLiteAdapter sqliteFirestoreInterface;
    private String currentEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sendButton = findViewById(R.id.sendMessageButton);
        message = findViewById(R.id.messageField);
        lv = findViewById(R.id.messages_view);

        chattingWith = getIntent().getStringExtra("chattingWith");
        if (chattingWith == null) {
            // instrumentation test running so initialize to sentinel value "null_0"
            chattingWith = "null_0";
        }

        messageAdapter = new MessageAdapter(this, chattingWith);
        lv.setAdapter(messageAdapter);

        currentEmail = ((MyApplication) getApplication()).appContainer.authenticationAPI.getCurrentUserEmail();

        SocialRepository.setContextActivity(this);

        // The current user is the sender
        chatFromCurrent = SocialRepository.getInstance().getChat(currentEmail, chattingWith);

        // The current user is the receiver
        chatFromFriend = SocialRepository.getInstance().getChat(chattingWith, currentEmail);

        sendButton.setOnClickListener(v -> onSendClicked(v));
        sqliteFirestoreInterface = ((MyApplication) getApplication()).appContainer.remoteToSQLiteAdapter;
        loadExistingMessages();
    }

    private void loadExistingMessages() {
        SocialRepository chatRepo = SocialRepository.getInstance();
        chatRepo.getMessagesExchanged(currentEmail, chattingWith);
        chatRepo.getMessagesExchanged(chattingWith, currentEmail);
        sqliteFirestoreInterface.setListener(this);
        sqliteFirestoreInterface.sendRemoteServerDataToLocal(currentEmail, chattingWith, chatFromFriend.getChat_id());
    }

    /**
     * This method tells what do do when the send button is clicked
     * It sends the message the user has written
     *
     * @param v the view on which we clicked
     */
    public void onSendClicked(View v) {
        Message m = new Message(new Date(), message.getText().toString(), chatFromCurrent.getChat_id());
        messageAdapter.add(new MessageDecorator(m, false));
        SocialRepository chatRepo = SocialRepository.getInstance();
        chatRepo.storeMessage(m);
        sqliteFirestoreInterface.sendLocalDataToRemoteServer(currentEmail, chattingWith, m);
    }

    /**
     * @brief decorates a message with a flag that indicates whether the message was incoming or outgoing
     */
    static final class MessageDecorator {
        private Message m;
        private boolean incoming;

        public MessageDecorator(Message m, boolean incoming) {
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
    public void contentFetchedWithServer(List<Message> output, boolean isFromServer, boolean incoming) {
        messages = output;
        for (Message el : messages) {
            if (isFromServer) {
                SocialRepository.getInstance().insertMessageFromRemote(new Timestamp(el.getDate()), el.getText(), chatFromFriend.getChat_id());
            }
            messageAdapter.add(new MessageDecorator(el, incoming));
        }
    }


    /**
     * needed for testing, returns the list of messages
     */
    public List<Message> getMessages() {
        // return defensive copy
        return new ArrayList<>(messages);
    }
}
