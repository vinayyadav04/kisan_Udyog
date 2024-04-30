package com.example.kisan_udyog.models;

public class FarmerPost {

    String fImage,fDescription,fLikes,fUsername,fprofile_pic,fid,fTime;
    public FarmerPost(){

    }

    public FarmerPost(String fImage, String fDescription, String fLikes, String fUsername, String fprofile_pic, String fid, String fTime) {
        this.fImage = fImage;
        this.fDescription = fDescription;
        this.fLikes = fLikes;
        this.fUsername = fUsername;
        this.fprofile_pic = fprofile_pic;
        this.fid = fid;
        this.fTime = fTime;
    }

    public String getfImage() {
        return fImage;
    }

    public void setfImage(String fImage) {
        this.fImage = fImage;
    }

    public String getfDescription() {
        return fDescription;
    }

    public void setfDescription(String fDescription) {
        this.fDescription = fDescription;
    }

    public String getfLikes() {
        return fLikes;
    }

    public void setfLikes(String fLikes) {
        this.fLikes = fLikes;
    }

    public String getfUsername() {
        return fUsername;
    }

    public void setfUsername(String fUsername) {
        this.fUsername = fUsername;
    }

    public String getFprofile_pic() {
        return fprofile_pic;
    }

    public void setFprofile_pic(String fprofile_pic) {
        this.fprofile_pic = fprofile_pic;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getfTime() {
        return fTime;
    }

    public void setfTime(String fTime) {
        this.fTime = fTime;
    }
}
