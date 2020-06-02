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

@Database(entities = {LeaderBoardEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile AppDatabase INSTANCE;

    public abstract AppDAO AppDAO();

    @VisibleForTesting
    private static final String DATABASE_NAME = "app-local-db";
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * returns the singleton of AppDatabase. It'll create the database the first time it's accessed, using Room's database builder
     * to create a RoomDatabase object in the application context from the AppDatabase class and names it "app-local-db".
     *
     * @param context The application context to be used
     * @return The singleton of the database
     */
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
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseExecutor.execute(() -> {
                // Delete the leaderBoard first time create the database
                INSTANCE.AppDAO().deleteAllFromGeneralLeaderBoard();
            });
        }
    };
}
