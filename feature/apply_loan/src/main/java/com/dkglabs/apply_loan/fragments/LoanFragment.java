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
import com.dkglabs.apply_loan.databinding.FragmentLoanApplyNowBinding;
import com.dkglabs.apply_loan.databinding.FragmentLoanBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoanResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.LoanManager;

public class LoanFragment extends BaseFragment implements View.OnClickListener, ResponseListener {

    private FragmentLoanBinding binding = null;

    public LoanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finish();
            }
        };
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!LoanPersistentManager.getLoanId().equals("")) {
            getLoanDetails();
        } else {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loanFragment_to_nav_apply_loan);
        }

    }

    private void getLoanDetails() {
        binding.llProgress.setMessage(getString(R.string.loading_loan_details));
        LoanManager.loanDetails(1001, PersistentManager.getUserResponse().getUserId(), PersistentManager.getUserResponse().getLoanId(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoanBinding.inflate(inflater, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (binding != null) {
            binding = null;
        }
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        LoanResponse loanResponse = (LoanResponse) response.getResponseData();
        LoanPersistentManager.setLoanId(loanResponse.getLoanId());
        LoanPersistentManager.setLoanResponse(loanResponse);
        if (loanResponse.getApplicationCompletionStatus().equals("Y") && binding != null)
            Navigation.findNavController(binding.getRoot()).navigate(LoanFragmentDirections.actionLoanFragmentToNavAcceptLoan());
        else if (binding != null)
            Navigation.findNavController(binding.getRoot()).navigate(LoanFragmentDirections.actionLoanFragmentToPendingLoanDialogFragment());
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {

    }

    @Override
    public void onFailure(int requestCode, Throwable t) {

    }

    @Override
    public void commonCall(int requestCode) {
        UIUtils.dismissDialog(progressDialog);
    }
}