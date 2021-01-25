package com.michael.rbccodeassignment.model;

import java.util.ArrayList;

public class Restaurant {

    String id;
    String name;
    String imageURL;
    String isClosed;
    String url;
    ArrayList<String> categories;
    String displayAddress;
    String displayPhone;

    public Restaurant(){

    }

    public Restaurant(String id, String name, String imageURL, String isClosed, String url, ArrayList<String> categories, String displayAddress, String displayPhone) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.isClosed = isClosed;
        this.url = url;
        this.categories = categories;
        this.displayAddress = displayAddress;
        this.displayPhone = displayPhone;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(String isClosed) {
        this.isClosed = isClosed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public String getDisplayAddress() {
        return displayAddress;
    }

    public void setDisplayAddress(String displayAddress) {
        this.displayAddress = displayAddress;
    }

    public String getDisplayPhone() {
        return displayPhone;
    }

    public void setDisplayPhone(String displayPhone) {
        this.displayPhone = displayPhone;
    }


}
