package com.example.moveoassignment.model;

import androidx.annotation.Nullable;

import java.util.Date;

public class Note implements Comparable<Note> {
    private String noteID;
    private Date mCreationDate;
    private String mTitle;
    private String mBody;
    private LatLng mLocation;

    public Note(String noteID, Date creationDate, String title, String body, LatLng location) {
        this.noteID = noteID;
        this.mCreationDate = creationDate;
        this.mTitle = title;
        this.mBody = body;
        this.mLocation = location;
    }

    public Note() {
    }

    public String getNoteID() {
        return noteID;
    }

    public void setCreationDate(Date mCreationDate) {
        this.mCreationDate = mCreationDate;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setBody(String mBody) {
        this.mBody = mBody;
    }

    public void setLocation(LatLng mLocation) {
        this.mLocation = mLocation;
    }

    public Date getCreationDate() {
        return mCreationDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    public LatLng getLocation() {
        return mLocation;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean equals = false;
        if (obj instanceof Note) {
            equals = this.noteID.equals(((Note) obj).noteID);
        }
        return equals;
    }

    @Override
    public int compareTo(Note note) {
        return this.mCreationDate.compareTo(note.getCreationDate());
    }
}
