package com.dkglabs.e_savari.activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.dkglabs.base.activities.BaseActivity;
import com.dkglabs.base.manager.LanguageManager;
import com.dkglabs.e_savari.R;
import com.dkglabs.e_savari.databinding.ActivityMainBinding;
import com.dkglabs.model.viewmodel.AppViewModel;
import com.dkglabs.remote.utils.AppUrlConstants;

/**
 * Created by Himanshu Srivastava on 3/22/2023.
 */
public class MainActivity extends BaseActivity {

    private FragmentManager fragmentManager;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private ActivityMainBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initialize();
    }

    private void initialize() {
        fragmentManager = getSupportFragmentManager();
        navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.navHostFragment);
        navController = navHostFragment.getNavController();

        AppViewModel appViewModel = new AppViewModel();
        viewModel = getViewModel();
        viewModel.setSelectedItem(appViewModel);
        viewModel.getSelectedItem().observe(this, o -> {
            AppViewModel model = (AppViewModel) o;

            if (model.isTerms()) {
                startWebActivity(this, getString(com.dkglabs.base.R.string.text_terms_of_services), AppUrlConstants.TERMS_CONDITION);
            }

            if (model.isPrivacy()) {
                startWebActivity(this, getString(com.dkglabs.base.R.string.text_privacy_policy), AppUrlConstants.PRIVACY_POLICY);
            }

            if (model.isLanguagePressed()) {
                if (model.isLanguageChanged()) {
                    recreate();
                } else {
                    navController.navigate(R.id.action_languageFragment_to_termsFragment);
                }
            }

            if (model.isTermsAgree()) {
                startActivity(AppGuideActivity.class);
                closeAllActivity();
            }
        });

        if (LanguageManager.getUserLanguageChanged()) {
            navController.navigate(R.id.action_languageFragment_to_termsFragment);
        }
    }
}