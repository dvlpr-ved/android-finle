package com.dkglabs.model.applyloan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "endDate",
        "ldg",
        "ldgCode",
        "ldgName",
        "loanApprovedAmt",
        "loanApprovedFlag",
        "loanDenyFlag",
        "loanEstimatedAmt",
        "loanRequestAmt",
        "startDate",
        "status"
})
public class LoanDetail implements Serializable {

    @JsonProperty("endDate")
    private String endDate;
    @JsonProperty("ldg")
    private String ldg;
    @JsonProperty("ldgCode")
    private String ldgCode;
    @JsonProperty("ldgName")
    private String ldgName;
    @JsonProperty("loanApprovedAmt")
    private String loanApprovedAmt;
    @JsonProperty("loanApprovedFlag")
    private String loanApprovedFlag;
    @JsonProperty("loanDenyFlag")
    private String loanDenyFlag;
    @JsonProperty("loanEstimatedAmt")
    private String loanEstimatedAmt;
    @JsonProperty("loanRequestAmt")
    private String loanRequestAmt;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("status")
    private String status;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLdg() {
        return ldg;
    }

    public void setLdg(String ldg) {
        this.ldg = ldg;
    }

    public String getLdgCode() {
        return ldgCode;
    }

    public void setLdgCode(String ldgCode) {
        this.ldgCode = ldgCode;
    }

    public String getLdgName() {
        return ldgName;
    }

    public void setLdgName(String ldgName) {
        this.ldgName = ldgName;
    }

    public String getLoanApprovedAmt() {
        return loanApprovedAmt;
    }

    public void setLoanApprovedAmt(String loanApprovedAmt) {
        this.loanApprovedAmt = loanApprovedAmt;
    }

    public String getLoanApprovedFlag() {
        return loanApprovedFlag;
    }

    public void setLoanApprovedFlag(String loanApprovedFlag) {
        this.loanApprovedFlag = loanApprovedFlag;
    }

    public String getLoanDenyFlag() {
        return loanDenyFlag;
    }

    public void setLoanDenyFlag(String loanDenyFlag) {
        this.loanDenyFlag = loanDenyFlag;
    }

    public String getLoanEstimatedAmt() {
        return loanEstimatedAmt;
    }

    public void setLoanEstimatedAmt(String loanEstimatedAmt) {
        this.loanEstimatedAmt = loanEstimatedAmt;
    }

    public String getLoanRequestAmt() {
        return loanRequestAmt.trim().isEmpty() ? "0" : loanRequestAmt;
    }

    public void setLoanRequestAmt(String loanRequestAmt) {
        this.loanRequestAmt = loanRequestAmt;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
