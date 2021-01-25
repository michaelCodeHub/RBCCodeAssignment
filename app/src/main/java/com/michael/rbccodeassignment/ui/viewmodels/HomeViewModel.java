package com.michael.rbccodeassignment.ui.viewmodels;

import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private String term_param = "food";
    private String city_param = "toronto";
    private String sort_param = "rating";
    private int count_param = 10;

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
}