package ch.epfl.sdp.social.RemoteUsers;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ch.epfl.sdp.utils.WaitsOn;
import ch.epfl.sdp.database.social.User;

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
