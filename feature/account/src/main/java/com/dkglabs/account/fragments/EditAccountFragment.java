package com.dkglabs.account.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.account.R;
import com.dkglabs.account.databinding.FragmentEditAccountBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.request.UpdateUserRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.UserResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.UserManager;

public class EditAccountFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, ResponseListener {


    private FragmentEditAccountBinding binding = null;

    public EditAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditAccountBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        setUpBackToolbar(getString(R.string.edit_profile));
        binding.buttonSave.setOnClickListener(this);
        binding.sameMobile.setOnCheckedChangeListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textInputFirstName.getEditText().setText(PersistentManager.getUserResponse().getFirstName());
        binding.textInputLastName.getEditText().setText(PersistentManager.getUserResponse().getLastName());
        binding.textInputEmail.getEditText().setText(PersistentManager.getUserResponse().getEmail());
        String whatsAppNo = PersistentManager.getUserResponse().getWhatsappNumber();
        String mobileNo = PersistentManager.getUserResponse().getMobileNumber();
        binding.sameMobile.setChecked(mobileNo.equals(whatsAppNo));
        binding.textInputWhatsappNo.getEditText().setText(whatsAppNo);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonSave) {
            validateData();
        }
    }

    private void validateData() {
        String firstName = binding.textInputFirstName.getEditText().getText().toString().trim();
        String lastName = binding.textInputLastName.getEditText().getText().toString().trim();
        String email = binding.textInputEmail.getEditText().getText().toString().trim();
        String whatsAppNo = binding.textInputWhatsappNo.getEditText().getText().toString().trim();

        if (AppUtils.isStringEmpty(firstName)) {
            UIUtils.showSnackbar(binding.getRoot(), getString(R.string.first_name_msg));
            binding.textInputFirstName.requestFocus();
            binding.textInputFirstName.setError(getString(R.string.first_name_msg));
            return;
        }
        if (AppUtils.isStringEmpty(lastName)) {
            UIUtils.showSnackbar(binding.getRoot(), getString(R.string.last_name_msg));
            binding.textInputLastName.requestFocus();
            binding.textInputLastName.setError(getString(R.string.last_name_msg));
            return;
        }

        if (AppUtils.isContainsData(email) && !AppUtils.validateEmail(email)) {
            UIUtils.showSnackbar(binding.getRoot(), getString(R.string.enter_valid_email_msg));
            binding.textInputEmail.requestFocus();
            binding.textInputEmail.setError(getString(R.string.enter_valid_email_msg));
            return;
        }


        if (AppUtils.isInternetAvailable(getActivity().getApplication(), binding.getRoot().getRootView())) {
            UpdateUserRequest request = new UpdateUserRequest();
            request.setFirstName(firstName);
            request.setLastName(lastName);
            request.setEmail(email);
            request.setWhatsappNumber(whatsAppNo);
            request.setMobileNumber(PersistentManager.getUserResponse().getMobileNumber());
            request.setUserId(PersistentManager.getUserResponse().getUserId());

            progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.progress_updating_account));
            UserManager.updateUser(1001, request,  this);
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            binding.textInputWhatsappNo.getEditText().setText(PersistentManager.getUserResponse().getMobileNumber());
        } else {
            binding.textInputWhatsappNo.getEditText().setText(PersistentManager.getUserResponse().getWhatsappNumber());
        }
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        if (requestCode == 1001) {
            UserResponse userResponse = (UserResponse) response.getResponseData();
            PersistentManager.setUserResponse(userResponse);
            LoanPersistentManager.setLoanId(userResponse.getLoanId());
            UIUtils.showToast(context, getString(R.string.account_updated));
            Navigation.findNavController(binding.getRoot()).popBackStack();
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        UIUtils.showSnackbar(binding.getRoot(), getString(com.dkglabs.base.R.string.generic_error_msg));
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