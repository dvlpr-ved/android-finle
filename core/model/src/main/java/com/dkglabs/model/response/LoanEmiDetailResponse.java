package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

/**
 * Created by Himanshu Srivastava on 23,August,2023
 * Project e_savari
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "loanEmiId",
        "loanId",
        "loanEmiFrequency",
        "loanEmiNumber",
        "loanMonth",
        "loanEmiAmount",
        "loanCurrentBalance",
        "loanEmiInterest",
        "loanEmiPrincipal",
        "loanEmiPaymentDate",
        "loanEmiStatus",
        "remarks",
        "createdBy",
        "createdDate",
        "updatedDate"
})
public class LoanEmiDetailResponse implements Serializable {
    @JsonProperty("loanEmiId")
    private String loanEmiId;
    @JsonProperty("loanId")
    private String loanId;
    @JsonProperty("loanEmiNumber")
    private String loanEmiNumber;
    @JsonProperty("loanEmiFrequency")
    private String loanEmiFrequency;
    @JsonProperty("loanMonth")
    private Long loanMonth;
    @JsonProperty("loanEmiAmount")
    private Double loanEmiAmount;
    @JsonProperty("loanCurrentBalance")
    private Double loanCurrentBalance;
    @JsonProperty("loanEmiInterest")
    private Double loanEmiInterest;
    @JsonProperty("loanEmiPrincipal")
    private Double loanEmiPrincipal;
    @JsonProperty("loanEmiPaymentDate")
    private String loanEmiPaymentDate;
    @JsonProperty("loanEmiStatus")
    private String loanEmiStatus;
    @JsonProperty("remarks")
    private String remarks;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("createdDate")
    private String createdDate;
    @JsonProperty("updatedDate")
    private String updatedDate;

    public LoanEmiDetailResponse() {
    }

    public String getLoanEmiFrequency() {
        return loanEmiFrequency;
    }

    public void setLoanEmiFrequency(String loanEmiFrequency) {
        this.loanEmiFrequency = loanEmiFrequency;
    }

    public String getLoanEmiNumber() {
        return loanEmiNumber;
    }

    public void setLoanEmiNumber(String loanEmiNumber) {
        this.loanEmiNumber = loanEmiNumber;
    }

    public String getLoanEmiId() {
        return loanEmiId;
    }

    public void setLoanEmiId(String loanEmiId) {
        this.loanEmiId = loanEmiId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Long getLoanMonth() {
        return loanMonth;
    }

    public void setLoanMonth(Long loanMonth) {
        this.loanMonth = loanMonth;
    }

    public Double getLoanEmiAmount() {
        return loanEmiAmount;
    }

    public void setLoanEmiAmount(Double loanEmiAmount) {
        this.loanEmiAmount = loanEmiAmount;
    }

    public Double getLoanCurrentBalance() {
        return loanCurrentBalance;
    }

    public void setLoanCurrentBalance(Double loanCurrentBalance) {
        this.loanCurrentBalance = loanCurrentBalance;
    }

    public Double getLoanEmiInterest() {
        return loanEmiInterest;
    }

    public void setLoanEmiInterest(Double loanEmiInterest) {
        this.loanEmiInterest = loanEmiInterest;
    }

    public Double getLoanEmiPrincipal() {
        return loanEmiPrincipal;
    }

    public void setLoanEmiPrincipal(Double loanEmiPrincipal) {
        this.loanEmiPrincipal = loanEmiPrincipal;
    }

    public String getLoanEmiPaymentDate() {
        return loanEmiPaymentDate;
    }

    public void setLoanEmiPaymentDate(String loanEmiPaymentDate) {
        this.loanEmiPaymentDate = loanEmiPaymentDate;
    }

    public String getLoanEmiStatus() {
        return loanEmiStatus;
    }

    public void setLoanEmiStatus(String loanEmiStatus) {
        this.loanEmiStatus = loanEmiStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

}
