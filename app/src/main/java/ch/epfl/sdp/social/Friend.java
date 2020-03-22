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
    public int getFriendCount()
    {
        return friends.size();
    }
    public void addFriend(Friend usr)
    {
        friends.add(usr);
        usr.addFriend(this);
    }
    public void unFriend(Friend usr)
    {
        friends.remove(usr);
        usr.unFriend(this);
    }
    public List<Friend> getFriends() {
        List<Friend> friends = new ArrayList<>();
        friends.addAll(friends);
        return friends;
    }
}
