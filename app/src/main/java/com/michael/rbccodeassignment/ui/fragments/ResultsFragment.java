package com.michael.rbccodeassignment.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.michael.rbccodeassignment.databinding.HomeFragmentBinding;
import com.michael.rbccodeassignment.databinding.ResultsFragmentBinding;
import com.michael.rbccodeassignment.ui.viewmodels.HomeViewModel;

public class ResultsFragment extends Fragment {

    private HomeViewModel mViewModel;
    private ResultsFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ResultsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }
}