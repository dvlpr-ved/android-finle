package com.dkglabs.auth.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dkglabs.auth.R;
import com.dkglabs.auth.databinding.FragmentOtpBinding;
import com.dkglabs.auth.interfaces.OtpListener;
import com.dkglabs.auth.receiver.OtpReceiver;
import com.dkglabs.auth.viewmodel.AuthViewModel;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.interfaces.DialogActionInterface;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.LogsManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.request.LoginRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoginResponse;
import com.dkglabs.model.response.UserResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.UserManager;
import com.dkglabs.remote.utils.ServerConst;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpFragment extends BaseFragment implements View.OnClickListener, ResponseListener, OtpListener {

    public static final int SMS_CONSENT_REQUEST = 2;
    private static final int OTP_TIMEOUT = 10;
    private String mobileNumber = "";
    private String phone;
    private String code;
    private String verificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verifyCallbacks = null;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken = null;
    private OtpReceiver otpReceiver;
    private FragmentOtpBinding binding = null;
    private boolean isOtpSent = false;

    public OtpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* TODO register receiver
        otpReceiver = new OtpReceiver();
        context.registerReceiver(otpReceiver, new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION));
        otpReceiver.init(this);
        */

        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                alertDialog = UIUtils.showAlertDialog(getActivity(), getString(R.string.otp_verification), getString(R.string.otp_go_back), getString(R.string.text_yes), getString(R.string.text_no), (DialogActionInterface) (dialog, tag) -> {
                    switch (tag) {
                        case DialogActionInterface.POSITIVE_TAG:
                            setEnabled(false);
                            requireActivity().onBackPressed();
                        case DialogActionInterface.NEGATIVE_TAG:
                            dialog.dismiss();
                            break;
                    }
                });
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOtpBinding.inflate(inflater, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        FirebaseAuth.getInstance().signOut();
        binding.buttonVerifyOtp.setOnClickListener(this);
        binding.textViewChange.setOnClickListener(this);
        binding.textViewResend.setOnClickListener(this);
        phone = getArguments().getString("phone");
        code = getArguments().getString("code");
        mobileNumber = code + phone;
        binding.textViewPhone.setText(String.format(getString(R.string.user_phone_placeholder), code, phone));
        verifyUser();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /* TODO unregister receiver
        if (otpReceiver != null)
            context.unregisterReceiver(otpReceiver);
            */
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonVerifyOtp) {
            verifyOtp();
        } else if (id == R.id.textViewChange) {
            alertDialog = UIUtils.showAlertDialog(getActivity(), getString(R.string.change_phone_number), getString(R.string.otp_go_back), getString(R.string.text_yes), getString(R.string.text_no), new DialogActionInterface() {
                @Override
                public void onDialogActionListener(Dialog dialog, int tag) {
                    switch (tag) {
                        case DialogActionInterface.POSITIVE_TAG:
                            callback.setEnabled(false);
                            requireActivity().onBackPressed();
                        case DialogActionInterface.NEGATIVE_TAG:
                            dialog.dismiss();
                            break;
                    }
                }
            });
        } else if (id == R.id.textViewResend) {
            resendVerificationCode();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void verifyUser() {
        verifyCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                LogsManager.printLog("Verification_fail: " + e);
                UIUtils.dismissDialog(progressDialog);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    UIUtils.showSnackbar(binding.getRoot(), "Invalid auth details");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    UIUtils.showSnackbar(binding.getRoot(), "SMS Quota reached");
                } else if (e instanceof FirebaseAuthMissingActivityForRecaptchaException) {
                    UIUtils.showSnackbar(binding.getRoot(), "reCAPTCHA not verified");
                } else {
                    UIUtils.showGenericErrorDialog(binding.getRoot(), getContext());
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);
                OtpFragment.this.forceResendingToken = forceResendingToken;
                LogsManager.printLog("Code sent: " + mobileNumber);
                OtpFragment.this.verificationId = verificationId;
                isOtpSent = true;
                UIUtils.showView(binding.llResend);
            }

            public void onCodeAutoRetrievalTimeOut(@NonNull String var1) {
                LogsManager.printLog("Code Auto Time Out: " + mobileNumber);
                isOtpSent = true;
                UIUtils.showView(binding.llResend);
            }
        };

        sendVerificationCode();
    }

    private void verifyOtp() {
        if (isOtpSent) {
            UIUtils.hideKeyboard(getActivity());
            String otpValues = binding.otpView.getOTP();
            if (otpValues.length() < 6) {
                UIUtils.showSnackbar(binding.getRoot(), getString(R.string.invalid_otp));
                return;
            }
            UIUtils.hideKeyboard(getActivity());
            progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.progress_signing_in));
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otpValues);
            signInWithPhoneAuthCredential(credential);
        } else {
            Toast.makeText(context, "Please wait..", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendVerificationCode() {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance()).setPhoneNumber(mobileNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(getActivity())                 // Activity (for callback binding)
                .setCallbacks(verifyCallbacks)          // OnVerificationStateChangedCallbacks
                .setForceResendingToken(forceResendingToken).build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void resendVerificationCode() {
        binding.otpView.setOTP("");
        isOtpSent = false;
        UIUtils.hideViewInvisible(binding.llResend);
        UIUtils.showSnackbar(binding.getRoot(), getString(R.string.resend_otp_msg));
        PhoneAuthProvider.getInstance().verifyPhoneNumber(mobileNumber, OTP_TIMEOUT, TimeUnit.SECONDS, getActivity(), verifyCallbacks, forceResendingToken);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                    mUser.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                if (progressDialog == null || !progressDialog.isShowing()) {
                                    progressDialog = UIUtils.showProgressDialog(getActivity());
                                }
                                //verificationId = task.getResult().getToken();
                                validateUser();
                            } else {
                                UIUtils.showSnackbar(binding.getRoot(), getString(com.dkglabs.base.R.string.generic_error_msg));
                                LogsManager.printLog("OTP On Failure", task.getException());
                            }
                        }
                    });

                } else {
                    UIUtils.dismissDialog(progressDialog);
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        UIUtils.showSnackbar(binding.getRoot(), getString(R.string.incorrect_otp));
                    }
                }
            }
        });
    }

    private void validateUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setMobileNumber(phone);
        UserManager.userLogin(1001, loginRequest, this);
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        switch (requestCode) {
            case 1001:
                LoginResponse loginResponse = (LoginResponse) response.getResponseData();
                PersistentManager.setLoginResponse(loginResponse);
                PersistentManager.setAuthToken(loginResponse.getToken());
                PersistentManager.setNewUser(false);
                PersistentManager.setUserAlreadyLogin(true);

                LoginRequest request = new LoginRequest();
                request.setMobileNumber(loginResponse.getMobileNumber());
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
        if (requestCode == 1001) {
            if (message.equals(ServerConst.USER_NOT_FOUND_MESSAGE)) {
                AuthViewModel authViewModel = new AuthViewModel();
                authViewModel.setAuthSuccess(false);
                authViewModel.setUserNotFound(true);
                authViewModel.setUserMobile(phone);
                viewModel.setSelectedItem(authViewModel);
            } else {
                UIUtils.showSnackbar(binding.getRoot(), message.isEmpty() ? getString(com.dkglabs.base.R.string.generic_error_msg) : message);
            }
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

    @Override
    public void onOtpSuccess(Intent intent) {
        try {
            startActivityForResult(intent, SMS_CONSENT_REQUEST);
        } catch (ActivityNotFoundException e) {
            LogsManager.printLog(e);
        }
    }

    @Override
    public void onTimeout() {
        LogsManager.printLog("OTP Receiver timeout");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SMS_CONSENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                String otp = AppUtils.parseOneTimeCode(message);
                binding.otpView.setOTP(otp);
                verifyOtp();
            }
        }
    }
}