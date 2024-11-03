package com.dkglabs.dealer_collection.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.dkglabs.base.manager.CollectionPersistentManager;
import com.dkglabs.dealer_collection.R;
import com.dkglabs.dealer_collection.databinding.ActivityCollectionBinding;
import com.dkglabs.dealer_collection.view_model.CollectionViewModel;
import com.dkglabs.model.CollectionCard;

public class CollectionActivity extends BaseDealerActivity {

    private CollectionViewModel mCollectionViewModel;
    private FragmentManager fragmentManager;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private ActivityCollectionBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        binding = ActivityCollectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }

    private void initializeView() {
        setUpBackToolbar("Collection");
        setProgressIndicator(binding.toolbarLayout.progressBar);
        setView(binding.getRoot());

        fragmentManager = getSupportFragmentManager();
        navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.navHostFragment);
        navController = navHostFragment.getNavController();

        if (CollectionPersistentManager.getCollectionCard() != null) {
            updateCard(CollectionPersistentManager.getCollectionCard());
        }

        mCollectionViewModel = new ViewModelProvider(this).get(CollectionViewModel.class);
        mCollectionViewModel.getCollectionCard().observe(this, new Observer<CollectionCard>() {
            @Override
            public void onChanged(CollectionCard collectionCard) {
                updateCard(collectionCard);
            }
        });
    }


    private void updateCard(CollectionCard collectionCard) {
        binding.lastDeposit.setText(getString(com.dkglabs.base.R.string.placeholder_amount, collectionCard.getTotalLastEmiTxnAmount()));
        binding.totalOutstandingEmi.setText(getString(com.dkglabs.base.R.string.placeholder_amount, collectionCard.getTotalLoanAmount()));
        binding.outstandingEmi.setText(getString(com.dkglabs.base.R.string.placeholder_amount, collectionCard.getTotalPendingEmiAmount()));
    }
}