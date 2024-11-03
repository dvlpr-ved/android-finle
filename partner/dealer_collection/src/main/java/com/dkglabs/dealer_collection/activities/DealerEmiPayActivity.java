package com.dkglabs.dealer_collection.activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.dkglabs.dealer_collection.R;
import com.dkglabs.dealer_collection.databinding.ActivityDealerEmiPaymentContainerBinding;

public class DealerEmiPayActivity extends BaseDealerActivity {
    ActivityDealerEmiPaymentContainerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDealerEmiPaymentContainerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }

    private void initializeView() {
        setUpBackToolbar("EMI Details");
        setProgressIndicator(binding.toolbarLayout.progressBar);
        setView(binding.getRoot());
    }
}