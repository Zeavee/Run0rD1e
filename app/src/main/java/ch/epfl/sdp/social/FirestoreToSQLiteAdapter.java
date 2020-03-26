package ch.epfl.sdp.social;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class FirestoreToSQLiteAdapter {

    private static final FirebaseFirestore remoteHost = FirebaseFirestore.getInstance();


    // owner is "shaima@abc.com" and sender is "amro@abc.com"
    public static void fetchFirestoreDataIntoSQLite(String owner, String sender)
    {
        Map<String, Object> hm;
        remoteHost.collection("Chats").document(owner).collection(sender).document("Messages").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ChatActivity.getChatRepo().insertMessageFromRemote(task.getResult().getData());
                //System.out.println("PLEASE WORK!! " +task.getResult().getData());
            }
        });
    }
}