package com.example.bersuara;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "chat.db";
    public static final String TABLE_NAME = "messages";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "SENDER";
    public static final String COL_3 = "MESSAGE";

    public ChatDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create messages table
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SENDER TEXT, MESSAGE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert new message into the database
    public boolean insertMessage(String sender, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, sender);
        contentValues.put(COL_3, message);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // Retrieve all chat messages from the database
    public Cursor getAllMessages() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        }
}