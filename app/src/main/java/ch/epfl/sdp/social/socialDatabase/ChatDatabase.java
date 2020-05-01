package ch.epfl.sdp.social.socialDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * @brief provides a singleton instance of the database accessor object, which allows us to perform operations on the database.
 */
@Database(entities = {Message.class, Chat.class, User.class, IsFriendsWith.class}, version = 1, exportSchema = false)
@TypeConverters({TimestampConverter.class})
public abstract class ChatDatabase extends RoomDatabase {

    /**
     * @brief a singleton object of ChatDatabase that mediates all database operations
     * @return the singleton instance of ChatDAO which is created by a call to Room.databaseBuilder
     */
    public abstract ChatDAO daoAccess();
}