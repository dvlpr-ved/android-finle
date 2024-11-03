package com.dkglabs.e_savari_partner.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.dkglabs.base.activities.BaseActivity;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.e_savari_partner.R;
import com.dkglabs.e_savari_partner.databinding.ActivityDealerBinding;
import com.dkglabs.model.viewmodel.AppViewModel;
import com.dkglabs.remote.utils.AppUrlConstants;

public class DealerActivity extends BaseActivity {

    private FragmentManager fragmentManager;
    private NavController navController;
    private ActivityDealerBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDealerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }

    private void initializeView() {
        fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.navHostFragment);
        navController = navHostFragment.getNavController();

        AppViewModel appViewModel = new AppViewModel();
        viewModel = getViewModel();
        viewModel.setSelectedItem(appViewModel);
        viewModel.getSelectedItem().observe(this, o -> {
            AppViewModel model = (AppViewModel) o;
            if (model.isLogoutPressed()) {
                //TODO : Check if any process or service is running
                PersistentManager.clearAllPreferences();
                Intent intent = new Intent(this, UserAuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            if (model.isTerms()) {
                startWebActivity(this, getString(com.dkglabs.base.R.string.text_terms_of_services), AppUrlConstants.TERMS_CONDITION);
            }

            if (model.isPrivacy()) {
                startWebActivity(this, getString(com.dkglabs.base.R.string.text_privacy_policy), AppUrlConstants.PRIVACY_POLICY);
            }

            if (model.isLanguagePressed()) {
                if (model.isLanguageChanged()) {
                    Intent intent = new Intent(this, SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    navController.popBackStack();
                }
            }
        });
    }
}