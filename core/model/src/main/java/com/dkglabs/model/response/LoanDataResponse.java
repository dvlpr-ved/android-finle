package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by Himanshu Srivastava on 23,August,2023
 * Project e_savari
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanDataResponse implements Serializable {
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("loanId")
    private String loanId;
    @JsonProperty("nbfcLoanId")
    private String nbfcLoanId;
    @JsonProperty("loanType")
    private String loanType;
    @JsonProperty("loanAmount")
    private Integer loanAmount;
    @JsonProperty("rateOfInterest")
    private Double rateOfInterest;
    @JsonProperty("loanTotalAmount")
    private Integer loanTotalAmount;
    @JsonProperty("loanTotalDueAmount")
    private Integer loanTotalDueAmount;
    @JsonProperty("loanInterest")
    private Integer loanInterest;
    @JsonProperty("loanTenure")
    private Integer loanTenure;
    @JsonProperty("loanEmiAmount")
    private Integer loanEmiAmount;
    @JsonProperty("loanEmiPaymentDate")
    private String loanEmiPaymentDate;
    @JsonProperty("loanEmiPaymentMode")
    private String loanEmiPaymentMode;
    @JsonProperty("loanStatus")
    private String loanStatus;
    @JsonProperty("remarks")
    private String remarks;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("createdDate")
    private String createdDate;
    @JsonProperty("updatedDate")
    private String updatedDate;
    @JsonProperty("loanEmiFrequency")
    private String loanEmiFrequency;
    @JsonProperty("emandateStatus")
    private String emandateStatus;
    @JsonProperty("loanEmiDetailsDtoList")
    private List<LoanEmiDetailResponse> loanEmiDetailsList;
}
