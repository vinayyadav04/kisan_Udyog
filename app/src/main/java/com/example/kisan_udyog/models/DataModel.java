package com.example.kisan_udyog.models;

import java.util.ArrayList;
import java.util.List;

public class DataModel {
    private String name;
    private String imgUrl;
    private ArrayList<String> type = new ArrayList<>();
    private String description;

    public DataModel() {
    }

    public DataModel(String name, String imgUrl, ArrayList<String> type, String description) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.type = type;
        this.description = description;
    }


    public ArrayList<String> getType() {
        return type;
    }

    public void setType(ArrayList<String> type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
