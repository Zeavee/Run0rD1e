package ch.epfl.sdp.utils;

import androidx.annotation.VisibleForTesting;

import ch.epfl.sdp.database.firebase.api.ClientDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.ClientFirestoreDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.CommonFirestoreDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.ServerDatabaseAPI;
import ch.epfl.sdp.database.firebase.api.ServerFirestoreDatabaseAPI;
import ch.epfl.sdp.login.AuthenticationAPI;
import ch.epfl.sdp.login.FirebaseAuthenticationAPI;

public class DependencyFactory {
    private static AuthenticationAPI authenticationAPI;
    private static CommonDatabaseAPI commonDatabaseAPI;
    private static ServerDatabaseAPI serverDatabaseAPI;
    private static ClientDatabaseAPI clientDatabaseAPI;
    private static boolean testMode = false;

    public static void setTestMode(boolean testMode) {
        DependencyFactory.testMode = testMode;
    }

    public static boolean isTestMode() {
        return testMode;
    }

    @VisibleForTesting
    public static void setAuthenticationAPI(AuthenticationAPI authenticationAPI) {
        DependencyFactory.authenticationAPI = authenticationAPI;
    }

    public static AuthenticationAPI getAuthenticationAPI() {
        if(testMode && authenticationAPI != null) {
            return authenticationAPI;
        }
        return new FirebaseAuthenticationAPI();
    }

    @VisibleForTesting
    public static void setCommonDatabaseAPI(CommonDatabaseAPI commonDatabaseAPI) {
        DependencyFactory.commonDatabaseAPI = commonDatabaseAPI;
    }


    public static CommonDatabaseAPI getCommonDatabaseAPI() {
        if(testMode && commonDatabaseAPI != null) {
            return commonDatabaseAPI;
        }
        return new CommonFirestoreDatabaseAPI();
    }

    @VisibleForTesting
    public static void setServerDatabaseAPI(ServerDatabaseAPI serverDatabaseAPI) {
        DependencyFactory.serverDatabaseAPI = serverDatabaseAPI;
    }

    public static ServerDatabaseAPI getServerDatabaseAPI() {
        if(testMode && serverDatabaseAPI != null) {
            return serverDatabaseAPI;
        }
        return new ServerFirestoreDatabaseAPI();
    }

    @VisibleForTesting
    public static void setClientDatabaseAPI(ClientDatabaseAPI clientDatabaseAPI) {
        DependencyFactory.clientDatabaseAPI = clientDatabaseAPI;
    }

    public static ClientDatabaseAPI getClientDatabaseAPI() {
        if(testMode && clientDatabaseAPI != null) {
            return clientDatabaseAPI;
        }
        return new ClientFirestoreDatabaseAPI();
    }
}
