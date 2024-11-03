package com.dkglabs.e_savari.activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.dkglabs.auth.viewmodel.AuthViewModel;
import com.dkglabs.base.activities.BaseActivity;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.e_savari.R;
import com.dkglabs.e_savari.databinding.ActivityUserAuthBinding;
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
            if (authViewModel.getUserType().equals(ServerConst.CATEGORY_CONSUMER)) {
                startActivity(HomeActivity.class);
                closeAllActivity();
            } else {
                UIUtils.showSnackbar(binding.getRoot(), "User not found");
                //openOtherApp(this, "com.dkglabs.e_savari_partner");
                navController.popBackStack();
            }
        } else if (authViewModel.getUserNotFound()) {
            Bundle bundle = new Bundle();
            bundle.putString("mobile_number", authViewModel.getUserMobile());
            navController.navigate(com.dkglabs.auth.R.id.registerUserFragment, bundle);
        }
    }
}