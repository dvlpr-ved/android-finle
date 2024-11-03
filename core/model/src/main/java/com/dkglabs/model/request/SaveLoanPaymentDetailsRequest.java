package com.dkglabs.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;

@Data
public class SaveLoanPaymentDetailsRequest {
    @JsonProperty("loanAmount")
    private double loanAmount;

    @JsonProperty("loanEmiAmount")
    private double loanEmiAmount;

    @JsonProperty("loanEmiDetailsDtoList")
    private List<LoanEmiDetailsDto> loanEmiDetailsDtoList;

    @JsonProperty("loanEmiFrequency")
    private String loanEmiFrequency;

    @JsonProperty("loanEmiPaymentDate")
    private String loanEmiPaymentDate;

    @JsonProperty("loanEmiPaymentMode")
    private String loanEmiPaymentMode;

    @JsonProperty("loanId")
    private String loanId;

    @JsonProperty("emailId")
    private String emailId;

    @JsonProperty("loanInterest")
    private double loanInterest;

    @JsonProperty("loanStatus")
    private String loanStatus;

    @JsonProperty("loanTenure")
    private int loanTenure;

    @JsonProperty("loanTotalAmount")
    private Double loanTotalAmount;

    @JsonProperty("loanTotalDueAmount")
    private Double loanTotalDueAmount;

    @JsonProperty("loanType")
    private String loanType;

    @JsonProperty("nbfcLoanId")
    private String nbfcLoanId;

    @JsonProperty("rateOfInterest")
    private double rateOfInterest;

    @JsonProperty("remarks")
    private String remarks;

    @JsonProperty("updatedDate")
    private String updatedDate;

    @JsonProperty("userId")
    private String userId;

}
