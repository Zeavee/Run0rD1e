package ch.epfl.sdp;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MockAuthentication implements AuthenticationController {
    private HashMap<String, String> signedIn = new HashMap<String, String>();
    private HashMap<String, String> signedOut = new HashMap<String, String>();
    private String currentSignedInEmail;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9\\.]{1,20}@.{1,20}$";
    private static final String PASS_REGEX =  "^.{8,}$";

    private UserDataController store;
    private AuthenticationOutcomeDisplayVisitor displayVisitor;

    public MockAuthentication(AuthenticationOutcomeDisplayVisitor displayVisitor, UserDataController store)
    {
        // amro.abdrabo@gmail.com has previously registered
        this.store = store;
        this.displayVisitor = displayVisitor;
        signedOut.put("amro.abdrabo@gmail.com", "password");
    }
    @Override
    public int signIn(String email, String password) {
        if(!signedOut.containsKey(email)) {
            displayVisitor.onFailedAuthentication();
            return 1;
        }

        if(!signedOut.get(email).equals(password)) {
            displayVisitor.onFailedAuthentication();
            return 2;
        }

        signedOut.remove(email);
        signedIn.put(email, password);
        displayVisitor.onSuccessfulAuthentication();
        currentSignedInEmail = email;
        return 0;
    }

    @Override
    public boolean isSignedIn(String email) {
        return signedIn.containsKey(email);
    }


    @Override
    public boolean register(String email, String username, String password, String passwordConf) {
        if (signedIn.containsKey(email) || signedOut.containsKey(email)) {
            return false;
        }
        if (checkValidity(email,password, passwordConf) != 0)
        {
            return false;
        }
        store.setUserAttribute(email, "username", username);
        signedIn.put(email, password);
        displayVisitor.onSuccessfulAuthentication();
        currentSignedInEmail = email;
        return true;
    }

    @Override
    public boolean signOut() {
        signedOut.put(currentSignedInEmail, signedIn.get(currentSignedInEmail));
        signedIn.remove(currentSignedInEmail);
        currentSignedInEmail = null;
        return true;
    }

    @Override
    public int checkValidity(String email, String password, String passwordConf) {
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        Pattern passwordPattern = Pattern.compile(PASS_REGEX);
        Matcher emailMatch = emailPattern.matcher(email);
        if (!emailMatch.matches()) return 1;
        Matcher passMatch = passwordPattern.matcher(password);
        if (!passMatch.matches() || !password.equals(passwordConf)) return 2;
        return 0;
    }

}
