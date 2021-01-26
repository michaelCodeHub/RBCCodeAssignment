package com.michael.rbccodeassignment.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.michael.rbccodeassignment.api.YelpRepository;
import com.michael.rbccodeassignment.model.CustomList;
import com.michael.rbccodeassignment.model.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private String term_param = "food";
    private String city_param = "toronto";
    private String sort_param = "rating";
    private final int count_param = 10;

    private MutableLiveData<Boolean> showProgressBar;
    private MutableLiveData<CustomList> results;
    private HashMap<String, String> sorting_items;

    public void init(){
        showProgressBar = new MutableLiveData<>(false);
        results = new MutableLiveData<>();
        sorting_items = new HashMap<>();

        initializeSortingItems();
    }

    public void searchRestaurants(){
        //show spinner
        showProgressBar.postValue(true);
        new Thread(() -> {

            //Getting the results and post
            CustomList businessItems = YelpRepository.getInstance().getBusinessItems
                    (count_param ,term_param, city_param, sort_param);
            results.postValue(businessItems);

            //stop spinner
            showProgressBar.postValue(false);
        }).start();
    }

    private void initializeSortingItems(){
        sorting_items.put("Rating", "rating");
        sorting_items.put("Best Match", "best_match");
        sorting_items.put("Review Count", "review_count");
        sorting_items.put("Distance", "distance");
    }

    public String getTerm_param() {
        return term_param;
    }

    public void setTerm_param(String term_param) {
        this.term_param = term_param;
    }

    public String getCity_param() {
        return city_param;
    }

    public void setCity_param(String city_param) {
        this.city_param = city_param;
    }

    public String getSort_param() {
        return sort_param;
    }

    public void setSort_param(String sort_param) {
        this.sort_param = sorting_items.get(sort_param);
    }

    public MutableLiveData<Boolean> getShowProgressBar() {
        return showProgressBar;
    }

    public void setShowProgressBar(MutableLiveData<Boolean> showProgressBar) {
        this.showProgressBar = showProgressBar;
    }

    public MutableLiveData<CustomList> getResults() {
        return results;
    }

    public void setResults(MutableLiveData<CustomList> results) {
        this.results = results;
    }

    public List<String> getSorting_items() {
        List<String> keys = new ArrayList<>(sorting_items.keySet());
        return keys;
    }

    public void setSorting_items(HashMap<String, String> sorting_items) {
        this.sorting_items = sorting_items;
    }
}