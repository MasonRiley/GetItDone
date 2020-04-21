package com.example.getitdone.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.getitdone.database.EventBaseHelper;
import com.example.getitdone.database.EventCursorWrapper;
import com.example.getitdone.database.EventDbSchema.EventTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventLab {
    private static EventLab sEventLab;
    private static int numEvents = 0;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static EventLab get(Context context)
    {
        if(sEventLab == null)
        {
            sEventLab = new EventLab(context);
        }

        return sEventLab;
    }

    private EventLab(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new EventBaseHelper(mContext).getWritableDatabase();
    }

    public int getNumEvents()
    {
        return numEvents;
    }

    public List<Event> getEvents()
    {
        List<Event> events = new ArrayList<>();
        EventCursorWrapper cursor = queryEvents(null, null);

        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                events.add(cursor.getEvent());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return events;
    }

    public void addEvent(Event c)
    {
        ContentValues values = getContentValues(c);
        mDatabase.insert(EventTable.NAME, null, values);
        numEvents++;
    }

    public Event getEvent(UUID id)
    {
        EventCursorWrapper cursor = queryEvents(EventTable.Cols.UUID + " =?",
                new String[] { id.toString()});
        try{
            if(cursor.getCount() == 0)
            {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getEvent();
        }finally {
            cursor.close();
        }
    }

    public void updateEvent(Event event) {
        String uuidString = event.getId().toString();
        ContentValues values = getContentValues(event);
        mDatabase.update(EventTable.NAME, values, EventTable.Cols.UUID + " =?",
                new String[]{ uuidString });
    }

    public void deleteEvent(Event c) {
        String whereClause = EventTable.Cols.UUID + " = ?";
        String[] whereArg = {c.getId().toString()};
        mDatabase.delete(EventTable.NAME, whereClause, whereArg);
        numEvents--;
    }

    private static ContentValues getContentValues(Event event)
    {
        ContentValues values = new ContentValues();
        values.put(EventTable.Cols.UUID, event.getId().toString());
        values.put(EventTable.Cols.TITLE, event.getTitle());
        values.put(EventTable.Cols.DATE, event.getDate().getTime());
        values.put(EventTable.Cols.FAVORITED, event.isFavorited() ? 1 : 0);
        values.put(EventTable.Cols.PICTURE, event.getMemoryPictureBytes());

        return values;
    }

    private EventCursorWrapper queryEvents(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(EventTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        return new EventCursorWrapper(cursor);
    }
}
