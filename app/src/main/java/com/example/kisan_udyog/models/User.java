package com.example.kisan_udyog.models;

public class User {

    private String email;
    private String username;
    private String city;
    private double latitude;
    private double longitude;
    private long phone_number;
    private String u_id;
    private String profile_photo;
    private String role;

    public User(String email, String username, String city, double latitude, double longitude, long phone_number, String u_id, String profile_photo, String role) {
        this.email = email;
        this.username = username;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone_number = phone_number;
        this.u_id = u_id;
        this.profile_photo = profile_photo;
        this.role = role;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", city='" + city + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", phone_number=" + phone_number +
                ", u_id='" + u_id + '\'' +
                ", profile_photo='" + profile_photo + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
