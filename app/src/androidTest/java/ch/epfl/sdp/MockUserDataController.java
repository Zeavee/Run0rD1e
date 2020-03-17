package ch.epfl.sdp;

import java.util.Map;
import java.util.TreeMap;

public class MockUserDataController implements UserDataController{
    private TreeMap<String, User> userData = new TreeMap<>();

    @Override
    public User getUserData(String username) {
        if(!userData.containsKey(username)) {
            return null;
        }
        return userData.get(username);
    }

    @Override
    public void setUserAttribute(User user) {
        userData.put(user.getUsername(), user);
    }
}
