package com.example.hackathon202108;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DateDBOpenHelper {
    public static final String DATEDB_NAME="DateInfo.db";
    public static final int DATABASE_VERSION=1;
    public static SQLiteDatabase db;
    private DataBaseHelper dbHelper;
    private Context context;

    private class DataBaseHelper extends SQLiteOpenHelper{
        public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        public void onCreate(SQLiteDatabase db){
            db.execSQL(DataBases.createDateDB._CREATE1);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+DataBases.createDateDB._TABLENAME1);
            onCreate(db);
        }
    }

    public DateDBOpenHelper(Context context){
        this.context = context;
    }

    public DateDBOpenHelper open() throws SQLException{
        dbHelper = new DataBaseHelper(context, DATEDB_NAME, null, DATABASE_VERSION);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        dbHelper.onCreate(db);
    }

    public void close(){
        db.close();
    }

    public long insertColumn(String str, int x, int y){
        ContentValues values = new ContentValues();
        values.put(DataBases.createDateDB.DATE, str);
        values.put(DataBases.createDateDB.STARTBEDTIME, x);
        values.put(DataBases.createDateDB.ENDBEDTIME, y);
        return db.insert(DataBases.createDateDB._TABLENAME1, null, values);
    }

    public Cursor selectColumns(){
        return db.query(DataBases.createDateDB._TABLENAME1,null,null,null,null,null,null);
    }

    public boolean updateColumn(long id, String str, int x , int y){
        ContentValues values = new ContentValues();
        values.put(DataBases.createDateDB.DATE, str);
        values.put(DataBases.createDateDB.STARTBEDTIME, x);
        values.put(DataBases.createDateDB.ENDBEDTIME, y);
        return db.update(DataBases.createDateDB._TABLENAME1, values, "_id=" + id, null) > 0;

    }
    public void deleteAllColumns(){
        db.delete(DataBases.createDateDB._TABLENAME1,null,null);
    }

    public boolean deleteColumn(long id) {
        return db.delete(DataBases.createDateDB._TABLENAME1, "_id=" + id, null) > 0;
    }


}
