package com.dkglabs.apply_loan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.activity.OnBackPressedCallback;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentLoanUserContactBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.applyloan.ContactDetails;
import com.dkglabs.model.request.LoanRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.LoanManager;

public class LoanUserContactFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, ResponseListener {

    private FragmentLoanUserContactBinding binding = null;

    public LoanUserContactFragment() {
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
        binding = FragmentLoanUserContactBinding.inflate(getLayoutInflater(), container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.mobileNo.getEditText().setText(PersistentManager.getUserResponse().getMobileNumber());
        binding.buttonNext.setOnClickListener(this);
        binding.sameMobile.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonNext) {
            validateData();
        }
    }

    private void validateData() {
        UIUtils.hideKeyboard(getActivity());

        //String mobileNo = binding.mobileNo.getEditText().getText().toString().trim();
        String whatsAppNo = binding.whatsAppNo.getEditText().getText().toString().trim();
        String residenceStdCode = binding.residenceStdCode.getEditText().getText().toString().trim();
        String residenceTelephone = binding.residenceTelephone.getEditText().getText().toString().trim();
        String officeStdCode = binding.officeStdCode.getEditText().getText().toString().trim();
        String officeTelephone = binding.officeTelephone.getEditText().getText().toString().trim();
        String otherMobileNo = binding.otherMobileNo.getEditText().getText().toString().trim();
        String email = binding.email.getEditText().getText().toString().trim();

        if (!whatsAppNo.isEmpty() && whatsAppNo.length() < 10) {
            UIUtils.showSnackbar(binding.getRoot(), "Enter valid whatsapp no");
            binding.whatsAppNo.getEditText().requestFocus();
            return;
        }

        if (!otherMobileNo.isEmpty() && otherMobileNo.length() < 10) {
            UIUtils.showSnackbar(binding.getRoot(), "Enter valid mobile no");
            binding.whatsAppNo.getEditText().requestFocus();
            return;
        }
        if (email.isEmpty() || !AppUtils.validateEmail(email)) {
            UIUtils.showSnackbar(binding.getRoot(), "Enter valid mail");
            binding.email.requestFocus();
            return;
        }

        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setWhatsappNo(whatsAppNo);
        contactDetails.setResidenceStdCode(residenceStdCode);
        contactDetails.setResidenceTelephone(residenceTelephone);
        contactDetails.setOfficeStdCode(officeStdCode);
        contactDetails.setOfficeTelephone(officeTelephone);
        contactDetails.setOtherMobileNumber(otherMobileNo);
        contactDetails.setEmail(email);

        LoanRequest request = new LoanRequest();
        request.setContactDetails(contactDetails);
        request.setUserId(PersistentManager.getUserResponse().getUserId());
        request.setLoanId(LoanPersistentManager.getLoanId());

        progressDialog = UIUtils.showProgressDialog(getActivity(), "Saving contact details...");
        LoanManager.saveContactDetails(1001, request, this);


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            UIUtils.disableView(binding.whatsAppNo);
            binding.whatsAppNo.getEditText().setText(binding.mobileNo.getEditText().getText().toString().trim());
        } else {
            UIUtils.enableView(binding.whatsAppNo);
            binding.whatsAppNo.getEditText().setText("");
        }
    }


    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        String loanResponse = (String) response.getResponseData();
        if (loanResponse.equals("Contact Details saved.")) {
            Navigation.findNavController(binding.getRoot()).navigate(LoanUserContactFragmentDirections.actionLoanUserContactFragmentToLoanUserBankFragment());
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