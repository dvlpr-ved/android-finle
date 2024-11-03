package com.dkglabs.account.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;

import com.dkglabs.account.R;
import com.dkglabs.account.databinding.FragmentUpdatePasswordBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.request.UpdatePasswordRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.UserResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.UserManager;


/**
 * Created by Himanshu Srivastava on 11/3/2022.
 */
public class UpdatePasswordFragment extends BaseFragment implements View.OnClickListener, ResponseListener {


    private FragmentUpdatePasswordBinding binding = null;

    public UpdatePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUpdatePasswordBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        setUpBackToolbar(getString(R.string.change_password));
        binding.buttonChange.setOnClickListener(this);
    }

    public void validateUserPassword() {
        binding.oldPassword.setError("");
        binding.newPassword.setError("");
        binding.confirmPassword.setError("");
        String oldPassword = binding.oldPassword.getEditText().getText().toString();
        String newPassword = binding.newPassword.getEditText().getText().toString();
        String confirmPassword = binding.confirmPassword.getEditText().getText().toString();

        if (oldPassword.isEmpty()) {
            binding.oldPassword.setError(getString(R.string.old_password_empty));
            binding.oldPassword.requestFocus();
            return;
        }

        if (newPassword.length() < 8 || newPassword.length() > 16) {
            binding.newPassword.setError(getString(R.string.password_16_char));
            binding.newPassword.requestFocus();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            binding.confirmPassword.setError(getString(R.string.password_not_matched));
            binding.confirmPassword.requestFocus();
            return;
        }


        if (AppUtils.isInternetAvailable(getActivity().getApplication(), binding.getRoot().getRootView())) {
            progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.progress_updating_password));
            UpdatePasswordRequest request = new UpdatePasswordRequest();
            request.setPassword(confirmPassword);
            request.setOldPassword(oldPassword);
            request.setMobileNumber(PersistentManager.getUserResponse().getMobileNumber());
            request.setUserId(PersistentManager.getUserResponse().getUserId());
            UserManager.updatePassword(1001, request,  this);
        }


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonChange) {
            validateUserPassword();
        }
    }


    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        if (requestCode == 1001) {
            UserResponse userResponse = (UserResponse) response.getResponseData();
            PersistentManager.setUserResponse(userResponse);
            LoanPersistentManager.setLoanId(userResponse.getLoanId());
            UIUtils.showToast(context, getString(R.string.password_changed));
            Navigation.findNavController(binding.getRoot()).popBackStack();
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        if (message.equals("Old password not correct.")) {
            binding.oldPassword.setError(getString(R.string.old_password_not_matched));
            binding.oldPassword.requestFocus();
        }
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