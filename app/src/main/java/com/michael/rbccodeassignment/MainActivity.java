package com.michael.rbccodeassignment;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.michael.rbccodeassignment.databinding.ActivityMainBinding;
import com.michael.rbccodeassignment.model.Restaurant;
import com.michael.rbccodeassignment.ui.viewmodels.ActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityViewModel mViewModel;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private NavController navController;
    public static String SERIALIZABLE_KEY = "serializable_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        mViewModel.init();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    public void callDetailsFrag(Restaurant restaurant){
        Bundle bundle = new Bundle();
        bundle.putSerializable(SERIALIZABLE_KEY, restaurant);
        navController.navigate(R.id.action_resultsFragment_to_detailsFragment,
                bundle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}