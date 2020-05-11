package ch.epfl.sdp.database.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {LeaderboardEntity.class}, version = 1)
abstract class AppDatabase extends RoomDatabase {

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile AppDatabase INSTANCE;

    public abstract AppDAO AppDAO();


    @VisibleForTesting
    public static final String DATABASE_NAME = "app-local-db";
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onCreate method to populate the database.
     * We populate the database only when the database is created for the 1st time.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseExecutor.execute(() -> {

                // Delete the leaderboard everytime open the database
                INSTANCE.AppDAO().deleteAllFromLeaderboard();

                // populate the leaderboardEntity
                LeaderboardEntity leaderboardEntity0 = new LeaderboardEntity("fake0@gmail.com", "fake0", 100);
                LeaderboardEntity leaderboardEntity1 = new LeaderboardEntity("fake1@gmail.com", "fake1", 90);
                LeaderboardEntity leaderboardEntity2 = new LeaderboardEntity("fake2@gmail.com", "fake2", 80);
                INSTANCE.AppDAO().insertToLeaderboard(leaderboardEntity0);
                INSTANCE.AppDAO().insertToLeaderboard(leaderboardEntity1);
                INSTANCE.AppDAO().insertToLeaderboard(leaderboardEntity2);

            });
        }
    };
}
