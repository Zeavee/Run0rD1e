package ch.epfl.sdp;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import static android.content.ContentValues.TAG;

public class FirestoreUserData implements UserDataController {
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    @Override
    public User getUserData(String username) {
        return (User) fstore.collection("Users").document(username).get().getResult().getData();
    }

    @Override
    public void setUserAttribute(User user) {
        fstore.collection("Users").document(user.getUsername()).set(user)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "onSuccess: success saved"))
                .addOnFailureListener(e -> Log.d(TAG, "onFailure: failed"));
    }
}
