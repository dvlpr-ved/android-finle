package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "userId",
        "firstName",
        "lastName",
        "userType",
        "emailId",
        "mobileNumber",
        "whatsappNumber",
        "password",
        "userMode",
        "createdBy",
        "createdDate",
        "tokenDto",
        "loanId",
        "adminFlag",
        "active",
        "token"
})
public class UserResponse {
    @JsonProperty("id")
    private Object id;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("userType")
    private String userType;
    @JsonProperty("emailId")
    private String email;
    @JsonProperty("mobileNumber")
    private String mobileNumber;
    @JsonProperty("whatsappNumber")
    private String whatsappNumber;
    @JsonProperty("password")
    private Object password;
    @JsonProperty("userMode")
    private String userMode;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("createdDate")
    private String createdDate;
    @JsonProperty("tokenDto")
    private Object tokenDto;
    @JsonProperty("loanId")
    private String loanId;
    @JsonProperty("adminFlag")
    private Boolean adminFlag;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("token")
    private String token;

    public UserResponse() {
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email != null ? email : "";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getWhatsappNumber() {
        return whatsappNumber != null ? whatsappNumber : "";
    }

    public void setWhatsappNumber(String whatsappNumber) {
        this.whatsappNumber = whatsappNumber;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public String getUserMode() {
        return userMode;
    }

    public void setUserMode(String userMode) {
        this.userMode = userMode;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Object getTokenDto() {
        return tokenDto;
    }

    public void setTokenDto(Object tokenDto) {
        this.tokenDto = tokenDto;
    }

    public String getLoanId() {
        return loanId != null ? loanId : "";
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Boolean getAdminFlag() {
        return adminFlag;
    }

    public void setAdminFlag(Boolean adminFlag) {
        this.adminFlag = adminFlag;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
