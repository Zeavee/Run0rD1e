package ch.epfl.sdp.social.RemoteUsers;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ch.epfl.sdp.social.WaitsOn;
import ch.epfl.sdp.social.socialDatabase.User;

/**
 * This class permits to get the friends list of the user
 */
public class FriendsRepository implements RemoteFriendFetcher {
    private static final String USERS_PATH = "AllUsers";

    @Override
    public void getFriendsFromServer(String constraint, WaitsOn<User> waiter) {
        if (constraint == null) return;
        List<User> filtered = new ArrayList<>();
        FirebaseFirestore.getInstance().collection(USERS_PATH).get().addOnCompleteListener(task -> {
            List<DocumentSnapshot> docs = Objects.requireNonNull(task.getResult()).getDocuments();
            for (DocumentSnapshot doc : docs) {
                if (doc.getId().contains(constraint)) {
                    filtered.add(new User(doc.getId(), (String) Objects.requireNonNull(doc.getData()).get("username")));
                }
            }
            waiter.contentFetched(filtered);

        });

    }
}
