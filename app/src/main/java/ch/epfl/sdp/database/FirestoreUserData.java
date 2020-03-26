package ch.epfl.sdp.database;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import ch.epfl.sdp.db.LeaderboardEntity;
import ch.epfl.sdp.db.LeaderoardViewModel;
import ch.epfl.sdp.entity.Player;

public class FirestoreUserData implements UserDataController {
    @Override
    public void syncCloudFirebaseToRoom(LeaderoardViewModel leaderoardViewModel) {
        FirebaseFirestore.getInstance().collection("Users")
            .orderBy("healthPoints", Query.Direction.DESCENDING)
            .addSnapshotListener((queryDocumentSnapshots, e) -> {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Player player = documentSnapshot.toObject(Player.class);
                    LeaderboardEntity user = new LeaderboardEntity(player.getEmail(), player.getUsername(), player.getScore());
                    leaderoardViewModel.insert(user);
                }
            });
    }

    @Override
    public void storeUser(Player player) {
        FirebaseFirestore.getInstance().collection("Users").document(player.getEmail()).set(player);
    }
}
