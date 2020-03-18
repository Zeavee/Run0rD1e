package ch.epfl.sdp;

public interface UserDataController {

    UserForFirebase getUserData(String username);

    void setUserAttribute(UserForFirebase userForFirebase);
}
