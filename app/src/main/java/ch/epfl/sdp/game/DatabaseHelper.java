package ch.epfl.sdp.game;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DatabaseHelper extends SQLiteOpenHelper {
    public final static String DB_NAME = "run0rd1e.db";
    public final static int DB_VERSION = 1;

    public final static String USERS_TABLE_NAME = "users";
    public final static String USERS_ID = "id";
    public final static String USERS_EMAIL = "email";
    public final static String USERS_PASSWORD = "password";
    public final static String USERS_CREATE_TABLE =
            "CREATE TABLE " + USERS_TABLE_NAME +
                    "(" +
                    USERS_ID + " INTEGER PRIMARY KEY," +
                    USERS_EMAIL + " TEXT NOT NULL," +
                    USERS_PASSWORD + " TEXT NOT NULL" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void saveLoggedUser(String email, String password) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            String sqlQuery = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?)",
                    USERS_TABLE_NAME, USERS_EMAIL, USERS_PASSWORD, email, password);
            SQLiteStatement stmt = writableDatabase.compileStatement(sqlQuery);
            stmt.bindString(1, email);
            stmt.bindString(2, password);
            stmt.executeInsert();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public UserData getLoggedUser() {
        UserData result = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + USERS_TABLE_NAME, null);

        if (c.moveToNext()) {
            String email = c.getString(c.getColumnIndex(USERS_EMAIL));
            String password = c.getString(c.getColumnIndex(USERS_PASSWORD));
            result = new UserData(email, password);
        }

        c.close();
        return result;
    }

    public void deleteAllUsers() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.execSQL("DELETE FROM " + USERS_TABLE_NAME);
    }

    public class UserData extends CacheableUserInfo {
        public UserData(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(USERS_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
