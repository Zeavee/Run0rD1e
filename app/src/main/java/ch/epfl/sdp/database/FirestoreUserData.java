package ch.epfl.sdp.database;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import ch.epfl.sdp.artificial_intelligence.Updatable;
import ch.epfl.sdp.db.PlayerEntity;
import ch.epfl.sdp.db.AppViewModel;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.map.MapsActivity;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FirestoreUserData implements UserDataController, Updatable {
    @Override
    public void syncCloudFirebaseToRoom(AppViewModel appViewModel) {
        FirebaseFirestore.getInstance().collection("Users")
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
        FirebaseFirestore.getInstance().collection(collectionName).document(player.getEmail()).set(player, SetOptions.merge());
    }

    @Override
    public void getLobby(String collectionName) {
        FirebaseFirestore.getInstance().collection(collectionName)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Player player = document.toObject(Player.class);
                        MapsActivity.playerManager.addPlayer(player);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            });
    }

    private long lastUpdateTime = 0;
    @Override
    public void update() {
        if(lastUpdateTime == 0) {
            lastUpdateTime = System.currentTimeMillis();
        }

        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdateTime >= 1000) {
            lastUpdateTime = currentTime;
            getLobby("Users");
        }
    }
}
