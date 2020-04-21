package com.example.getitdone.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.getitdone.Models.Event;

import java.util.Date;
import java.util.UUID;

public class EventCursorWrapper extends CursorWrapper {

    public EventCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Event getEvent()
    {

        String uuidString = getString(getColumnIndex(EventDbSchema.EventTable.Cols.UUID));
        String title = getString(getColumnIndex(EventDbSchema.EventTable.Cols.TITLE));
        long date = getLong(getColumnIndex(EventDbSchema.EventTable.Cols.DATE));
        int isFavorited = getInt(getColumnIndex(EventDbSchema.EventTable.Cols.FAVORITED));
        byte[] pictureBytes = getBlob(getColumnIndex(EventDbSchema.EventTable.Cols.PICTURE));
        String task1 = getString(getColumnIndex(EventDbSchema.EventTable.Cols.TASK1));
        String task2 = getString(getColumnIndex(EventDbSchema.EventTable.Cols.TASK2));
        String task3 = getString(getColumnIndex(EventDbSchema.EventTable.Cols.TASK3));

        Event event = new Event(UUID.fromString(uuidString));
        event.setTitle(title);
        event.setDate(new Date(date));
        event.setFavorited(isFavorited != 0);
        event.setMemoryPicture(pictureBytes);
        event.setTask1(task1);
        event.setTask2(task2);
        event.setTask3(task3);

        return event;

    }
}
