package ch.epfl.sdp;

import java.util.Map;
import java.util.TreeMap;

public class MockUserDataController implements UserDataController{
    private TreeMap<String, Map<String, Object>> userData = new TreeMap<>();

    @Override
    public Map<String, Object> getUserData(String id) {
        if(!userData.containsKey(id)) {
            return null;
        }
        return userData.get(id);
    }

    @Override
    public void setUserAttribute(String id, String attribute, Object value) {
        if (!userData.containsKey(id)) {
            userData.put(id, new TreeMap<>());
        }
        getUserData(id).put(attribute, value);
    }
}
