package ch.epfl.sdp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidator {
    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPasswordValid(String password) {
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
