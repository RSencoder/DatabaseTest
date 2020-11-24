package com.example.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private String createBook = "create table Book (" +
            " id integer primary key," +
            "author text," +
            "price real," +
            "pages integer," +
            "name text)";
    private String TAG = "MyDatabaseHelper";

    public MyDatabaseHelper(@Nullable Context context, @Nullable String name,
                            @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createBook);
        Log.d(TAG, "Create succeeded");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(createBook);
        Log.d(TAG, "Update succeeded");
    }
}
