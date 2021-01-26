package com.michael.rbccodeassignment.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.michael.rbccodeassignment.R;
import com.michael.rbccodeassignment.api.YelpRepository;
import com.michael.rbccodeassignment.databinding.DetailsFragmentBinding;
import com.michael.rbccodeassignment.databinding.HomeFragmentBinding;
import com.michael.rbccodeassignment.model.CustomList;
import com.michael.rbccodeassignment.model.Restaurant;
import com.michael.rbccodeassignment.ui.viewmodels.HomeViewModel;
import com.squareup.picasso.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class DetailsFragment extends Fragment {

    private HomeViewModel mViewModel;
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
        mViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        binding.imageProgress.setVisibility(View.VISIBLE);

        restaurant = (Restaurant) getArguments().getSerializable(
                "key");

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
            Uri gmmIntentUri = Uri.parse("geo:"+restaurant.getLatitude()+","+restaurant.getLongitude());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

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