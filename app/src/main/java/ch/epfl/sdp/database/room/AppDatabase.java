package ch.epfl.sdp.database.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {LeaderboardEntity.class}, version = 1, exportSchema = false)
abstract class AppDatabase extends RoomDatabase {

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile AppDatabase INSTANCE;

    public abstract AppDAO LeaderboardDAO();

    @VisibleForTesting
    public static final String DATABASE_NAME = "app-local-db";
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();
    static final ExecutorService databaseWriteExecutor = Executors.newSingleThreadExecutor();

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                    INSTANCE.updateDatabaseCreated(context.getApplicationContext());
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

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Add a delay to simulate a long-running operation
                addDelay();

                // Populate the database in the background.
//                List<LeaderboardEntity> users = generateUsers();
                INSTANCE.LeaderboardDAO().deleteAll();
//                INSTANCE.LeaderboardDAO().insertAll(users);

                // notify that the database was created and it's ready to be used
                INSTANCE.setDatabaseCreated();
            });
        }
    };

    private static void addDelay() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

//    private static List<LeaderboardEntity> generateUsers(){
//        String[] usernames = new String[]{"aaa", "bbb", "ccc", "ddd", "eee"};
//        Double[] healthPoints = new Double[]{100D, 90D, 80D, 70D, 60D};
//        List<LeaderboardEntity> users = new ArrayList<>();
//        for(int i = 0; i < usernames.length; i++){
//            LeaderboardEntity user = new LeaderboardEntity(usernames[i]+"@gmail.com", usernames[i], healthPoints[i]);
//            users.add(user);
//        }
//        return users;
//    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
