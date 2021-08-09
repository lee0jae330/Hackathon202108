package com.example.hackathon202108;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TimeDBOpenHelper {
    public static final String TIMEDB_NAME="TimeInfo.db";
    public static final int DATABASE_VERSION=1;
    public static SQLiteDatabase db;
    private DataBaseHelper dbHelper;
    private Context context;

    private class DataBaseHelper extends SQLiteOpenHelper{
        public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        public void onCreate(SQLiteDatabase db){
            db.execSQL(DataBases.createTimeDB._CREATE2);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+DataBases.createTimeDB._TABLENAME2);
            onCreate(db);
        }
    }

    public TimeDBOpenHelper(Context context){
        this.context = context;
    }

    public TimeDBOpenHelper open() throws SQLException{
        dbHelper = new DataBaseHelper(context, TIMEDB_NAME, null, DATABASE_VERSION);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        dbHelper.onCreate(db);
    }

    public void close(){
        db.close();
    }

    public long insertColumn(int type, String str, int x, int y){
        ContentValues values = new ContentValues();
        values.put(DataBases.createTimeDB.DATE, str);
        values.put(DataBases.createTimeDB.STARTOFFTIME, x);
        values.put(DataBases.createTimeDB.ENDOFFTIME, y);
        return db.insert(DataBases.createTimeDB._TABLENAME2, null, values);
    }

    public Cursor selectColumns(){
        return db.query(DataBases.createTimeDB._TABLENAME2,null,null,null,null,null,null);
    }

    public boolean updateColumn(long id, String str, int x , int y){
        ContentValues values = new ContentValues();
        values.put(DataBases.createTimeDB.DATE, str);
        values.put(DataBases.createTimeDB.STARTOFFTIME, x);
        values.put(DataBases.createTimeDB.ENDOFFTIME, y);
        return db.update(DataBases.createTimeDB._TABLENAME2, values, "_id=" + id, null) > 0;
    }
    public void deleteAllColumns(){
        db.delete(DataBases.createTimeDB._TABLENAME2,null,null);
    }

    public boolean deleteColumn(long id){
        return db.delete(DataBases.createTimeDB._TABLENAME2,"_id="+id,null)>0;
    }
}
