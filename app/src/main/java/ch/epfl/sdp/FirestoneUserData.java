package ch.epfl.sdp;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import static java.util.Map.Entry;

public class FirestoneUserData implements UserDataController {
    @Override
    public Map<String, Object> getUserData(String id) {
        return FirebaseFirestore.getInstance().collection("Players").document(id).get().getResult().getData();
    }

    @Override
    public void setUserAttribute(String id, final String attribute, Object value) {
        Map<String, Object> data = new HashMap<>();
        data.put(attribute, value);
        FirebaseFirestore.getInstance().collection("Players").document(id).set(data, SetOptions.merge());

    }
}
