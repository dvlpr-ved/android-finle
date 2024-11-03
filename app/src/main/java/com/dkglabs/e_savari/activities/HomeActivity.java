package com.dkglabs.e_savari.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.dkglabs.base.activities.BaseActivity;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.e_savari.R;
import com.dkglabs.e_savari.databinding.ActivityHomeBinding;
import com.dkglabs.model.viewmodel.AppViewModel;
import com.dkglabs.remote.utils.AppUrlConstants;

/**
 * Created by Himanshu Srivastava on 3/30/2023.
 */
public class HomeActivity extends BaseActivity {

    private NavHostFragment navHostFragment;
    private NavController navController;
    private ActivityHomeBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }

    private void initializeView() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if (navDestination.getId() == com.dkglabs.home.R.id.homeFragment
                        || navDestination.getId() == com.dkglabs.dashboard.R.id.dashboardFragment
                        || navDestination.getId() == com.dkglabs.notification.R.id.notificationFragment
                        || navDestination.getId() == com.dkglabs.account.R.id.accountFragment) {
                    UIUtils.showView(binding.navigationView);
                } else {
                    UIUtils.hideViewGone(binding.navigationView);
                }
            }
        });
        NavigationUI.setupWithNavController(binding.navigationView, navController);

        AppViewModel appViewModel = new AppViewModel();
        viewModel = getViewModel();
        viewModel.setSelectedItem(appViewModel);
        viewModel.getSelectedItem().observe(this, o -> {
            AppViewModel model = (AppViewModel) o;
            if (model.isLogoutPressed()) {
                //TODO : Check if any process or service is running
                PersistentManager.clearAllPreferences();
                Intent intent = new Intent(HomeActivity.this, UserAuthActivity.class);
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
                    Intent intent = new Intent(HomeActivity.this, SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    navController.popBackStack();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!navController.popBackStack()) {
            super.onBackPressed();
        }
    }
}