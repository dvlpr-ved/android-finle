package com.dkglabs.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Himanshu Srivastava on 30,June,2023
 * Project e_savari
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentRequest {

    @JsonProperty("userId")
    private String userId;
    @JsonProperty("imageBase64")
    private String imageBase64;
    @JsonProperty("docType")
    private String docType;
    @JsonProperty("mobileNo")
    private String mobileNo;
    @JsonProperty("otp")
    private String otp;
    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("partnerId")
    private String partnerId;

    @JsonProperty("nbfcId")
    private String nbfcId;

    public DocumentRequest() {
    }
    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getNbfcId() {
        return nbfcId;
    }

    public void setNbfcId(String nbfcId) {
        this.nbfcId = nbfcId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
