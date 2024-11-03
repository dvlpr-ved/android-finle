package com.dkglabs.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Himanshu Srivastava on 04,August,2023
 * Project e_savari
 */
public class UpdatePasswordRequest {
    /*
        "mobileNumber": "string",
        "oldPassword": "string",
        "password": "string",
        "userId": "string"
    */
    @JsonProperty("mobileNumber")
    private String mobileNumber;
    @JsonProperty("oldPassword")
    private String oldPassword;
    @JsonProperty("password")
    private String password;
    @JsonProperty("userId")
    private String userId;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}