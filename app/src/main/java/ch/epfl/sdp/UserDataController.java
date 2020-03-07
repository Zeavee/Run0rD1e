package ch.epfl.sdp;

import java.util.Map;

public interface UserDataController {

    Map<String, Object> getUserData(String id);

    void setUserAttribute(String id, String attribute, Object value);
}
