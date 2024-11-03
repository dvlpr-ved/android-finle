package com.dkglabs.products.activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.dkglabs.base.activities.BaseActivity;
import com.dkglabs.products.R;
import com.dkglabs.products.databinding.ActivityProductBinding;

public class ProductActivity extends BaseActivity {


    private FragmentManager fragmentManager;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private ActivityProductBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }

    private void initializeView() {
        fragmentManager = getSupportFragmentManager();
        navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.navHostFragment);
        navController = navHostFragment.getNavController();
    }
}