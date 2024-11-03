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
import com.dkglabs.apply_loan.databinding.FragmentLoanUserBankBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.applyloan.BankDetails;
import com.dkglabs.model.request.LoanRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoanResponse;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.LoanManager;

public class LoanUserBankFragment extends BaseFragment implements View.OnClickListener, ResponseListener {

    private FragmentLoanUserBankBinding binding = null;

    public LoanUserBankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Bundle bundle = new Bundle();
                bundle.putString("title", "title");
                bundle.putString("message", "message");
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_global_loanBackPressFragment, bundle);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoanUserBankBinding.inflate(getLayoutInflater(), container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
        applyLoanModel.setLoanState(3);
        viewModel.setSelectedItem(applyLoanModel);
    }

    private void initializeView() {
        binding.buttonVerify.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonVerify) {
            validateData();
        }
    }

    private void validateData() {
        UIUtils.hideKeyboard(getActivity());

        String accountNumber = binding.accountNumber.getEditText().getText().toString().trim();
        String ifscCode = binding.ifscCode.getEditText().getText().toString().trim();
        String accountHolderName = binding.accountHolderName.getEditText().getText().toString().trim();
        Boolean userAcceptance = binding.userAcceptance.isChecked();

        if (!AppUtils.isContainsData(accountNumber)) {
            UIUtils.showSnackbar(binding.getRoot(), "Enter your account number");
            binding.accountNumber.requestFocus();
            return;
        }

        if (!AppUtils.isContainsData(ifscCode)) {
            UIUtils.showSnackbar(binding.getRoot(), "Enter your bank IFSC code");
            binding.ifscCode.requestFocus();
            return;
        }

        if (!AppUtils.isContainsData(accountHolderName)) {
            UIUtils.showSnackbar(binding.getRoot(), "Enter account holder name");
            binding.accountHolderName.requestFocus();
            return;
        }

        if (!userAcceptance) {
            UIUtils.showSnackbar(binding.getRoot(), "I consent (Please check the box)");
            return;
        }

        BankDetails bankDetails = new BankDetails();
        bankDetails.setAccountNumber(accountNumber);
        bankDetails.setIfscCode(ifscCode);
        bankDetails.setAccHolderName(accountHolderName);


        LoanRequest request = new LoanRequest();
        request.setBankDetails(bankDetails);
        request.setUserId(PersistentManager.getUserResponse().getUserId());
        request.setLoanId(LoanPersistentManager.getLoanId());


        progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.verify_bank_details));
        LoanManager.saveBankDetails(1001, request,  this);
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        LoanResponse loanResponse = (LoanResponse) response.getResponseData();
        if (loanResponse != null) {
            LoanUserBankFragmentDirections.ActionLoanUserBankFragmentToBankDetailsFragment direction = LoanUserBankFragmentDirections.actionLoanUserBankFragmentToBankDetailsFragment(loanResponse.getBankDetails());
            Navigation.findNavController(binding.getRoot()).navigate(direction);
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        UIUtils.showSnackbar(binding.getRoot(), message.isEmpty() ? getString(com.dkglabs.base.R.string.generic_error_msg) : message);
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        UIUtils.showSnackbar(binding.getRoot(), getString(com.dkglabs.base.R.string.generic_error_msg));
    }

    @Override
    public void commonCall(int requestCode) {
        UIUtils.dismissDialog(progressDialog);
    }
}