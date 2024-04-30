package com.example.kisan_udyog.models;

public class PostModel {

    String pImage, pTitle, pDescription,pQuantity,pPrice,pUsername,profile_pic,uid,status,pTime,phoneNumber,city,pId,bId;

    public PostModel() {
    }

    public PostModel(String pImage, String pTitle, String pDescription, String pQuantity, String pPrice, String pUsername, String profile_pic, String uid, String status, String pTime, String phoneNumber, String city, String pId, String bId) {
        this.pImage = pImage;
        this.pTitle = pTitle;
        this.pDescription = pDescription;
        this.pQuantity = pQuantity;
        this.pPrice = pPrice;
        this.pUsername = pUsername;
        this.profile_pic = profile_pic;
        this.uid = uid;
        this.status = status;
        this.pTime = pTime;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.pId = pId;
        this.bId = bId;
    }

    public String getpId() {
        return pId;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getpQuantity() {
        return pQuantity;
    }

    public String getpUsername() {
        return pUsername;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public void setpUsername(String pUsername) {
        this.pUsername = pUsername;
    }

    public void setpQuantity(String pQuantity) {
        this.pQuantity = pQuantity;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }
}
