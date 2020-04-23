package ch.epfl.sdp.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private AppRepository sRepository;
    private final LiveData<List<PlayerEntity>> mObservablePlayers;

    public AppViewModel(@NonNull Application application) {
        super(application);
        sRepository = AppRepository.getInstance(application);
        mObservablePlayers = sRepository.getLeaderboard();
    }

    /**
     * Expose the LiveData LeaderboardEntity query so the UI can observe it.
     */
    public LiveData<List<PlayerEntity>> getLeaderboard() {
        return mObservablePlayers;
    }

    public void insertToLeaderboard(PlayerEntity player) {sRepository.insertToLeaderboard(player); }
}
