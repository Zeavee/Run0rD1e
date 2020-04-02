package ch.epfl.sdp.social;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 This class fetches messages from FireStore and places them in the local SQLite database.
 */
public class FireStoreToSQLiteAdapter {

    private static final FirebaseFirestore remoteHost = FirebaseFirestore.getInstance();

    private static Context listener;

    public static void setListener(Context listener) {
        FireStoreToSQLiteAdapter.listener = listener;
    }

    // Gets the incoming messages from FireStore. Owner is "stupid1@gmail.com" i.e the currently signed in user
    public static void sendFireStoreDataToLocal(String owner, String sender)
    {
        List<Message> remoteMessages = new ArrayList<>();

        // Get all unread messages
        remoteHost.collection("Users").document(owner).collection("Texts").document(sender).collection("Texts").
                whereEqualTo("read", false).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    //Log.d("for owner ", owner);
                    QuerySnapshot result = task.getResult();
                    for (QueryDocumentSnapshot doc: result) {
                        remoteMessages.add(new Message(((Timestamp)doc.getData().get("date")).toDate(), (String)doc.getData().get("content")));
                    }
                    ((WaitsOnMessageFetch)listener).incomingMessageFetchFinished(remoteMessages);
                }
            }
        });
        // TODO: deletion for a later week (doesn't affect demo since app starts a new)

    }

    // Sends outgoing messages to User "to"'s message inbox
    public static void sendLocalDataToFireStore(String current_usr, String to, Message m)
    {
        Map<String, Object> data = new HashMap<>();
        data.put("content", m.getText());
        data.put("date", new Timestamp(new Date()));
        remoteHost.collection("Users").document(to).collection("Texts").document(current_usr).collection("Texts").document().set(data);
    }
}