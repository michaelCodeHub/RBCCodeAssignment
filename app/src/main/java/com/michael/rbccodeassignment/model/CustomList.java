package com.michael.rbccodeassignment.model;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomList {
    HashMap<String, ArrayList<Restaurant>> restaurants = new HashMap<>();
    ArrayList<String> categories = new ArrayList<>();

    public HashMap<String, ArrayList<Restaurant>> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(HashMap<String, ArrayList<Restaurant>> restaurants) {
        this.restaurants = restaurants;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }
}
