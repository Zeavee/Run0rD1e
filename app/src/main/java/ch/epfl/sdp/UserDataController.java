package ch.epfl.sdp;

public interface UserDataController {

    User getUserData(String username);

    void setUserAttribute(User user);
}
