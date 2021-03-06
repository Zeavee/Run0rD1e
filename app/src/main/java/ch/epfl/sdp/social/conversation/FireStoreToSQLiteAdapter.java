package ch.epfl.sdp.social.conversation;

import android.content.Context;
import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ch.epfl.sdp.entities.player.PlayerManager;
import ch.epfl.sdp.utils.WaitsOnWithServer;
import ch.epfl.sdp.database.room.social.Message;

/*
 This class fetches messages from FireStore and places them in the local SQLite database.
 As usual, singleton design.
 */
public class FireStoreToSQLiteAdapter implements RemoteToSQLiteAdapter {

    private final FirebaseFirestore remoteHost = FirebaseFirestore.getInstance();
    private Context listener;
    private final List<String> PATH_SEGMENTS = Arrays.asList(PlayerManager.USER_COLLECTION_NAME, "Texts", "Texts", "read");

    public void setListener(Context listener) {
        this.listener = listener;
    }

    @Override
    public void sendRemoteServerDataToLocal(String owner, String sender, int chat_id) {

        List<Message> remoteMessages = new ArrayList<>();

        // Get all unread messages
        remoteHost.collection(
                PATH_SEGMENTS.get(0)).
                document(owner).collection(PATH_SEGMENTS.get(1)).
                document(sender).collection(PATH_SEGMENTS.get(2)).
                whereEqualTo(PATH_SEGMENTS.get(3), false).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot result = task.getResult();
                        for (QueryDocumentSnapshot doc : Objects.requireNonNull(result)) {
                            remoteMessages.add(new Message(((Timestamp) doc.getData().get("date")).toDate(),
                                    (String) doc.getData().get("content"),
                                    chat_id));
                            remoteHost.collection(
                                    PATH_SEGMENTS.get(0))
                                    .document(owner).collection(PATH_SEGMENTS.get(1))
                                    .document(sender).collection(PATH_SEGMENTS.get(2))
                                    .document(doc.getId()).update(PATH_SEGMENTS.get(3), true);
                        }
                        ((WaitsOnWithServer<Message>) listener).contentFetchedWithServer(remoteMessages, true, true);
                    }
                });
    }

    // Sends outgoing messages to User "to"'s message inbox
    @Override
    public void sendLocalDataToRemoteServer(String current_usr, String to, Message m) {
        Map<String, Object> data = new HashMap<>();
        data.put("content", m.getText());
        data.put("date", new Timestamp(new Date()));
        data.put("read", false);
        remoteHost.collection(PATH_SEGMENTS.get(0)).
                document(to).collection(PATH_SEGMENTS.get(1)).
                document(current_usr).collection(PATH_SEGMENTS.get(2)).
                document().set(data);
    }
}