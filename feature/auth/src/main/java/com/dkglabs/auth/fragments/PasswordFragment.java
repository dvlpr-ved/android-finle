package com.dkglabs.auth.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dkglabs.auth.R;
import com.dkglabs.auth.databinding.FragmentPasswordBinding;
import com.dkglabs.auth.viewmodel.AuthViewModel;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.request.LoginRequest;
import com.dkglabs.model.request.UserRegisterRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoginResponse;
import com.dkglabs.model.response.UserResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.UserManager;
import com.dkglabs.remote.utils.ServerConst;


/**
 * Created by Himanshu Srivastava on 9/14/2022.
 */
public class PasswordFragment extends BaseFragment implements View.OnClickListener, ResponseListener {

    private UserRegisterRequest userRequest;

    private FragmentPasswordBinding binding = null;

    public PasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPasswordBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void initializeView() {
        binding.buttonSubmit.setOnClickListener(this);
        userRequest = (UserRegisterRequest) getArguments().getSerializable("user");
        binding.textFirstName.setText(getString(R.string.first_name_label) + " "+userRequest.getFirstName());
        binding.textLastName.setText(getString(R.string.last_name_label) +" "+userRequest.getLastName());
        binding.textEmail.setText(getString(R.string.email_label) + " "+userRequest.getEmail());
    }

    public void validateUserPassword() {
        binding.textInputPassword.setError("");
        binding.textInputConfirmPassword.setError("");
        String password = binding.textInputPassword.getEditText().getText().toString();
        String confirmPassword = binding.textInputConfirmPassword.getEditText().getText().toString();

//        if (password.length() < 8 || password.length() > 16) {
//            binding.textInputPassword.setError(getString(R.string.password_16_char));
//            binding.textInputPassword.requestFocus();
//            return;
//        }

//        if (!password.equals(confirmPassword)) {
//            binding.textInputConfirmPassword.setError(getString(R.string.password_not_matched));
//            binding.textInputConfirmPassword.requestFocus();
//            return;
//        }


        if (AppUtils.isInternetAvailable(getActivity().getApplication(), binding.getRoot().getRootView())) {
            progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.progress_creating_account));
            userRequest.setPassword(password);
            userRequest.setConfirmPassword(confirmPassword);
            userRequest.setCategory(ServerConst.CATEGORY_CONSUMER);
            UserManager.registerUser(1001, userRequest, this);
        }


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonSubmit) {
            validateUserPassword();
        }
    }


    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        switch (requestCode) {
            case 1001:
                UserResponse registerUserResponse = (UserResponse) response.getResponseData();

                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setUserId(registerUserResponse.getUserId());
                loginResponse.setMobileNumber(registerUserResponse.getMobileNumber());
                loginResponse.setToken(registerUserResponse.getToken());

                PersistentManager.setLoginResponse(loginResponse);
                PersistentManager.setAuthToken(loginResponse.getToken());
                PersistentManager.setNewUser(false);
                PersistentManager.setUserAlreadyLogin(true);

                LoginRequest request = new LoginRequest();
                request.setMobileNumber(registerUserResponse.getMobileNumber());
                progressDialog = UIUtils.showProgressDialog(getActivity());
                UserManager.userDetails(1002, request,  this);
                break;
            case 1002:
                UserResponse userResponse = (UserResponse) response.getResponseData();
                PersistentManager.setUserResponse(userResponse);
                LoanPersistentManager.setLoanId(userResponse.getLoanId());
                AuthViewModel authViewModel = new AuthViewModel();
                authViewModel.setAuthSuccess(true);
                authViewModel.setUserType(userResponse.getUserType());
                viewModel.setSelectedItem(authViewModel);
                break;
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