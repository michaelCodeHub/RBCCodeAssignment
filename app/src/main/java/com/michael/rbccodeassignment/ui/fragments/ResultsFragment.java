package com.michael.rbccodeassignment.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.michael.rbccodeassignment.databinding.ResultsFragmentBinding;
import com.michael.rbccodeassignment.model.CustomList;
import com.michael.rbccodeassignment.model.Restaurant;
import com.michael.rbccodeassignment.ui.adapters.CustomExpandableListAdapter;
import com.michael.rbccodeassignment.ui.adapters.CustomSpinnerAdapter;
import com.michael.rbccodeassignment.ui.viewmodels.ActivityViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ResultsFragment extends Fragment {

    private ActivityViewModel mViewModel;
    private ResultsFragmentBinding binding;
    private ArrayList<String> categories = new ArrayList<>();
    private final HashMap<String, ArrayList<Restaurant>> restaurants = new HashMap<>();
    private CustomExpandableListAdapter customExpandableListAdapter;
    private CustomSpinnerAdapter spinnerAdapter;
    private int spinnerCheck = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ResultsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(ActivityViewModel.class);

        //Show the actionbar
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).show();

        init();

        binding.spinnerSort.setOnItemSelectedListener(spinnerItemClickListener);

        binding.buttonRefresh.setOnClickListener(view ->{
            mViewModel.searchRestaurants();
        });

        //Updates the listview
        mViewModel.getResults().observe(getActivity(), results -> {
            if(mViewModel.getShowProgressBar().getValue()){
                handleResults(results);
                customExpandableListAdapter.notifyDataSetChanged();
            }
        });

        //Shows and hides progressbar depends on the mutable data
        mViewModel.getShowProgressBar().observe(getActivity(), aBoolean -> {
            if(aBoolean) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        mViewModel.getShowNoResult().observe(getActivity(), aBoolean -> {
            if(aBoolean) {
                binding.noResults.setVisibility(View.VISIBLE);
                binding.buttonRefresh.setVisibility(View.VISIBLE);
            } else {
                binding.noResults.setVisibility(View.GONE);
                binding.buttonRefresh.setVisibility(View.GONE);
            }
        });

        mViewModel.searchRestaurants();
    }

    /**
     * When the user changes the sorting items, the listner
     * triggers a search api call with the updated sort item
     */
    private final AdapterView.OnItemSelectedListener spinnerItemClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(++spinnerCheck > 1) {
                mViewModel.setSort_param(mViewModel.getSorting_items().get(position));
                mViewModel.searchRestaurants();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    /**
     * Hiding the keyboard incase if the keyboard was open in the last fragment
     * Setting up the adapter for listview and the spinner
     */
    private void init() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle("Searching "+ mViewModel.getTerm_param()+" in "+mViewModel.getCity_param());

        customExpandableListAdapter =
                new CustomExpandableListAdapter(getContext(), categories, restaurants);
        binding.expandableListView.setAdapter(customExpandableListAdapter);

        spinnerAdapter = new CustomSpinnerAdapter(getContext(), mViewModel.getSorting_items());
        binding.spinnerSort.setAdapter(spinnerAdapter);
    }

    /**
     * Clears the existing item in the listview
     * validates the results
     * if validation fails then shows the textview with No results found
     * Otherwise updates the listview
     */
    private void handleResults(CustomList results){
        categories.clear();
        restaurants.clear();

        if(!validateResults(results)){
            mViewModel.setShowNoResult(true);
        }
        else{
            mViewModel.setShowNoResult(false);
            categories.addAll(results.getCategories());
            restaurants.putAll(results.getRestaurants());
        }
    }

    /**
     * Return false if there is no data in the results
     * Return true otherwise
     * @param results CustomList contains both hashmap and list
     * @return boolean
     */
    private boolean validateResults(CustomList results){
        if(results.getCategories()==null || results.getRestaurants()==null){
            return false;
        }
        else return results.getRestaurants().size() > 0 && results.getCategories().size() > 0;
    }

    /**
     * Clear/Remove the observers to stop memory leaks
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mViewModel!=null){
            mViewModel.getShowProgressBar().removeObservers(getActivity());
            mViewModel.getResults().removeObservers(getActivity());
            mViewModel.getShowNoResult().removeObservers(getActivity());
        }
    }
}