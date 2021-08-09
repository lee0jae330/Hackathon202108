package com.example.hackathon202108;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.provider.UserDictionary;

public final class DataBases {

    public static final class createInfoDB implements BaseColumns{
        public static final String NAME = "name";
        public static final String STARTTIME = "startTime";
        public static final String ENDTIME = "endTime";
        public static final String _TABLENAME0 = "userTable";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                + "_id integer PRIMARY KEY autoincrement, "
                + NAME+" text not null , "
                + STARTTIME+" integer not null , "
                + ENDTIME+" integer not null );";
    }

    public static final class createDateDB implements BaseColumns{
        public static final String DATE = "date";
        public static final String STARTBEDTIME = "startBedTime";
        public static final String ENDBEDTIME = "endBedTime";
        public static final String _TABLENAME1 = "userTable";
        public static final String _CREATE1 = "create table if not exists "+_TABLENAME1+"("
                + "_id integer PRIMARY KEY autoincrement, "
                + DATE+" text not null , "
                + STARTBEDTIME+" integer not null , "
                + ENDBEDTIME+" integer not null );";
    }

    public static final class createTimeDB implements BaseColumns{
        public static final String DATE = "date";
        public static final String STARTOFFTIME = "startOffTime";
        public static final String ENDOFFTIME = "endOffTime";
        public static final String _TABLENAME2 = "userTable";
        public static final String _CREATE2 = "create table if not exists "+_TABLENAME2+"("
                + "_id integer PRIMARY KEY autoincrement, "
                + DATE+" text not null , "
                + STARTOFFTIME+" integer not null , "
                + ENDOFFTIME+" integer not null );";
    }
}
