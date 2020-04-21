package com.example.getitdone.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "phoningitin.db";

    public EventBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table " + EventDbSchema.EventTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                EventDbSchema.EventTable.Cols.UUID + ", " +
                EventDbSchema.EventTable.Cols.TITLE + ", " +
                EventDbSchema.EventTable.Cols.DATE + ", " +
                EventDbSchema.EventTable.Cols.FAVORITED + ", " +
                EventDbSchema.EventTable.Cols.PICTURE + ", " +
                EventDbSchema.EventTable.Cols.TASK1 + ", " +
                EventDbSchema.EventTable.Cols.TASK2 + ", " +
                EventDbSchema.EventTable.Cols.TASK3+ ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
