package ch.epfl.sdp.leaderBoard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ch.epfl.sdp.database.room.AppDatabase;
import ch.epfl.sdp.database.room.LeaderBoardEntity;

import java.util.List;

/**
 * The generalLeaderBoardViewModel gets the Application as a parameter and extends AndroidViewModel
 * Holds all the data needed for the GeneralLeaderBoard UI
 */
class GeneralLeaderBoardViewModel extends AndroidViewModel {
    private final AppDatabase sDatabase;
    private final LiveData<List<LeaderBoardEntity>> mObservableUsers;

    /**
     * The constructor creates a generalLeaderBoardViewModel
     *
     * @param application The application to be used
     */
    public GeneralLeaderBoardViewModel(@NonNull Application application) {
        super(application);
        sDatabase = AppDatabase.getInstance(application);
        mObservableUsers = sDatabase.AppDAO().getGeneralLeaderBoard();
    }

    /**
     * Return the LiveData LeaderBoardEntity list so the generalLeaderBoard UI can observe it.
     */
    LiveData<List<LeaderBoardEntity>> getGeneralLeaderBoard() {
        return mObservableUsers;
    }

    /**
     * Insert a user to the leaderBoard table in the local Room database
     *
     * @param user The user to be inserted to the leaderBoard table in the local Room database
     */
    void insertToGeneralLeaderBoard(LeaderBoardEntity user) {
        AppDatabase.databaseExecutor.execute(() -> sDatabase.AppDAO().insertToGeneralLeaderBoard(user));
    }
}
