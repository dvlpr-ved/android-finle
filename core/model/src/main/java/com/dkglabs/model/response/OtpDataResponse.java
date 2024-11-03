package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "requestId",
        "otpSentStatus",
        "if_number",
        "status",
        "client_id",
        "full_name",
        "aadhaar_number",
        "dob",
        "gender",
        "address",
        "face_status",
        "face_score",
        "zip",
        "profile_image",
        "has_image",
        "email_hash",
        "mobile_hash",
        "zip_data",
        "raw_xml",
        "care_of",
        "share_code",
        "mobile_verified",
        "reference_id",
        "validAadhaar"
})
public class OtpDataResponse implements Serializable {
    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("otpSentStatus")
    private Boolean otpSentStatus;
    @JsonProperty("if_number")
    private Boolean ifNumber;
    @JsonProperty("status")
    private String status;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("aadhaar_number")
    private String aadhaarNumber;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("address")
    private AadhaarAddress address;
    @JsonProperty("face_status")
    private Boolean faceStatus;
    @JsonProperty("face_score")
    private Long faceScore;
    @JsonProperty("zip")
    private String zip;
    @JsonProperty("profile_image")
    private String profileImage;
    @JsonProperty("has_image")
    private Boolean hasImage;
    @JsonProperty("email_hash")
    private String emailHash;
    @JsonProperty("mobile_hash")
    private String mobileHash;
    @JsonProperty("zip_data")
    private String zipData;
    @JsonProperty("raw_xml")
    private String rawXml;
    @JsonProperty("care_of")
    private String careOf;
    @JsonProperty("share_code")
    private String shareCode;
    @JsonProperty("mobile_verified")
    private Boolean mobileVerified;
    @JsonProperty("reference_id")
    private String referenceId;
    @JsonProperty("validAadhaar")
    private Boolean validAadhaar;

    public OtpDataResponse() {

    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Boolean getOtpSentStatus() {
        return otpSentStatus;
    }

    public void setOtpSentStatus(Boolean otpSentStatus) {
        this.otpSentStatus = otpSentStatus;
    }

    public Boolean getIfNumber() {
        return ifNumber;
    }

    public void setIfNumber(Boolean ifNumber) {
        this.ifNumber = ifNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public AadhaarAddress getAddress() {
        return address;
    }

    public void setAddress(AadhaarAddress address) {
        this.address = address;
    }

    public Boolean getFaceStatus() {
        return faceStatus;
    }

    public void setFaceStatus(Boolean faceStatus) {
        this.faceStatus = faceStatus;
    }

    public Long getFaceScore() {
        return faceScore;
    }

    public void setFaceScore(Long faceScore) {
        this.faceScore = faceScore;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Boolean getHasImage() {
        return hasImage;
    }

    public void setHasImage(Boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getEmailHash() {
        return emailHash;
    }

    public void setEmailHash(String emailHash) {
        this.emailHash = emailHash;
    }

    public String getMobileHash() {
        return mobileHash;
    }

    public void setMobileHash(String mobileHash) {
        this.mobileHash = mobileHash;
    }

    public String getZipData() {
        return zipData;
    }

    public void setZipData(String zipData) {
        this.zipData = zipData;
    }

    public String getRawXml() {
        return rawXml;
    }

    public void setRawXml(String rawXml) {
        this.rawXml = rawXml;
    }

    public String getCareOf() {
        return careOf;
    }

    public void setCareOf(String careOf) {
        this.careOf = careOf;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public Boolean getMobileVerified() {
        return mobileVerified;
    }

    public void setMobileVerified(Boolean mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public Boolean getValidAadhaar() {
        return validAadhaar;
    }

    public void setValidAadhaar(Boolean validAadhaar) {
        this.validAadhaar = validAadhaar;
    }


}
