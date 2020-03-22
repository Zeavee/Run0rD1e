package ch.epfl.sdp.social;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.UserForFirebase;

public class Friend extends UserForFirebase {

    private List<Friend> friends;
    private UserForFirebase wrappedUser;
    public Friend(UserForFirebase usr)
    {
        this.wrappedUser = usr;
    }
    public String getName()
    {
        return wrappedUser.getUsername() + ": " + wrappedUser.getEmail();
    }
    public int getFriendCount()
    {
        return friends.size();
    }
    public boolean isFriendsWith(Friend usr)
    {
        return usr.friends.contains(this) && this.friends.contains(usr);
    }
    public void addFriend(Friend usr)
    {
        friends.add(usr);
        usr.friends.add(this);
    }
    public void unFriend(Friend usr)
    {
        friends.remove(usr);
        usr.friends.remove(this);
    }
    public List<Friend> getFriends() {
        List<Friend> friends = new ArrayList<>();
        friends.addAll(friends);
        return friends;
    }
}
