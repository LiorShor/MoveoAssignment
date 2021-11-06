package com.example.moveoassignment.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Note {
    private String mNoteID;
    private Date mCreationDate;
    private String mTitle;
    private String mBody;
    private LatLng mLocation;

    public Note() {
    }

    public Note(String noteID, Date creationDate, String title, String body, LatLng location) {
        this.mNoteID = noteID;
        this.mCreationDate = creationDate;
        this.mTitle = title;
        this.mBody = body;
        this.mLocation = location;
    }

    public Note(String mNoteID, Date mCreationDate, String mTitle, String mBody) {
        this.mNoteID = mNoteID;
        this.mCreationDate = mCreationDate;
        this.mTitle = mTitle;
        this.mBody = mBody;
    }

    public String getNoteID() {
        return mNoteID;
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
}
