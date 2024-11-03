package com.dkglabs.apply_loan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentEnachStatusBinding;
import com.dkglabs.apply_loan.utils.LoanStatusConst;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.ui.stateprogressbar.StateProgressBar;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoanDataResponse;
import com.dkglabs.model.response.LoanResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.LoanManager;
import com.google.android.material.color.MaterialColors;

import java.util.ArrayList;
import java.util.Arrays;

public class EnachStatusFragment extends BaseFragment implements View.OnClickListener, ResponseListener {

    private static final int REQ_LOAN_PAYMENT_DETAILS = 101;
    private LoanResponse loanResponse;

    private FragmentEnachStatusBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loanResponse = LoanPersistentManager.getLoanResponse();

        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                closeActivity();
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEnachStatusBinding.inflate(inflater, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switch (loanResponse.getLoanStatus()) {
            case LoanStatusConst.INCOMPLETE:
                binding.loanStateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                break;
            case LoanStatusConst.AWAITING_APPROVAL:
                binding.loanStateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                break;
            case LoanStatusConst.PENDING:
            case LoanStatusConst.APPROVED:
                binding.loanStateProgressBar.setCurrentStateDescriptionColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreen));
                binding.loanStateProgressBar.setBackgroundColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreenContainer));
                binding.loanStateProgressBar.setForegroundColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreen));
                binding.loanStateProgressBar.setStateDescriptionColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreen));
                binding.loanStateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                break;
            case LoanStatusConst.REJECTED:
                ArrayList<String> loanState = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_loan_state_rejected)));
                binding.loanStateProgressBar.setStateDescriptionData(loanState);
                binding.loanStateProgressBar.setCurrentStateDescriptionColor(MaterialColors.getColor(binding.getRoot(), com.google.android.material.R.attr.colorError));
                binding.loanStateProgressBar.setBackgroundColor(MaterialColors.getColor(binding.getRoot(), com.google.android.material.R.attr.colorErrorContainer));
                binding.loanStateProgressBar.setForegroundColor(MaterialColors.getColor(binding.getRoot(), com.google.android.material.R.attr.colorError));
                binding.loanStateProgressBar.setStateDescriptionColor(MaterialColors.getColor(binding.getRoot(), com.google.android.material.R.attr.colorError));
                binding.loanStateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                break;
//            case LoanStatusConst.PENDING:
//                binding.loanStateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
//                break;
            case LoanStatusConst.ACTIVE_LOAN:
                binding.loanStateProgressBar.setCurrentStateDescriptionColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreen));
                binding.loanStateProgressBar.setBackgroundColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreenContainer));
                binding.loanStateProgressBar.setForegroundColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreen));
                binding.loanStateProgressBar.setStateDescriptionColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreen));
                binding.loanStateProgressBar.setAllStatesCompleted(true);
                break;
            case LoanStatusConst.CLOSED_LOAN:
                binding.loanStateProgressBar.setAllStatesCompleted(true);
                break;

        }

        getLoanPaymentDetails();


    }

    private void initializeView() {
        binding.loanStateTitle.setText(String.format(getString(R.string.hi_loan_status), PersistentManager.getUserResponse().getFirstName()));

        ArrayList<String> loanState = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_loan_state)));
        binding.loanStateProgressBar.setStateDescriptionData(loanState);

        binding.buttonVideoKyc.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == binding.buttonVideoKyc.getId()) {
            Navigation.findNavController(binding.getRoot()).navigate(EnachStatusFragmentDirections.actionEnachStatusFragmentToLoanVideoKYCFragment());
        }

    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        switch (requestCode) {
            case REQ_LOAN_PAYMENT_DETAILS:
                showEnachStatus((LoanDataResponse) response.getResponseData());
                break;
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {

    }

    @Override
    public void onFailure(int requestCode, Throwable t) {

    }

    @Override
    public void commonCall(int requestCode) {
        switch (requestCode) {
            case REQ_LOAN_PAYMENT_DETAILS:
                UIUtils.hideViewGone(binding.llProgressHolder);
        }
    }

    private void getLoanPaymentDetails() {
        UIUtils.showView(binding.llProgressHolder);
        binding.llProgress.setMessage("Checking e-NACH status...");
        LoanManager.getLoanPaymentDetails(REQ_LOAN_PAYMENT_DETAILS,
                PersistentManager.getUserResponse().getUserId(),
                loanResponse.getLoanId(),
                this);
    }


    private void showEnachStatus(LoanDataResponse loanDataResponse) {
        UIUtils.showView(binding.llEnachStatus);
        binding.setLoanDataResponse(loanDataResponse);
        if (loanDataResponse.getEmandateStatus() != null) {
            switch (loanDataResponse.getEmandateStatus()) {
                case LoanStatusConst.ENACH_ACTIVE:
                    binding.enachStatusMessage.setText("Your e-NACH account is active.");
                    binding.buttonVideoKyc.setEnabled(true);
                    break;
                case LoanStatusConst.ENACH_CANCELLED:
                    binding.enachStatusMessage.setText("Your e-NACH process is cancelled.");
                    break;
                case LoanStatusConst.ENACH_INITIALIZED:
                    binding.enachStatusMessage.setText("Your e-NACH process is pending. Please complete e-NACH process. We have share you link on your mobile and email.");
                    break;
                case LoanStatusConst.ENACH_LINK_EXPIRED:
                    binding.enachStatusMessage.setText("Your e-NACH link has expired. Please contact our support team");
                    break;
            }
        } else {
            binding.enachStatusMessage.setText("Your e-NACH is under processing. Please wait for a while.");
        }
    }
}
