package com.example.getitdone.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Date;
import java.util.UUID;

public class Event {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mFavorited;
    private byte[] mMemoryPicture;
    private String mTask1;
    private String mTask2;
    private String mTask3;

    public Event()
    {
        this(UUID.randomUUID());
    }

    public Event(UUID id)
    {
        mId = id;
        mDate = new Date();
    }

    public String getTask1() {
        return mTask1;
    }

    public void setTask1(String task1) {
        mTask1 = task1;
    }

    public String getTask2() {
        return mTask2;
    }

    public void setTask2(String task2) {
        mTask2 = task2;
    }

    public String getTask3() {
        return mTask3;
    }

    public void setTask3(String task3) {
        mTask3 = task3;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isFavorited() {
        return mFavorited;
    }

    public void setFavorited(boolean favorited) {
        mFavorited = favorited;
    }

    public byte[] getMemoryPictureBytes() {
        return mMemoryPicture;
    }

    public Bitmap getMemoryPicture() {
        if(mMemoryPicture != null) {
            Bitmap decodedPicture = BitmapFactory.decodeByteArray(mMemoryPicture, 0,
                    mMemoryPicture.length);
            return decodedPicture;
        }

        return null;
    }

    public void setMemoryPicture(byte[] memoryPicture) {
        if(memoryPicture != null) {
            mMemoryPicture = memoryPicture.clone();
        }
    }

    public UUID getId() {
        return mId;
    }
}
