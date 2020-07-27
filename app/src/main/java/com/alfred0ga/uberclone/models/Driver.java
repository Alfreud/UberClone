package com.alfred0ga.uberclone.models;

public class Driver {
    private String mId;
    private String mName;
    private String mEmail;
    private String mBrand;
    private String mPlate;
    private String mImage;

    public Driver() {
    }

    public Driver(String id, String name, String email, String brand, String plate) {
        mId = id;
        mName = name;
        mEmail = email;
        mBrand = brand;
        mPlate = plate;
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

    public String getBrand() {
        return mBrand;
    }

    public void setBrand(String mBrand) {
        this.mBrand = mBrand;
    }

    public String getPlate() {
        return mPlate;
    }

    public void setPlate(String mPlate) {
        this.mPlate = mPlate;
    }
}
