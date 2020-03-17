package ch.epfl.sdp;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class FirestoreUserData implements UserDataController {
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    @Override
    public User getUserData(String username) {
        return (User) fstore.collection("Users").document(username).get().getResult().getData();
    }

    @Override
    public void setUserAttribute(User user) {
        fstore.collection("Users").document(user.getUsername()).set(user, SetOptions.merge());
    }
}
