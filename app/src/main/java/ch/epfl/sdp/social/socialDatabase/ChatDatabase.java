package ch.epfl.sdp.social.socialDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Message.class, Chat.class, User.class, IsFriendsWith.class}, version = 1, exportSchema = false)
@TypeConverters({TimestampConverter.class})
public abstract class ChatDatabase extends RoomDatabase {
    public abstract ChatDAO daoAccess();
}