package com.michael.rbccodeassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.michael.rbccodeassignment.databinding.ActivityMainBinding;
import com.michael.rbccodeassignment.model.Restaurant;
import com.michael.rbccodeassignment.ui.viewmodels.HomeViewModel;

public class MainActivity extends AppCompatActivity {

    private HomeViewModel mViewModel;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mViewModel.init();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    public void callDetailsFrag(Restaurant restaurant){
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", restaurant);
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