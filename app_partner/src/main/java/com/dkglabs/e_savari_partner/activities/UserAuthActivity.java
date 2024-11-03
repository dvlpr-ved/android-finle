package com.dkglabs.e_savari_partner.activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.dkglabs.auth.viewmodel.AuthViewModel;
import com.dkglabs.base.activities.BaseActivity;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.e_savari_partner.R;
import com.dkglabs.e_savari_partner.databinding.ActivityUserAuthBinding;
import com.dkglabs.remote.utils.ServerConst;

/**
 * Created by Himanshu Srivastava on 31,March,2023
 * Project e_savari_customer
 */
public class UserAuthActivity extends BaseActivity {

    private AuthViewModel authViewModel;
    private FragmentManager fragmentManager;
    private NavController navController;
    private ActivityUserAuthBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }

    private void initializeView() {
        fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.navHostPhone);
        navController = navHostFragment.getNavController();
        authViewModel = new AuthViewModel();
        viewModel = getViewModel();
        viewModel.setSelectedItem(authViewModel);
        viewModel.getSelectedItem().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                validateAuth(o);
            }
        });
    }

    private void validateAuth(Object o) {
        AuthViewModel authViewModel = (AuthViewModel) o;
        if (authViewModel.getAuthSuccess() || authViewModel.getRegisterSuccess()) {
            validateUserType(authViewModel.getUserType());
        } else if (authViewModel.getUserNotFound()) {
            UIUtils.showSnackbar(binding.getRoot(), "User not found");
            navController.popBackStack();
        }
    }

    private void validateUserType(String userType) {
        switch (userType) {
            case ServerConst.CATEGORY_CONSUMER:
                //openOtherApp(this, "com.dkglabs.e_savari");
                UIUtils.showSnackbar(binding.getRoot(), "Partner not found");
                navController.popBackStack();
                break;
            case ServerConst.CATEGORY_DEALER:
                startActivity(DealerActivity.class);
                closeAllActivity();
                break;
        }

    }
}