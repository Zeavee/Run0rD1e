package ch.epfl.sdp;

import android.app.Activity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class FirestoreUserData implements UserDataController {
    @Override
    public void loadUsersForLeaderboard(Activity activity, SetupLeaderboard setupLeaderboard, List<UserForFirebase> mUserForFirebases) {
        FirebaseFirestore.getInstance().collection("Users")
                .orderBy("healthPoints", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        UserForFirebase userForFirebase = documentSnapshot.toObject(UserForFirebase.class);
                        if (!mUserForFirebases.contains(userForFirebase))
                            mUserForFirebases.add(userForFirebase);
                    }
                    setupLeaderboard.setupLeaderboardView(activity, mUserForFirebases);
                    setupLeaderboard.setupChampions(activity, mUserForFirebases);
                });
    }

//    @Override
//    public void storeUser(Player player) {
//        FirebaseFirestore.getInstance().collection("Users").document(player.getEmail()).set(player);
//    }

    @Override
    public void storeUser(UserForFirebase userForFirebase) {
        FirebaseFirestore.getInstance().collection("Users").document(userForFirebase.getEmail()).set(userForFirebase);
    }
}
