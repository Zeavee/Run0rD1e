package ch.epfl.sdp;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private static final int MINIMUM_PASSWORD_LENGTH = 8;
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
    public int signIn(String id, String password) {
        int isValid = checkValidity(id, password, password);
        if (isValid != 0)
        {
            return isValid;
        }
        auth.signInWithEmailAndPassword(id, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                displayVisitor.onSuccessfulAuthentication();
            }
        });
        return 0;
    }

    @Override
    public boolean isSignedIn(String email) {
        return auth.getCurrentUser() != null;
    }

    @Override
    public boolean register(final String id, final String username, String password, String passwordConf) {
        if (checkValidity(id, password, passwordConf) != 0)
        {
            return false;
        }
        auth.createUserWithEmailAndPassword(id, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    userDataStore.setUserAttribute(id, "username", username);
                    displayVisitor.onSuccessfulAuthentication();
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
    public int checkValidity(String id, String password, String passwordConf) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(id);
        if ((!matcher.matches()))
        {
            displayVisitor.onFailedAuthentication();
            return 1;
        }
        if(password.length() < MINIMUM_PASSWORD_LENGTH || (!passwordConf.equals(password))){
            displayVisitor.onFailedAuthentication();
            return 2;
        }
        return 0;
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
