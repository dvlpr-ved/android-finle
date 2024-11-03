package com.dkglabs.model.request;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostSaveLoanRequest {

    @JsonProperty("additionalDocNumber")
    private String additionalDocNumber;

    @JsonProperty("dealerId")
    private String dealerId;

    @JsonProperty("loanId")
    private String loanId;

    @JsonProperty("registrationNumber")
    private String registrationNumber;

    // Constructors, getters, and setters

    public PostSaveLoanRequest() {
        // Default constructor
    }

    public PostSaveLoanRequest(String additionalDocNumber, String dealerId, String loanId, String registrationNumber) {
        this.additionalDocNumber = additionalDocNumber;
        this.dealerId = dealerId;
        this.loanId = loanId;
        this.registrationNumber = registrationNumber;
    }

    // Getters and Setters

    public String getAdditionalDocNumber() {
        return additionalDocNumber;
    }

    public void setAdditionalDocNumber(String additionalDocNumber) {
        this.additionalDocNumber = additionalDocNumber;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
