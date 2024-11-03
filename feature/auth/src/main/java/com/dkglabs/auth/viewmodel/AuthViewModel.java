package com.dkglabs.auth.viewmodel;

/**
 * Created by Himanshu Srivastava on 31,May,2023
 * Project e_savari
 */
public class AuthViewModel {
    public Boolean authSuccess = false;
    public Boolean userNotFound = false;
    public String userMobile = "";
    public Boolean registerSuccess = false;
    public String userType = "";

    public AuthViewModel() {
    }

    public Boolean getAuthSuccess() {
        return authSuccess;
    }

    public void setAuthSuccess(Boolean authSuccess) {
        this.authSuccess = authSuccess;
    }

    public Boolean getUserNotFound() {
        return userNotFound;
    }

    public void setUserNotFound(Boolean userNotFound) {
        this.userNotFound = userNotFound;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public Boolean getRegisterSuccess() {
        return registerSuccess;
    }

    public void setRegisterSuccess(Boolean registerSuccess) {
        this.registerSuccess = registerSuccess;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
