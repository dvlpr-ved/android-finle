package com.dkglabs.apply_loan.activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.ActivityApplyLoanBinding;
import com.dkglabs.base.activities.BaseActivity;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.ui.stateprogressbar.StateProgressBar;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.remote.utils.ServerConst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ApplyLoanActivity extends BaseActivity {

    private FragmentManager fragmentManager;
    private NavController navController;
    private ActivityApplyLoanBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApplyLoanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }

    private void initializeView() {
        ArrayList<String> loanSteps = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_loan_steps)));
        binding.stateProgressBar.setStateDescriptionData(loanSteps);

        fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.navHostFragment);
        navController = navHostFragment.getNavController();
        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
        viewModel = getViewModel();
        viewModel.setSelectedItem(applyLoanModel);
        viewModel.getSelectedItem().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                ApplyLoanModel applyLoanModel = (ApplyLoanModel) o;
                if (applyLoanModel.getExitApply()) {
                    UIUtils.showToast(getContext(), getString(R.string.loan_application_canceled));
                    LoanPersistentManager.removeLoanResponse();
                    closeActivity();
                }

                if (applyLoanModel.getLoanState() != 0) {
                    UIUtils.showView(binding.stateProgressBarLayout);
                    switch (applyLoanModel.getLoanState()) {
                        case 1:
                            binding.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                            break;
                        case 2:
                            binding.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                            break;
                        case 3:
                            binding.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                            break;
                        case 4:
                            binding.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                            break;
                        case 5:
                            binding.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
                            break;
                        case 6:
                            binding.stateProgressBar.setAllStatesCompleted(true);
                            break;
                        case 7:
                            UIUtils.hideViewGone(binding.stateProgressBarLayout);
                            break;
                    }
                }

                if (applyLoanModel.getPsyTestDone() == 1) {
                    navController.navigate(R.id.userEvScoreFragment);
                }

                if (applyLoanModel.getContinueApply()) {
                    validateLoanStageFlag(LoanPersistentManager.getLoanResponse().getLoanStageFlag());
                }
            }
        });
    }

    private void validateLoanStageFlag(String loanStageFlag) {
        if (loanStageFlag.isEmpty()) {
            navController.navigate(R.id.basicKycPanFragment);
            return;
        }

        String[] flags = loanStageFlag.split(",");
        Map<String, Boolean> map = new HashMap<>();
        for (String flag : flags) {
            map.put(flag, true);
        }

        if (!map.containsKey(ServerConst.STAGE_PAN)) {
            navController.navigate(R.id.basicKycPanFragment);
        } else if (!map.containsKey(ServerConst.STAGE_AADHAAR_FRONT) && !map.containsKey(ServerConst.STAGE_ADDRESS_DTLS)) {
            navController.navigate(R.id.basicKycAadhaarOtpFragment);
        } else if (!map.containsKey(ServerConst.STAGE_LICENCE)) {
            navController.navigate(R.id.basicKycDlFragment);
        } else if (!map.containsKey(ServerConst.STAGE_INCOME_DTLS) && !map.containsKey(ServerConst.STAGE_LOAN_DTLS) && !map.containsKey(ServerConst.STAGE_COLLATERAL_DTLS)) {
            navController.navigate(R.id.loanDetailFragment);
        } else if (!map.containsKey(ServerConst.STAGE_PERSONAL_DTLS)) {
            navController.navigate(R.id.applicantDetailFragment);
        } else if (!map.containsKey(ServerConst.STAGE_CONTACT_DTLS)) {
            navController.navigate(R.id.loanUserContactFragment);
        } else/* if (!map.containsKey(ServerConst.STAGE_ADDRESS_DTLS)) {
            navController.navigate(R.id.loanUserAddressFragment);
        } else*/ if (!map.containsKey(ServerConst.STAGE_BANK_DTLS)) {
            navController.navigate(R.id.loanUserBankFragment);
        } else if (!map.containsKey(ServerConst.STAGE_DOCUMENT_DTLS)) {
            navController.navigate(R.id.electricityBillFragment);
        } else if (!map.containsKey(ServerConst.STAGE_VIDEO_KYC)) {
            navController.navigate(R.id.loanVideoKYCFragment);
        } else {
            navController.navigate(R.id.loanUserDocumentFragment);
        }
    }
}