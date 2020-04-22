package ch.epfl.sdp.social.RemoteUsers;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.social.socialDatabase.User;
import ch.epfl.sdp.social.WaitsOn;

public class FriendsRepositery implements RemoteFriendFetcher {

    @Override
    public void getFriendsFromServer(String constraint, WaitsOn<User> waiter) {
        if (constraint == null) return;
        List<User> filtered = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DocumentSnapshot> docs = task.getResult().getDocuments();
                for (DocumentSnapshot doc : docs) {
                    if (doc.getId().contains(constraint)) {
                        filtered.add(new User(doc.getId(), (String) doc.getData().get("username")));
                    }
                }
                waiter.contentFetched(filtered);

            }
        });

    }
}
