package com.michael.rbccodeassignment.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.michael.rbccodeassignment.MainActivity;
import com.michael.rbccodeassignment.databinding.DetailsFragmentBinding;
import com.michael.rbccodeassignment.model.Restaurant;
import com.michael.rbccodeassignment.ui.viewmodels.ActivityViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailsFragment extends Fragment {

    private ActivityViewModel mViewModel;
    private DetailsFragmentBinding binding;
    private Restaurant restaurant;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DetailsFragmentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(ActivityViewModel.class);

        //Hiding the actionbar
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).hide();

        //GGetting the restaurant object from the arguments
        restaurant = (Restaurant) getArguments().getSerializable(
                MainActivity.SERIALIZABLE_KEY);

        binding.imageProgress.setVisibility(View.VISIBLE);
        binding.itemTitle.setText(restaurant.getName());
        binding.itemReview.setText("Rating ("+restaurant.getReviewCount()+")");
        binding.itemRating.setRating(restaurant.getRating());

        binding.buttonCall.setOnClickListener(view -> {
            Uri number = Uri.parse("tel:"+restaurant.getDisplayPhone());
            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            startActivity(callIntent);
        });

        binding.buttonLink.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.getUrl()));
            startActivity(browserIntent);
        });

        binding.buttonMap.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" +
                    ""+ restaurant.getLatitude()+","+restaurant.getLongitude()));
            startActivity(browserIntent);
        });

        binding.imageBack.setOnClickListener(view -> {
            (getActivity()).onBackPressed();
        });

        //Image loader for restaurant image from url
        Picasso.get()
                .load(restaurant.getImageURL())
                .into(binding.itemImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        binding.imageProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        binding.imageProgress.setVisibility(View.GONE);
                    }
                });

    }
}