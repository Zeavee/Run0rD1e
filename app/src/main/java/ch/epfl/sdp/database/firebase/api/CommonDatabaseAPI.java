package ch.epfl.sdp.database.firebase.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.leaderboard.LeaderboardViewModel;

public interface CommonDatabaseAPI {

    void syncCloudFirebaseToRoom(LeaderboardViewModel leaderboardViewModel);

    void addUser(UserForFirebase userForFirebase, OnValueReadyCallback<Task<Void>> OnValueReadyCallback);

    void fetchUser(String email, OnValueReadyCallback<Task<DocumentSnapshot>> onValueReadyCallback);

    void selectLobby(OnValueReadyCallback<Task<QuerySnapshot>> onValueReadyCallback);

    void registerToLobby(PlayerForFirebase playerForFirebase, Map<String, Object> data, OnValueReadyCallback<Task<Void>> onValueReadyCallback);

    void fetchPlayers(OnValueReadyCallback<Task<QuerySnapshot>> onValueReadyCallback);
}
