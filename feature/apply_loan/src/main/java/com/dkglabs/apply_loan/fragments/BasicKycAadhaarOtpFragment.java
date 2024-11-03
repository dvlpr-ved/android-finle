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
import com.dkglabs.apply_loan.databinding.FragmentBasicKycAadhaarOtpBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.request.DocumentRequest;
import com.dkglabs.model.response.AadhaarOtpResponse;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.OtpDataResponse;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.DocumentManager;

public class BasicKycAadhaarOtpFragment extends BaseFragment implements View.OnClickListener, ResponseListener {
    private final int REQ_AADHAAR_GET_OTP = 1001;
    private final int REQ_AADHAAR_VERIFY_OTP = 1002;
    private OtpDataResponse otpDataResponse;
    private FragmentBasicKycAadhaarOtpBinding binding = null;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBasicKycAadhaarOtpBinding.inflate(inflater, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.setShowProgress(false);
        binding.buttonGetOtp.setOnClickListener(this);
        binding.buttonVerify.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
        applyLoanModel.setLoanState(1);
        viewModel.setSelectedItem(applyLoanModel);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.buttonGetOtp) {
            validateAadhaarNo();
        } else if (id == R.id.buttonVerify) {
            validateOtp();
        }

    }

    private void validateAadhaarNo() {
        UIUtils.hideKeyboard(getActivity());
        binding.aadhaarNumber.setError(null);
        binding.buttonVerify.setEnabled(true);
        String aadhaarNumber = binding.aadhaarNumber.getEditText().getText().toString().trim();
        if (aadhaarNumber.length() != 12) {
            binding.aadhaarNumber.setError("Enter valid aadhaar number. Do not use white space");
            binding.aadhaarNumber.requestFocus();
            return;
        }

        if (!binding.userAcceptance.isChecked()) {
            UIUtils.showSnackbar(binding.getRoot(), "Please check Aadhaar consent");
            binding.userAcceptance.requestFocus();
            return;
        }

        binding.setShowProgress(true);
        binding.buttonGetOtp.setEnabled(false);
        DocumentManager.getAadhaarOkycOtp(REQ_AADHAAR_GET_OTP, aadhaarNumber,  this);
    }

    private void validateOtp() {
        UIUtils.hideKeyboard(getActivity());
        binding.otp.setError(null);
        String otp = binding.otp.getEditText().getText().toString().trim();
        if (otp.length() != 6) {
            binding.otp.setError("Enter valid OTP");
            binding.otp.requestFocus();
            return;
        }

        progressDialog = UIUtils.showProgressDialog(getActivity(), "Verifying OTP...");
        binding.buttonVerify.setEnabled(false);
        DocumentRequest request = new DocumentRequest();
        request.setUserId(PersistentManager.getUserResponse().getUserId());
        request.setMobileNo(PersistentManager.getUserResponse().getMobileNumber());
        request.setOtp(otp);
        request.setRequestId(otpDataResponse.getRequestId());
        DocumentManager.validateAadhaar(REQ_AADHAAR_VERIFY_OTP, request,  this);
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        AadhaarOtpResponse aadhaarOtpResponse = (AadhaarOtpResponse) response.getResponseData();
        if (aadhaarOtpResponse.getStatusCode() == 200) {
            switch (requestCode) {
                case REQ_AADHAAR_GET_OTP:
                    otpDataResponse = aadhaarOtpResponse.getData();
                    if (otpDataResponse.getOtpSentStatus()) {
                        UIUtils.showToast(context, "OTP sent");
                        UIUtils.showView(binding.llAadhaarOtp);
                        binding.buttonGetOtp.setEnabled(true);
                    } else {
                        UIUtils.showSnackbar(binding.getRoot(), "Unable to send OTP, Please try again later...");
                        binding.buttonGetOtp.setEnabled(true);
                    }

                    break;

                case REQ_AADHAAR_VERIFY_OTP:
                    LoanPersistentManager.setAadhaarOtpResponse(aadhaarOtpResponse);
                    UIUtils.showToast(context, "Aadhaar verified");
                    BasicKycAadhaarOtpFragmentDirections.ActionBasicKycAadhaarOtpFragmentToAadhaarDetailsFragment directions = BasicKycAadhaarOtpFragmentDirections.actionBasicKycAadhaarOtpFragmentToAadhaarDetailsFragment(aadhaarOtpResponse);
                    Navigation.findNavController(binding.getRoot()).navigate(directions);
                    break;
            }
        } else if (aadhaarOtpResponse.getStatusCode() == 422) {
            binding.buttonGetOtp.setEnabled(true);
            UIUtils.hideViewGone(binding.llAadhaarOtp);
            UIUtils.showSnackbar(binding.getRoot(), aadhaarOtpResponse.getMessage());
            binding.buttonGetOtp.setEnabled(true);
        } else {
            UIUtils.showSnackbar(binding.getRoot(), "Unable to send OTP, Please try again later...");
            binding.buttonGetOtp.setEnabled(true);
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        UIUtils.showGenericErrorDialog(binding.getRoot(), context);
        switch (requestCode) {
            case REQ_AADHAAR_GET_OTP:
                binding.buttonGetOtp.setEnabled(true);
                break;
            case REQ_AADHAAR_VERIFY_OTP:
                binding.buttonVerify.setEnabled(true);
                break;
        }
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        UIUtils.showGenericErrorDialog(binding.getRoot(), context);
        switch (requestCode) {
            case REQ_AADHAAR_GET_OTP:
                binding.buttonGetOtp.setEnabled(true);
                break;
            case REQ_AADHAAR_VERIFY_OTP:
                binding.buttonVerify.setEnabled(true);
                break;
        }
    }

    @Override
    public void commonCall(int requestCode) {
        switch (requestCode) {
            case REQ_AADHAAR_GET_OTP:
                binding.setShowProgress(false);
                break;
            case REQ_AADHAAR_VERIFY_OTP:
                UIUtils.dismissDialog(progressDialog);
                break;
        }

    }
}