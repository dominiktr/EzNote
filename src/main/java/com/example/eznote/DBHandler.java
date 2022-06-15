package com.example.eznote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {
    public DBHandler(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, "EzMemosDB.db", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE notes( _id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }

    public Cursor getMemo(int id){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("SELECT * FROM notes WHERE _id = "+id, null);
    }

    public Cursor getAllNotes(){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("SELECT * FROM notes", null);
    }

    public void addMemo(String title, String content){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title",title);
        cv.put("content", content);
        db.insert("notes",null, cv);
        db.close();
    }

    public void edit(int id, String title, String content){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title",title);
        cv.put("content", content);
        db.update("notes",cv,"_id = "+id,null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("notes","_id = "+id, null);
        db.close();
    }

}
