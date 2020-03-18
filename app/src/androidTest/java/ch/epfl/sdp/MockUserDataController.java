package ch.epfl.sdp;

import java.util.TreeMap;

public class MockUserDataController implements UserDataController{
    private TreeMap<String, UserForFirebase> userData = new TreeMap<>();

    @Override
    public UserForFirebase getUserData(String username) {
        if(!userData.containsKey(username)) {
            return null;
        }
        return userData.get(username);
    }

    @Override
    public void setUserAttribute(UserForFirebase userForFirebase) {
        userData.put(userForFirebase.getUsername(), userForFirebase);
    }
}
