package com.dkglabs.apply_loan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentPreviewLoanDetailsBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoanResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.LoanManager;

public class PreviewLoanDetailsFragment extends BaseFragment implements View.OnClickListener, ResponseListener {
    private final int REQ_LOAN_DETAIL = 1001;

    private FragmentPreviewLoanDetailsBinding binding = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPreviewLoanDetailsBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLoanResponse(LoanPersistentManager.getLoanResponse());
    }

    private void initializeView() {
        binding.buttonOk.setOnClickListener(this);

        binding.setIsRefreshing(true);
        LoanManager.loanDetails(REQ_LOAN_DETAIL, PersistentManager.getUserResponse().getUserId(), LoanPersistentManager.getLoanId(), this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == binding.buttonNotOk.getId()) {
            Navigation.findNavController(binding.getRoot()).navigate(PreviewLoanDetailsFragmentDirections.actionPreviewLoanDetailsFragmentToDealerMappingFragment());
        } else if (id == binding.buttonOk.getId()) {
            Navigation.findNavController(binding.getRoot()).navigate(PreviewLoanDetailsFragmentDirections.actionPreviewLoanDetailsFragmentToPsychometricStatusFragment());
        }
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        switch (requestCode) {
            case REQ_LOAN_DETAIL:
                LoanResponse loanResponse = (LoanResponse) response.getResponseData();
                LoanPersistentManager.setLoanResponse(loanResponse);
                binding.setLoanResponse(loanResponse);
                break;
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        UIUtils.showToast(context, message);
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        UIUtils.showToast(context, getString(com.dkglabs.base.R.string.generic_error_msg));
    }

    @Override
    public void commonCall(int requestCode) {
        switch (requestCode) {
            case REQ_LOAN_DETAIL:
                binding.setIsRefreshing(false);
                break;
        }
    }
}
