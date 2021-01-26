package com.michael.rbccodeassignment.ui.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.michael.rbccodeassignment.R;
import com.michael.rbccodeassignment.databinding.HomeFragmentBinding;
import com.michael.rbccodeassignment.ui.viewmodels.ActivityViewModel;

import java.net.InetAddress;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private ActivityViewModel mViewModel;
    private HomeFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(ActivityViewModel.class);

        //Show the actionbar
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).show();

        binding.buttonSubmit.setOnClickListener(onSubmitClick);
    }

    private final View.OnClickListener onSubmitClick = view -> {
        if(doValidation()){
            // calling to result fragment once the validation passes
            NavHostFragment.findNavController(HomeFragment.this)
                    .navigate(R.id.action_homeFragment_to_resultsFragment);
        }
    };

    /**
     * Show error if the term field is null
     * Show error if city field is null
     * if both not null then store the data in the viewmodel
     * @return boolean
     */
    private boolean doValidation(){
        if(binding.eTxtTerm.getText() == null || binding.eTxtTerm.getText().toString().equals("")){
            binding.eTxtTerm.setError("Please enter a search term");
            return false;
        }
        else if(binding.eTxtCity.getText() == null || binding.eTxtCity.getText().toString().equals("")){
            binding.eTxtCity.setError("Please enter a location");
            return false;
        }
        else{
            mViewModel.setTerm_param(binding.eTxtTerm.getText().toString());
            mViewModel.setCity_param(binding.eTxtCity.getText().toString());
            return true;
        }
    }
}