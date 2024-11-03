package com.dkglabs.model.request;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoanEmiDetailsDto implements Serializable {
    @JsonProperty("loanCurrentBalance")
    private Double loanCurrentBalance;

    @JsonProperty("loanEmiAmount")
    private Double loanEmiAmount;

    @JsonProperty("loanEmiFrequency")
    private String loanEmiFrequency;

    @JsonProperty("loanEmiId")
    private String loanEmiId;

    @JsonProperty("loanEmiInterest")
    private Double loanEmiInterest;

    @JsonProperty("loanEmiNumber")
    private String loanEmiNumber;

    @JsonProperty("loanEmiPrincipal")
    private Double loanEmiPrincipal;

    @JsonProperty("loanEmiStatus")
    private String loanEmiStatus;

    @JsonProperty("loanId")
    private String loanId;

}