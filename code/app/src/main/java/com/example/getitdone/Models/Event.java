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

    public Event()
    {
        this(UUID.randomUUID());
    }

    public Event(UUID id)
    {
        mId = id;
        mDate = new Date();
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
