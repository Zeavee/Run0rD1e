package ch.epfl.sdp.database.room.leader_board;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * The generalLeaderBoardViewModel gets the Application as a parameter and extends AndroidViewModel
 * Holds all the data needed for the GeneralLeaderBoard UI
 */
public class GeneralLeaderBoardViewModel extends AndroidViewModel {
    private final LeaderBoardDatabase sDatabase;
    private final LiveData<List<GeneralLeaderBoardEntity>> mObservableUsers;

    /**
     * The constructor creates a generalLeaderBoardViewModel
     *
     * @param application The application to be used
     */
    public GeneralLeaderBoardViewModel(@NonNull Application application) {
        super(application);
        sDatabase = LeaderBoardDatabase.getInstance(application);
        mObservableUsers = sDatabase.AppDAO().getGeneralLeaderBoard();
    }

    /**
     * Return the LiveData LeaderBoardEntity list so the generalLeaderBoard UI can observe it.
     */
    public LiveData<List<GeneralLeaderBoardEntity>> getGeneralLeaderBoard() {
        return mObservableUsers;
    }

    /**
     * Insert a user to the leaderBoard table in the local Room database
     *
     * @param user The user to be inserted to the leaderBoard table in the local Room database
     */
    public void insertToGeneralLeaderBoard(GeneralLeaderBoardEntity user) {
        LeaderBoardDatabase.databaseExecutor.execute(() -> sDatabase.AppDAO().insertToGeneralLeaderBoard(user));
    }
}
