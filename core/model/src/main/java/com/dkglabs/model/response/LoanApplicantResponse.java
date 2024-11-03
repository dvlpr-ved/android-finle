package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "loanId",
        "name",
        "email",
        "phone",
        "partnerCode",
        "loanAmount",
        "term",
        "loanEmiAmount",
        "loanEmiPaymentDate",
        "emiDue",
        "lateFeeDue",
        "extraPaid",
        "status",
        "subStatus",
        "lastPaidAmount",
        "lastPaidDate",
        "dueEmiAmount",
        "currentEmiPaymentDate",
        "lastPaidEmiDetails"
})
public class LoanApplicantResponse implements Serializable {
    @JsonProperty("loanId")
    private String loanId;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("partnerCode")
    private String partnerCode;
    @JsonProperty("loanAmount")
    private Double loanAmount;
    @JsonProperty("term")
    private String term;
    @JsonProperty("loanEmiAmount")
    private Double loanEmiAmount;
    @JsonProperty("loanEmiPaymentDate")
    private String loanEmiPaymentDate;
    @JsonProperty("emiDue")
    private Long emiDue;
    @JsonProperty("lateFeeDue")
    private Double lateFeeDue;
    @JsonProperty("extraPaid")
    private Double extraPaid;
    @JsonProperty("status")
    private String status;
    @JsonProperty("subStatus")
    private String subStatus;
    @JsonProperty("lastPaidAmount")
    private Double lastPaidAmount;
    @JsonProperty("lastPaidDate")
    private String lastPaidDate;
    @JsonProperty("dueEmiAmount")
    private Double dueEmiAmount;
    @JsonProperty("currentEmiPaymentDate")
    private String currentEmiPaymentDate;
    @JsonProperty("emiDetails")
    private List<LoanEmiDetailResponse> emiDetails;
    LoanApplicantResponse response;

    public LoanApplicantResponse getResponse() {
        return response;
    }

    public void setResponse(LoanApplicantResponse response) {
        this.response = response;
    }

    public LoanApplicantResponse() {
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Double getLoanEmiAmount() {
        return loanEmiAmount;
    }

    public void setLoanEmiAmount(Double loanEmiAmount) {
        this.loanEmiAmount = loanEmiAmount;
    }

    public String getLoanEmiPaymentDate() {
        return loanEmiPaymentDate;
    }

    public void setLoanEmiPaymentDate(String loanEmiPaymentDate) {
        this.loanEmiPaymentDate = loanEmiPaymentDate;
    }

    public Long getEmiDue() {
        return emiDue;
    }

    public void setEmiDue(Long emiDue) {
        this.emiDue = emiDue;
    }

    public Double getLateFeeDue() {
        return lateFeeDue;
    }

    public void setLateFeeDue(Double lateFeeDue) {
        this.lateFeeDue = lateFeeDue;
    }

    public Double getExtraPaid() {
        return extraPaid;
    }

    public void setExtraPaid(Double extraPaid) {
        this.extraPaid = extraPaid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }

    public Double getLastPaidAmount() {
        return lastPaidAmount;
    }

    public void setLastPaidAmount(Double lastPaidAmount) {
        this.lastPaidAmount = lastPaidAmount;
    }

    public String getLastPaidDate() {
        return lastPaidDate;
    }

    public void setLastPaidDate(String lastPaidDate) {
        this.lastPaidDate = lastPaidDate;
    }

    public Double getDueEmiAmount() {
        return dueEmiAmount;
    }

    public void setDueEmiAmount(Double dueEmiAmount) {
        this.dueEmiAmount = dueEmiAmount;
    }

    public String getCurrentEmiPaymentDate() {
        return currentEmiPaymentDate;
    }

    public void setCurrentEmiPaymentDate(String currentEmiPaymentDate) {
        this.currentEmiPaymentDate = currentEmiPaymentDate;
    }

    public List<LoanEmiDetailResponse> getEmiDetails() {
        return emiDetails;
    }

    public void setEmiDetails(List<LoanEmiDetailResponse> emiDetails) {
        this.emiDetails = emiDetails;
    }
}
