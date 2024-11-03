package com.dkglabs.psychometric_test.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;

import com.dkglabs.base.activities.BaseActivity;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.ui.stateprogressbar.StateProgressBar;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.psychometric_test.R;
import com.dkglabs.psychometric_test.databinding.ActivityPsychometricTestBinding;

public class PsychometricTestActivity extends BaseActivity {

    private FragmentManager fragmentManager;
    private ActivityPsychometricTestBinding binding = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPsychometricTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }

    private void initializeView() {
        fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.navHostFragment);
        //NavController navController = navHostFragment.getNavController();
        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
        viewModel = getViewModel();
        viewModel.setSelectedItem(applyLoanModel);
        viewModel.getSelectedItem().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                ApplyLoanModel applyLoanModel = (ApplyLoanModel) o;
                if (applyLoanModel.getPsyTestDone() == 1) {
                    closeActivity();
                }
            }
        });
    }
}