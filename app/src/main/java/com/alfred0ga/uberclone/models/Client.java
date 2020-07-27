package com.alfred0ga.uberclone.models;

public class Client {
    private String mId;
    private String mName;
    private String mEmail;
    private String mImage;

    public Client() {
    }

    public Client(String id, String name, String email) {
        mId = id;
        mName = name;
        mEmail = email;
    }

    public Client(String mId, String mName, String mEmail, String mImage) {
        this.mId = mId;
        this.mName = mName;
        this.mEmail = mEmail;
        this.mImage = mImage;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }
}
