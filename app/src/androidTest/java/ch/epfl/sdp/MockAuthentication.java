package ch.epfl.sdp;

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MockAuthentication implements AuthenticationController {
    private TreeMap<String, String> registeredUsers = new TreeMap<>();
    private TreeSet<String> signedIn = new TreeSet<>();

    @Override
    public boolean signIn(String email, String password) {
        if(!registeredUsers.containsKey(email)) {
            return false;
        }

        if(!registeredUsers.get(email).equals(password)) {
            return false;
        }

        signedIn.add(email);
        return true;
    }

    @Override
    public boolean isSignedIn(String email) {
        return signedIn.contains(email);
    }

    @Override
    public boolean register(String email, String username, String password) {
        if (registeredUsers.containsKey(email)) {
            return false;
        }

        if(!DataValidator.isEmailValid(email)) {
            return false;
        }

        if(!isPasswordValid(password)) {
            return false;
        }

        registeredUsers.put(email, password);
        return true;
    }

    @Override
    public boolean signOut(String email) {
        if(!isSignedIn(email)) {
            return false;
        }
        signedIn.remove(email);
        return true;
    }

    @Override
    public boolean checkValidity(String email, String password) {
        return isEmailValid(email) && isPasswordValid(password);
    }

    @Override
    public boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean isPasswordValid(String password) {
        if(password.length() < 8) {
            return false;
        }

        // Kein grosser Buchstabe
        // aaaba == aaaba
        // abCde != abcde
        if(password.equals(password.toLowerCase())) {
            return false;
        }

        // Kein kleiner Buchstabe
        // ABC == ABC
        // aBC != ABC
        if(password.equals(password.toUpperCase())) {
            return false;
        }

        // Kein digit
        if(!password.matches("\\d")) {
            return false;
        }

        // Kein spezielles Character
        if(!password.matches("^[A-Za-z0-9 ]*$")) {
            return false;
        }

        return true;
    }
}
