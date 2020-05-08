package ch.epfl.sdp.dependency.injection;

import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.authentication.AuthenticationAPI;
import ch.epfl.sdp.map.MapApi;
import ch.epfl.sdp.social.Conversation.RemoteToSQLiteAdapter;
import ch.epfl.sdp.social.RemoteUsers.RemoteFriendFetcher;

/* There is a visitor for each interface that can be mocked
 Example, UserDataController is the interface which is implemented by MockUserDataController and FireStoreUserData thus UserDataController
 has its own visitor. The Activity that seeks to be tested should redefine the appropriate visitor method. Any class that has external dependencies
 must extend DependencyVisitor. During testing, to set the dependencies of the activity rule simply call beforeActivityLaunched  and then ((DependencyVisitor)getActivity()).setDependency
 */
public interface DependencyVisitor {

    void setDependency(CommonDatabaseAPI dependency);

    void setDependency(AuthenticationAPI dependency);

    void setDependency(MapApi dependency);

    void setDependency(RemoteToSQLiteAdapter dependency);

    void setDependency(RemoteFriendFetcher dataController);

    void inject();

}
