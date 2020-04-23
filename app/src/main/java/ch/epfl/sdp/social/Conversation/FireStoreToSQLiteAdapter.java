package ch.epfl.sdp.social.Conversation;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import ch.epfl.sdp.social.socialDatabase.*;
import ch.epfl.sdp.social.*;
import java.util.List;
import java.util.Map;

/*
 This class fetches messages from FireStore and places them in the local SQLite database.
 As usual, singleton design.
 */
public class FireStoreToSQLiteAdapter implements RemoteToSQLiteAdapter {

    private static final FirebaseFirestore remoteHost = FirebaseFirestore.getInstance();
    private static Context listener;
    private final static List<String> PATH_SEGMENTS = Arrays.asList("Users", "Texts", "Texts", "read");
    private static FireStoreToSQLiteAdapter singleton = new FireStoreToSQLiteAdapter();


    public static FireStoreToSQLiteAdapter getInstance() { return singleton; }
    public void setListener(Context listener) {
        singleton.listener = listener;
    }

    public void sendRemoteServerDataToLocal(String owner, String sender, int chat_id) {

        List<Message> remoteMessages = new ArrayList<>();

        // Get all unread messages
        remoteHost.collection(
                PATH_SEGMENTS.get(0)).
                document(owner).collection(PATH_SEGMENTS.get(1)).
                document(sender).collection(PATH_SEGMENTS.get(2)).
                whereEqualTo(PATH_SEGMENTS.get(3), false).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    QuerySnapshot result = task.getResult();
                    for (QueryDocumentSnapshot doc: result) {
                        remoteMessages.add(new Message(((Timestamp)doc.getData().get("date")).toDate(),
                                                        (String)doc.getData().get("content"),
                                                        chat_id));
                        remoteHost.collection(
                                 PATH_SEGMENTS.get(0))
                                .document(owner).collection(PATH_SEGMENTS.get(1))
                                .document(sender).collection(PATH_SEGMENTS.get(2))
                                .document(doc.getId()).update(PATH_SEGMENTS.get(3), true);
                    }
                    ((WaitsOnWithServer<Message>)listener).contentFetchedWithServer(remoteMessages, true);
                }
            }
        });
    }

    // Sends outgoing messages to User "to"'s message inbox
    public void sendLocalDataToRemoteServer(String current_usr, String to, Message m) {

        Map<String, Object> data = new HashMap<>();
        data.put("content", m.getText());
        data.put("date", new Timestamp(new Date()));
        remoteHost.collection(PATH_SEGMENTS.get(0)).
                document(to).collection(PATH_SEGMENTS.get(1)).
                document(current_usr).collection(PATH_SEGMENTS.get(2)).
                document().set(data);
    }
}