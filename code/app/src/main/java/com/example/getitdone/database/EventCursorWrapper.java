package com.example.getitdone.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.getitdone.Models.Memory;

import java.util.Date;
import java.util.UUID;

public class EventCursorWrapper extends CursorWrapper {

    public EventCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Memory getMemory()
    {
        String uuidString = getString(getColumnIndex(EventDbSchema.MemoryTable.Cols.UUID));
        String title = getString(getColumnIndex(EventDbSchema.MemoryTable.Cols.TITLE));
        long date = getLong(getColumnIndex(EventDbSchema.MemoryTable.Cols.DATE));
        int isFavorited = getInt(getColumnIndex(EventDbSchema.MemoryTable.Cols.FAVORITED));
        byte[] pictureBytes = getBlob(getColumnIndex(EventDbSchema.MemoryTable.Cols.PICTURE));

        Memory memory = new Memory(UUID.fromString(uuidString));
        memory.setTitle(title);
        memory.setDate(new Date(date));
        memory.setFavorited(isFavorited != 0);
        memory.setMemoryPicture(pictureBytes);

        return memory;

    }
}
