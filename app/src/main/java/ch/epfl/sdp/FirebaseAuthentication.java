package ch.epfl.sdp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * This class is designed to use Firebase's email and password feature
 */
public class FirebaseAuthentication implements AuthenticationController {

    /* This regular expression pattern allows for matching emails of
     * the form [alphanumeric characters or dot]@[any characters]
     * where the part before the @ sign can have length ranging from
     *  1 to 20 and the part after can have length ranging from 1 to 20
     * useful for sanitizing input
     */
    private final static String REGEX = "^[A-Za-z0-9\\.]{1,20}@.{1,20}$";
    private AuthenticationOutcomeDisplayVisitor displayVisitor;
    private FirebaseAuth auth;
    private AppCompatActivity activity;
    private UserDataController userDataStore;

    public FirebaseAuthentication(AuthenticationOutcomeDisplayVisitor displayVisitor, UserDataController store, AppCompatActivity activity)
    {
        this.displayVisitor = displayVisitor;
        auth = FirebaseAuth.getInstance();
        this.activity = activity;
        userDataStore = store;
    }

    @Override
    public boolean signIn(String id, String password) {
        if (checkValidity(id, password) == false)
        {
            return false;
        }
        auth.signInWithEmailAndPassword(id, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                displayVisitor.onSuccessfulAuthentication();
            }
        });
        return true;
    }

    @Override
    public boolean isSignedIn() {
        return  auth.getCurrentUser() != null;
    }

    @Override
    public boolean register(final String id, final String username, String password) {
        if (checkValidity(id, password) == false)
        {
            return false;
        }
        auth.createUserWithEmailAndPassword(id, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    displayVisitor.onSuccessfulAuthentication();
                    userDataStore.setUserAttribute(id, "username", username);
                }
                else {
                    displayVisitor.onFailedAuthentication();
                }
            }
        });
        return true;
    }

    @Override
    public boolean signOut() {
        auth.signOut();
        return true;
    }

    @Override
    public boolean checkValidity(String id, String password) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(id);
        if ((!matcher.matches()) || password.length() < 4)
        {
            displayVisitor.onFailedAuthentication();
            return false;
        }
        return true;
    }

   /* @Override
    public Player signedInPlayer() {
        String id =  auth.getCurrentUser().getEmail();
        Map<String, Object> userData = userDataStore.getUserData(id);
        double longitude = Double.parseDouble(userData.get("longitude").toString());
        double latitude = Double.parseDouble(userData.get("latitude").toString());
        double radius = Double.parseDouble(userData.get("radius").toString());
        String username =userData.get("username").toString();
        Player p = new Player(longitude, latitude, radius, username, id);
        return p;
    }*/
}
