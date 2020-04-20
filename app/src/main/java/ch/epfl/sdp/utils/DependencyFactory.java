package ch.epfl.sdp.utils;

import androidx.annotation.VisibleForTesting;

import ch.epfl.sdp.database.firebase.CommonDatabaseAPI;
import ch.epfl.sdp.database.firebase.CommonFirestoreDatabaseAPI;
import ch.epfl.sdp.login.AuthenticationAPI;
import ch.epfl.sdp.login.FirebaseAuthenticationAPI;

public class DependencyFactory {
    private static AuthenticationAPI authenticationAPI;
    private static CommonDatabaseAPI commonDatabaseAPI;
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
}
