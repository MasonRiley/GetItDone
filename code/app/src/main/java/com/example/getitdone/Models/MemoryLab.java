package com.example.getitdone.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.getitdone.database.EventBaseHelper;
import com.example.getitdone.database.EventCursorWrapper;
import com.example.getitdone.database.EventDbSchema.MemoryTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MemoryLab {
    private static MemoryLab sMemoryLab;
    private static int numCrimes = 0;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static MemoryLab get(Context context)
    {
        if(sMemoryLab == null)
        {
            sMemoryLab = new MemoryLab(context);
        }

        return sMemoryLab;
    }

    private MemoryLab(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new EventBaseHelper(mContext).getWritableDatabase();
    }

    public int getNumMemories()
    {
        return numCrimes;
    }

    public List<Memory> getMemories()
    {
        List<Memory> memories = new ArrayList<>();
        EventCursorWrapper cursor = queryMemories(null, null);

        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                memories.add(cursor.getMemory());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return memories;
    }

    public void addMemory(Memory c)
    {
        ContentValues values = getContentValues(c);
        mDatabase.insert(MemoryTable.NAME, null, values);
        numCrimes++;
    }

    public Memory getMemory(UUID id)
    {
        EventCursorWrapper cursor = queryMemories(MemoryTable.Cols.UUID + " =?",
                new String[] { id.toString()});
        try{
            if(cursor.getCount() == 0)
            {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getMemory();
        }finally {
            cursor.close();
        }
    }

    public void updateMemory(Memory memory) {
        String uuidString = memory.getId().toString();
        ContentValues values = getContentValues(memory);
        mDatabase.update(MemoryTable.NAME, values, MemoryTable.Cols.UUID + " =?",
                new String[]{ uuidString });
    }

    public void deleteMemory(Memory c) {
        String whereClause = MemoryTable.Cols.UUID + " = ?";
        String[] whereArg = {c.getId().toString()};
        mDatabase.delete(MemoryTable.NAME, whereClause, whereArg);
        numCrimes--;
    }

    private static ContentValues getContentValues(Memory memory)
    {
        ContentValues values = new ContentValues();
        values.put(MemoryTable.Cols.UUID, memory.getId().toString());
        values.put(MemoryTable.Cols.TITLE, memory.getTitle());
        values.put(MemoryTable.Cols.DATE, memory.getDate().getTime());
        values.put(MemoryTable.Cols.FAVORITED, memory.isFavorited() ? 1 : 0);
        values.put(MemoryTable.Cols.PICTURE, memory.getMemoryPictureBytes());

        return values;
    }

    private EventCursorWrapper queryMemories(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(MemoryTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        return new EventCursorWrapper(cursor);
    }
}
