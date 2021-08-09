package com.example.hackathon202108;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InfoDBOpenHelper {
    public static final String INFODB_NAME="UserInfo.db";
    public static final int DATABASE_VERSION=1;
    public static SQLiteDatabase db;
    private DataBaseHelper dbHelper;
    private Context context;

    private class DataBaseHelper extends SQLiteOpenHelper{
        public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        public void onCreate(SQLiteDatabase db){
            db.execSQL(DataBases.createInfoDB._CREATE0);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+DataBases.createInfoDB._TABLENAME0);
            onCreate(db);
        }
    }

    public InfoDBOpenHelper(Context context){
        this.context = context;
    }

    public InfoDBOpenHelper open() throws SQLException{
        dbHelper = new DataBaseHelper(context, INFODB_NAME, null, DATABASE_VERSION);
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
        values.put(DataBases.createInfoDB.NAME, str);
        values.put(DataBases.createInfoDB.STARTTIME, x);
        values.put(DataBases.createInfoDB.ENDTIME, y);
        return db.insert(DataBases.createInfoDB._TABLENAME0, null, values);
    }

    public Cursor selectColumns(){
        return db.query(DataBases.createInfoDB._TABLENAME0,null,null,null,null,null,null);
    }

    public boolean updateColumn(long id, String str, int x , int y){
        ContentValues values = new ContentValues();
        values.put(DataBases.createInfoDB.NAME, str);
        values.put(DataBases.createInfoDB.STARTTIME, x);
        values.put(DataBases.createInfoDB.ENDTIME, y);
        return db.update(DataBases.createInfoDB._TABLENAME0, values, "_id=" + id, null) > 0;
    }
    public void deleteAllColumns(){
        db.delete(DataBases.createInfoDB._TABLENAME0,null,null);
    }

    public boolean deleteColumn(long id){
        return db.delete(DataBases.createInfoDB._TABLENAME0,"_id="+id,null)>0;
    }
}
