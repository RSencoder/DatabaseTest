package com.example.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static String TAG_USER = "User_info";
    private static String TAG_BOOK = "Book_info";

    private Button btnAdd;
    private Button btnSelect;
    private Button btnDelete;

    private Button btnSOHCreateDatabase;
    private Button btnSOHAdd;
    private Button btnSOHUpdate;
    private Button btnSOHDelete;
    private Button btnSOHSelect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btn_add);
        btnSelect = findViewById(R.id.btn_select);
        btnDelete = findViewById(R.id.btn_delete);

        btnSOHCreateDatabase = findViewById(R.id.btn_create_database);
        btnSOHAdd = findViewById(R.id.btn_soh_add);
        btnSOHUpdate = findViewById(R.id.btn_soh_update);
        btnSOHDelete = findViewById(R.id.btn_soh_delete);
        btnSOHSelect = findViewById(R.id.btn_soh_select);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        User user = new User();
                        user.setUid(1);
                        user.setFirstName("Richard");
                        user.setLastName("NB");
                        UserDatabase
                                .getInstance(getApplicationContext())
                                .getUserDao()
                                .insert(user);
                        return null;
                    }
                }.execute();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        User user = UserDatabase
                                .getInstance(getApplicationContext())
                                .getUserDao()
                                .findByUid(1);
//                        Toast.makeText(MainActivity.this,
//                                user.uid + ", " + user.firstName + ", " + user.lastName,
//                                Toast.LENGTH_LONG).show();
                        Log.d(TAG_USER,
                                user.uid + ", " + user.firstName + ", " + user.lastName);
                        return null;
                    }
                }.execute();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        User user = new User();
                        user.setUid(1);
                        user.setFirstName("Richard");
                        user.setLastName("NB");
                        UserDatabase
                                .getInstance(getApplicationContext())
                                .getUserDao()
                                .delete(user);
                        return null;
                    }
                }.execute();
            }
        });

        final MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "BookStore.db",
                null, 1);
        btnSOHCreateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });
        btnSOHAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues value1 = new ContentValues();
                value1.put("id", 1);
                value1.put("name", "The Da Vinci Code");
                value1.put("author", "Dan Brown");
                value1.put("pages", 454);
                value1.put("price", 16.96);
                db.insert("Book", null, value1);
                ContentValues value2 = new ContentValues();
                value2.put("id", 2);
                value2.put("name", "The Lost Symbol");
                value2.put("author", "Dan Brown");
                value2.put("pages", 510);
                value2.put("price", 19.95);
                db.insert("Book", null, value2);
            }
        });
        btnSOHUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                ContentValues value = new ContentValues();
                value.put("price", 10.99);
                String[] args = new String[]{"The Da Vinci Code"};
                db.update("Book", value, "name = ?", args);
            }
        });
        btnSOHDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String[] args = new String[]{"500"};
                db.delete("Book", "pages > ?", args);
            }
        });
        btnSOHSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.rawQuery("select * from Book", null);
                if (cursor.moveToFirst()) {
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        StringBuilder builder = new StringBuilder();
                        builder.append("id: ").append(id).append(" name: ").append(name).
                                append(" pages: ").append(pages).append(" price: ").append(price);
                        Log.d(TAG_BOOK, builder.toString());
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}
