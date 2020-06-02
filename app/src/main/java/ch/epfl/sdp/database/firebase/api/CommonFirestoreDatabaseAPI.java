package ch.epfl.sdp.database.firebase.api;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ch.epfl.sdp.database.firebase.entity.PlayerForFirebase;
import ch.epfl.sdp.database.firebase.entity.UserForFirebase;
import ch.epfl.sdp.database.utils.CustomResult;
import ch.epfl.sdp.database.utils.OnValueReadyCallback;
import ch.epfl.sdp.entity.PlayerManager;

public class CommonFirestoreDatabaseAPI implements CommonDatabaseAPI {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private static final PlayerManager playerManager = PlayerManager.getInstance();

    @Override
    public void addUser(UserForFirebase userForFirebase, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.USER_COLLECTION_NAME).document(userForFirebase.getEmail()).set(userForFirebase)
                .addOnSuccessListener(aVoid -> onValueReadyCallback.finish(new CustomResult<>(null, true, null)))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void fetchUser(String email, OnValueReadyCallback<CustomResult<UserForFirebase>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.USER_COLLECTION_NAME).document(email).get()
                .addOnSuccessListener(documentSnapshot -> {
                    UserForFirebase userForFirebase = documentSnapshot.toObject(UserForFirebase.class);
                    onValueReadyCallback.finish(new CustomResult<>(userForFirebase, true, null));
                }).addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void selectLobby(OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        CollectionReference lobbyRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME);
        lobbyRef.whereLessThan("count", PlayerManager.NUMBER_OF_PLAYERS_IN_LOBBY).limit(1).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        playerManager.setLobbyDocumentName(lobbyRef.document().getId());
                        playerManager.setIsServer(true);
                        playerManager.setNumPlayersInLobby(0);
                    } else {
                        QueryDocumentSnapshot doc = queryDocumentSnapshots.iterator().next();
                        playerManager.setLobbyDocumentName(doc.getId());
                        playerManager.setIsServer(false);
                        playerManager.setNumPlayersInLobby(doc.getLong("count"));
                    }
                    onValueReadyCallback.finish(new CustomResult<>(null, true, null));
                }).addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void registerToLobby(PlayerForFirebase playerForFirebase, Map<String, Object> data, OnValueReadyCallback<CustomResult<Void>> onValueReadyCallback) {
        DocumentReference docRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(playerManager.getLobbyDocumentName());
        docRef.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail()).set(playerForFirebase)
                .addOnSuccessListener(aVoid -> docRef.set(data, SetOptions.merge())
                        .addOnSuccessListener(aVoid1 -> onValueReadyCallback.finish(new CustomResult<>(null, true, null)))
                        .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e))))
                .addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void fetchPlayers(String lobbyName, OnValueReadyCallback<CustomResult<List<PlayerForFirebase>>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(lobbyName).collection(PlayerManager.PLAYER_COLLECTION_NAME).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<PlayerForFirebase> playerForFirebases = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        playerForFirebases.add(document.toObject(PlayerForFirebase.class));
                    }
                    onValueReadyCallback.finish(new CustomResult<>(playerForFirebases, true, null));
                }).addOnFailureListener(e -> onValueReadyCallback.finish(new CustomResult<>(null, false, e)));
    }

    @Override
    public void generalGameScoreListener(OnValueReadyCallback<CustomResult<List<UserForFirebase>>> onValueReadyCallback) {
        firebaseFirestore.collection(PlayerManager.USER_COLLECTION_NAME).addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
                onValueReadyCallback.finish(new CustomResult<>(null, false, e));
            } else {
                List<UserForFirebase> userForFirebaseList = new ArrayList<>();
                for (DocumentChange dc : Objects.requireNonNull(queryDocumentSnapshots).getDocumentChanges()) {
                    userForFirebaseList.add(dc.getDocument().toObject(UserForFirebase.class));
                }
                onValueReadyCallback.finish(new CustomResult<>(userForFirebaseList, true, null));
            }
        });
    }

    @Override
    public void sendUserPosition(PlayerForFirebase playerForFirebase) {
        DocumentReference lobbyRef = firebaseFirestore.collection(PlayerManager.LOBBY_COLLECTION_NAME).document(PlayerManager.getInstance().getLobbyDocumentName());
        lobbyRef.collection(PlayerManager.PLAYER_COLLECTION_NAME).document(playerForFirebase.getEmail()).update("geoPointForFirebase", playerForFirebase.getGeoPointForFirebase());
    }
}