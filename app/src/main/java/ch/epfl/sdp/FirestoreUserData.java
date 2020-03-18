package ch.epfl.sdp;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class FirestoreUserData implements UserDataController {
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    @Override
    public UserForFirebase getUserData(String username) {
        return (UserForFirebase) fstore.collection("Users").document(username).get().getResult().getData();
    }

    @Override
    public void setUserAttribute(UserForFirebase userForFirebase) {
        fstore.collection("Users").document(userForFirebase.getUsername()).set(userForFirebase)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "onSuccess: success saved"))
                .addOnFailureListener(e -> Log.d(TAG, "onFailure: failed"));
    }
}
