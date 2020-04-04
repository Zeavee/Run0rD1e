package ch.epfl.sdp.game;

/*
 A class that abstracts User info that can be cached across app restarts
 */

public abstract class CacheableUserInfo {
    public String email;
    public String password;
}
