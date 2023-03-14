package com.example.winamp_cloud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "winampusers.db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE "+ TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    COLUMN_USERNAME + " TEXT NOT NULL, "+
                    COLUMN_PASSWORD + " TEXT NOT NULL, "+
                    COLUMN_EMAIL + " TEXT NOT NULL)";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void insertUser(String username, String password, String email){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_EMAIL, email);

        long result = db.insert(TABLE_NAME, null, cv);

        if(result == -1){
            Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show();
            db.close();
        }else{
            Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show();
            db.close();
        }
    }

    public boolean checkLogin(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM "+ TABLE_NAME + " WHERE "
                + COLUMN_EMAIL + " = '" + email+ "' AND "
                + COLUMN_PASSWORD + " = '" + password + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        int count = cursor.getCount();

        cursor.close();
        db.close();

        return count > 0;
    }
}
