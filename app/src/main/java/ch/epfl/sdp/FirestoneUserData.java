package ch.epfl.sdp;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirestoneUserData implements UserDataController {
    private Map<String, Object> userAttributes;
    @Override
    public Map<String, Object> getUserData(String id) {
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Players").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists())
                    {
                        userAttributes = doc.getData();
                    }
                }
            }
        });
        return new HashMap<>(userAttributes);
    }
}
