package ch.epfl.sdp.database.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LeaderoardViewModel extends AndroidViewModel {
    private DataRepository sRepository;
    private final LiveData<List<LeaderboardEntity>> mObservableUsers;

    public LeaderoardViewModel(@NonNull Application application) {
        super(application);
        sRepository = DataRepository.getInstance(application);
        mObservableUsers = sRepository.getLeaderboard();
    }

    /**
     * Expose the LiveData LeaderboardEntity query so the UI can observe it.
     */
    public LiveData<List<LeaderboardEntity>> getLeaderboard() {
        return mObservableUsers;
    }

    public void insert(LeaderboardEntity user) {sRepository.insert(user); }
}
