package ch.epfl.sdp.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataRepository {
    private static DataRepository sInstance;
    private final AppDatabase mDatabase;
    private LiveData<List<LeaderboardEntity>> mObservableUsers;

    public DataRepository(final AppDatabase mDatabase) {
        this.mDatabase = mDatabase;
        this.mObservableUsers = mDatabase.LeaderboardDAO().getLeaderboard();
    }

    public static DataRepository getInstance(Application application) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(AppDatabase.getInstance(application));
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<LeaderboardEntity>> getLeaderboard() {
        return mObservableUsers;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(LeaderboardEntity user) {
        mDatabase.databaseWriteExecutor.execute(() -> {
            mDatabase.LeaderboardDAO().insert(user);
        });
    }
}
