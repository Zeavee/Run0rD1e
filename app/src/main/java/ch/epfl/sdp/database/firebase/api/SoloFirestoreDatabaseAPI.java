package ch.epfl.sdp.database.firebase.api;

import com.google.firebase.firestore.FirebaseFirestore;
import ch.epfl.sdp.entity.PlayerManager;

public class SoloFirestoreDatabaseAPI implements SoloDatabaseAPI {
    @Override
    public void updateCurrentUserScore(String currentUserEmail, int generalGameScore) {
        FirebaseFirestore.getInstance().collection(PlayerManager.USER_COLLECTION_NAME)
                .document(currentUserEmail)
                .update("generalScore", generalGameScore);
    }
}
