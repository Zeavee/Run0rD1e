package ch.epfl.sdp.social.friends_firestore;



/*
This interface provides an abstraction for class that fetch all friends from the remote server
 */
public abstract class RemoteFriendFetcher {
     protected WaitsOnUserFetch waiter;
     public  abstract  void getFriendsFromServer(String constraint);
     public void setListener(WaitsOnUserFetch listener)
     {
          this.waiter = listener;
     }
}
