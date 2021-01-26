package com.michael.rbccodeassignment.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.michael.rbccodeassignment.databinding.ResultsFragmentBinding;
import com.michael.rbccodeassignment.model.CustomList;
import com.michael.rbccodeassignment.model.Restaurant;
import com.michael.rbccodeassignment.ui.adapters.CustomExpandableListAdapter;
import com.michael.rbccodeassignment.ui.adapters.CustomSpinnerAdapter;
import com.michael.rbccodeassignment.ui.viewmodels.HomeViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ResultsFragment extends Fragment {

    private HomeViewModel mViewModel;
    private ResultsFragmentBinding binding;
    private ArrayList<String> categories = new ArrayList<>();
    private HashMap<String, ArrayList<Restaurant>> restaurants = new HashMap<>();
    private CustomExpandableListAdapter customExpandableListAdapter;
    private CustomSpinnerAdapter spinnerAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ResultsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        mViewModel.searchRestaurants();

        binding.noResults.setVisibility(View.GONE);

        binding.searchDesc.setText("Searching "+ mViewModel.getTerm_param()+" in "+mViewModel.getCity_param());

        setpAdapters();

        mViewModel.getResults().observe(getViewLifecycleOwner(), results -> {
            handleResults(results);
            customExpandableListAdapter.notifyDataSetChanged();
        });

        binding.spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.setSort_param(mViewModel.getSorting_items().get(position));
                mViewModel.searchRestaurants();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mViewModel.getShowProgressBar().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                } else {
                    binding.progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setpAdapters() {
        customExpandableListAdapter =
                new CustomExpandableListAdapter(getContext(), categories, restaurants);
        binding.expandableListView.setAdapter(customExpandableListAdapter);

        spinnerAdapter = new CustomSpinnerAdapter(getContext(), mViewModel.getSorting_items());
        binding.spinnerSort.setAdapter(spinnerAdapter);

        //Choosing the selection before to stop onItemSelected to execute while listner initialize
        binding.spinnerSort.setSelection(0,false);
    }

    private void handleResults(CustomList results){
        categories.clear();
        restaurants.clear();

        if(results==null || results.getCategories()==null || results.getRestaurants()==null){
            binding.noResults.setVisibility(View.VISIBLE);
        }
        else{
            binding.noResults.setVisibility(View.GONE);
            categories.addAll(results.getCategories());
            restaurants.putAll(results.getRestaurants());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.getShowProgressBar().removeObservers(getActivity());
        mViewModel.getResults().removeObservers(getActivity());
    }
}