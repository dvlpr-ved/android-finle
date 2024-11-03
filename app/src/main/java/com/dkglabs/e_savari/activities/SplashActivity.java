package com.dkglabs.e_savari.activities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.dkglabs.base.activities.BaseActivity;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.manager.ThemeManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.e_savari.R;
import com.dkglabs.e_savari.databinding.ActivitySplashBinding;
import com.dkglabs.e_savari.viewmodel.SplashViewModel;
import com.dkglabs.model.request.LoginRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoginResponse;
import com.dkglabs.model.response.UserResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.UserManager;
import com.dkglabs.remote.utils.ServerConst;

/**
 * Created by Himanshu Srivastava on 3/13/2023.
 */
public class SplashActivity extends BaseActivity implements ResponseListener {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final int SPLASH_SCREEN_POST_DELAY = 2000;
    private Runnable runnable = null;
    private ActivitySplashBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.updateTheme();
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fullScreenActivity();
        initializeView();
    }

    private void initializeView() {
        PackageManager manager = getPackageManager();

        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }

        SplashViewModel splashViewModel = new SplashViewModel(String.format(getString(R.string.app_version), info.versionName));
        binding.setSplashViewModel(splashViewModel);
        binding.setLifecycleOwner(this);

        handler.postDelayed(runnable = () -> {
            validateUser();
        }, SPLASH_SCREEN_POST_DELAY);
    }

    private void validateUser() {
        if (PersistentManager.isUserAlreadyLogin()) {
            validateMobileNumber();
        } else if (PersistentManager.isNewUser()) {
            startActivity(MainActivity.class);
            closeActivity();
        } else {
            startActivity(UserAuthActivity.class);
            closeActivity();
        }
    }

    private void validateMobileNumber() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setMobileNumber(PersistentManager.getUserResponse().getMobileNumber());
        UserManager.userLogin(1001, loginRequest, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding != null) binding = null;
        handler.removeCallbacks(runnable);
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
                UserManager.userDetails(1002, request,  this);
                break;
            case 1002:
                UserResponse userResponse = (UserResponse) response.getResponseData();
                validateUser(userResponse);
                break;
        }
    }

    private void validateUser(UserResponse userResponse) {
        PersistentManager.setUserResponse(userResponse);
        LoanPersistentManager.setLoanId(userResponse.getLoanId());
        if (userResponse.getUserType().equals(ServerConst.CATEGORY_CONSUMER)) {
            startActivity(HomeActivity.class);
            closeAllActivity();
        } else {
            startActivity(UserAuthActivity.class);
            closeAllActivity();
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        switch (requestCode) {
            case 1001:
                if (message.equals(ServerConst.USER_NOT_FOUND_MESSAGE)) {
                    startActivity(UserAuthActivity.class);
                    closeActivity();
                } else {
                    UIUtils.showSnackbar(binding.getRoot(), message.isEmpty() ? getString(com.dkglabs.base.R.string.generic_error_msg) : message);
                }
                break;
            default:
                UIUtils.showSnackbar(binding.getRoot(), message.isEmpty() ? getString(com.dkglabs.base.R.string.generic_error_msg) : message);
                break;
        }
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        UIUtils.showSnackbar(binding.getRoot(), getString(com.dkglabs.base.R.string.generic_error_msg));
    }

    @Override
    public void commonCall(int requestCode) {

    }
}