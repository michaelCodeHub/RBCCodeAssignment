package com.michael.rbccodeassignment.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.michael.rbccodeassignment.api.YelpRepository;
import com.michael.rbccodeassignment.model.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeViewModel extends ViewModel {

    private String term_param = "food";
    private String city_param = "toronto";
    private String sort_param = "rating";
    private int count_param = 10;

    private MutableLiveData<Boolean> showSpinner = new MutableLiveData<>(false);
    private MutableLiveData<HashMap<String, ArrayList<Restaurant>>> restaurants = new MutableLiveData<>();

    public void searchRestaurants(){
        //show spinner
        showSpinner.postValue(true);
        new Thread(() -> {
            HashMap<String, ArrayList<Restaurant>> businessItems = YelpRepository.getInstance().getBusinessItems
                    (count_param ,term_param, city_param, sort_param);
            restaurants.postValue(businessItems);

            //stop spinner
            showSpinner.postValue(false);
        }).start();
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
        this.sort_param = sort_param;
    }

    public MutableLiveData<Boolean> getShowSpinner() {
        return showSpinner;
    }

    public void setShowSpinner(MutableLiveData<Boolean> showSpinner) {
        this.showSpinner = showSpinner;
    }

    public MutableLiveData<HashMap<String, ArrayList<Restaurant>>> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(MutableLiveData<HashMap<String, ArrayList<Restaurant>>> restaurants) {
        this.restaurants = restaurants;
    }
}