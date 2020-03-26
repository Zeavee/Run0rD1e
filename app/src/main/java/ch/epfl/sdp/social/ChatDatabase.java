package ch.epfl.sdp.social;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Message.class, Chat.class, User.class}, version = 1)
@TypeConverters({TimestampConverter.class})
public abstract class ChatDatabase extends RoomDatabase {
    public abstract ChatAccessor daoAccess();
}