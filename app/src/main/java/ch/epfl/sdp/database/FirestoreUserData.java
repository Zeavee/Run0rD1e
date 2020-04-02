package ch.epfl.sdp.database;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import ch.epfl.sdp.db.AppViewModel;
import ch.epfl.sdp.db.PlayerEntity;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FirestoreUserData implements UserDataController {
    @Override
    public void syncCloudFirebaseToRoom(AppViewModel appViewModel, String collectionName) {
        FirebaseFirestore.getInstance().collection(collectionName)
            .orderBy("healthPoints", Query.Direction.DESCENDING)
            .addSnapshotListener((queryDocumentSnapshots, e) -> {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Player player = documentSnapshot.toObject(Player.class);
                    PlayerEntity playerEntity = new PlayerEntity(player.getEmail(), player.getUsername(), player.getHealthPoints());
                    appViewModel.insertToLeaderboard(playerEntity);
                }
            });
    }

    @Override
    public void storeUser(String collectionName, Player player) {
        FirebaseFirestore.getInstance().collection(collectionName).document(player.getEmail()).set(player);
    }

    @Override
    public void getLobby(String collectionName) {
        FirebaseFirestore.getInstance().collection(collectionName)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Player player = document.toObject(Player.class);
                        PlayerManager.getInstance().addPlayer(player);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            });
    }

}
