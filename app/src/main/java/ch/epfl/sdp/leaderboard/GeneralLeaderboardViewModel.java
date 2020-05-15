package ch.epfl.sdp.leaderboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ch.epfl.sdp.database.room.AppRepository;
import ch.epfl.sdp.database.room.LeaderboardEntity;

import java.util.List;

public class GeneralLeaderboardViewModel extends AndroidViewModel {
    private AppRepository sRepository;
    private final LiveData<List<LeaderboardEntity>> mObservableUsers;

    public GeneralLeaderboardViewModel(@NonNull Application application) {
        super(application);
        sRepository = AppRepository.getInstance(application);
        mObservableUsers = sRepository.getLeaderboard();
    }

    /**
     * Expose the LiveData LeaderboardEntity query so the UI can observe it.
     */
    public LiveData<List<LeaderboardEntity>> getLeaderboard() {
        return mObservableUsers;
    }

    public void insertToLeaderboard(LeaderboardEntity user) {sRepository.insertToLeaderboard(user); }
}
