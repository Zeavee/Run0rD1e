package ch.epfl.sdp.game;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MockDatabaseHelper extends SQLiteOpenHelper {

    public MockDatabaseHelper(@Nullable Context context) {
        super(context, "MockUser.db", null, 1);
    }

    public UserData getLoggedUser()
    {
        return new UserData("","");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public class UserData extends CacheableUserInfo
    {
        // The email and password can be hard coded according to the test
        public UserData(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String email;
        public String password;
    }
}
