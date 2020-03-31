package ch.epfl.sdp.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {
    private static AppRepository sInstance;
    private final AppDatabase mDatabase;
    private LiveData<List<PlayerEntity>> mObservablePlayers;

    public AppRepository(final AppDatabase mDatabase) {
        this.mDatabase = mDatabase;
        this.mObservablePlayers = mDatabase.AppDAO().getLeaderboard();
    }

    public static AppRepository getInstance(Application application) {
        if (sInstance == null) {
            synchronized (AppRepository.class) {
                if (sInstance == null) {
                    sInstance = new AppRepository(AppDatabase.getInstance(application));
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<PlayerEntity>> getLeaderboard() {
        return mObservablePlayers;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insertToLeaderboard(PlayerEntity player) {
        mDatabase.databaseExecutor.execute(() -> {
            mDatabase.AppDAO().insertToLeaderboard(player);
        });
    }
}
