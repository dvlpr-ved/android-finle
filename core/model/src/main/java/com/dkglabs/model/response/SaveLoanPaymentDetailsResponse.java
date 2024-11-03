package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class SaveLoanPaymentDetailsResponse implements Serializable {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("loanId")
    private String loanId;

    @JsonProperty("nbfcLoanId")
    private String nbfcLoanId;

    @JsonProperty("loanType")
    private String loanType;

    @JsonProperty("loanEmiFrequency")
    private String loanEmiFrequency;

    @JsonProperty("loanAmount")
    private double loanAmount;

    @JsonProperty("rateOfInterest")
    private double rateOfInterest;

    @JsonProperty("loanTotalAmount")
    private Double loanTotalAmount;

    @JsonProperty("loanTotalDueAmount")
    private Double loanTotalDueAmount;

    @JsonProperty("loanInterest")
    private double loanInterest;

    @JsonProperty("loanTenure")
    private int loanTenure;

    @JsonProperty("loanEmiAmount")
    private double loanEmiAmount;

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

    @JsonProperty("loanEmiDetailsDtoList")
    private List<LoanEmiDetailsDto> loanEmiDetailsDtoList;

    // Constructors, getters, and setters can be added based on your requirements.

    // Default Constructor
    public SaveLoanPaymentDetailsResponse() {
    }

    // Parameterized Constructor
    public SaveLoanPaymentDetailsResponse(String userId, String loanId, String nbfcLoanId, String loanType,
                        String loanEmiFrequency, double loanAmount, double rateOfInterest,
                        Double loanTotalAmount, Double loanTotalDueAmount, double loanInterest,
                        int loanTenure, double loanEmiAmount, String loanEmiPaymentDate,
                        String loanEmiPaymentMode, String loanStatus, String remarks,
                        String createdBy, String createdDate, String updatedDate,
                        List<LoanEmiDetailsDto> loanEmiDetailsDtoList) {
        this.userId = userId;
        this.loanId = loanId;
        this.nbfcLoanId = nbfcLoanId;
        this.loanType = loanType;
        this.loanEmiFrequency = loanEmiFrequency;
        this.loanAmount = loanAmount;
        this.rateOfInterest = rateOfInterest;
        this.loanTotalAmount = loanTotalAmount;
        this.loanTotalDueAmount = loanTotalDueAmount;
        this.loanInterest = loanInterest;
        this.loanTenure = loanTenure;
        this.loanEmiAmount = loanEmiAmount;
        this.loanEmiPaymentDate = loanEmiPaymentDate;
        this.loanEmiPaymentMode = loanEmiPaymentMode;
        this.loanStatus = loanStatus;
        this.remarks = remarks;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.loanEmiDetailsDtoList = loanEmiDetailsDtoList;
    }

    // Getters and Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getNbfcLoanId() {
        return nbfcLoanId;
    }

    public void setNbfcLoanId(String nbfcLoanId) {
        this.nbfcLoanId = nbfcLoanId;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getLoanEmiFrequency() {
        return loanEmiFrequency;
    }

    public void setLoanEmiFrequency(String loanEmiFrequency) {
        this.loanEmiFrequency = loanEmiFrequency;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getRateOfInterest() {
        return rateOfInterest;
    }

    public void setRateOfInterest(double rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }

    public Double getLoanTotalAmount() {
        return loanTotalAmount;
    }

    public void setLoanTotalAmount(Double loanTotalAmount) {
        this.loanTotalAmount = loanTotalAmount;
    }

    public Double getLoanTotalDueAmount() {
        return loanTotalDueAmount;
    }

    public void setLoanTotalDueAmount(Double loanTotalDueAmount) {
        this.loanTotalDueAmount = loanTotalDueAmount;
    }

    public double getLoanInterest() {
        return loanInterest;
    }

    public void setLoanInterest(double loanInterest) {
        this.loanInterest = loanInterest;
    }

    public int getLoanTenure() {
        return loanTenure;
    }

    public void setLoanTenure(int loanTenure) {
        this.loanTenure = loanTenure;
    }

    public double getLoanEmiAmount() {
        return loanEmiAmount;
    }

    public void setLoanEmiAmount(double loanEmiAmount) {
        this.loanEmiAmount = loanEmiAmount;
    }

    public String getLoanEmiPaymentDate() {
        return loanEmiPaymentDate;
    }

    public void setLoanEmiPaymentDate(String loanEmiPaymentDate) {
        this.loanEmiPaymentDate = loanEmiPaymentDate;
    }

    public String getLoanEmiPaymentMode() {
        return loanEmiPaymentMode;
    }

    public void setLoanEmiPaymentMode(String loanEmiPaymentMode) {
        this.loanEmiPaymentMode = loanEmiPaymentMode;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
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

    public List<LoanEmiDetailsDto> getLoanEmiDetailsDtoList() {
        return loanEmiDetailsDtoList;
    }

    public void setLoanEmiDetailsDtoList(List<LoanEmiDetailsDto> loanEmiDetailsDtoList) {
        this.loanEmiDetailsDtoList = loanEmiDetailsDtoList;
    }

    public static class LoanEmiDetailsDto {
        @JsonProperty("loanEmiId")
        private String loanEmiId;

        @JsonProperty("loanId")
        private String loanId;

        @JsonProperty("loanEmiFrequency")
        private String loanEmiFrequency;

        @JsonProperty("loanEmiNumber")
        private int loanEmiNumber;

        @JsonProperty("loanEmiAmount")
        private double loanEmiAmount;

        @JsonProperty("loanCurrentBalance")
        private double loanCurrentBalance;

        @JsonProperty("loanEmiInterest")
        private double loanEmiInterest;

        @JsonProperty("loanEmiPrincipal")
        private double loanEmiPrincipal;

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

        // Constructors, getters, and setters can be added based on your requirements.

        // Default Constructor
        public LoanEmiDetailsDto() {
        }

        // Parameterized Constructor
        public LoanEmiDetailsDto(String loanEmiId, String loanId, String loanEmiFrequency,
                                 int loanEmiNumber, double loanEmiAmount, double loanCurrentBalance,
                                 double loanEmiInterest, double loanEmiPrincipal,
                                 String loanEmiPaymentDate, String loanEmiStatus, String remarks,
                                 String createdBy, String createdDate, String updatedDate) {
            this.loanEmiId = loanEmiId;
            this.loanId = loanId;
            this.loanEmiFrequency = loanEmiFrequency;
            this.loanEmiNumber = loanEmiNumber;
            this.loanEmiAmount = loanEmiAmount;
            this.loanCurrentBalance = loanCurrentBalance;
            this.loanEmiInterest = loanEmiInterest;
            this.loanEmiPrincipal = loanEmiPrincipal;
            this.loanEmiPaymentDate = loanEmiPaymentDate;
            this.loanEmiStatus = loanEmiStatus;
            this.remarks = remarks;
            this.createdBy = createdBy;
            this.createdDate = createdDate;
            this.updatedDate = updatedDate;
        }

        // Getters and Setters

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

        public String getLoanEmiFrequency() {
            return loanEmiFrequency;
        }

        public void setLoanEmiFrequency(String loanEmiFrequency) {
            this.loanEmiFrequency = loanEmiFrequency;
        }

        public int getLoanEmiNumber() {
            return loanEmiNumber;
        }

        public void setLoanEmiNumber(int loanEmiNumber) {
            this.loanEmiNumber = loanEmiNumber;
        }

        public double getLoanEmiAmount() {
            return loanEmiAmount;
        }

        public void setLoanEmiAmount(double loanEmiAmount) {
            this.loanEmiAmount = loanEmiAmount;
        }

        public double getLoanCurrentBalance() {
            return loanCurrentBalance;
        }

        public void setLoanCurrentBalance(double loanCurrentBalance) {
            this.loanCurrentBalance = loanCurrentBalance;
        }

        public double getLoanEmiInterest() {
            return loanEmiInterest;
        }

        public void setLoanEmiInterest(double loanEmiInterest) {
            this.loanEmiInterest = loanEmiInterest;
        }

        public double getLoanEmiPrincipal() {
            return loanEmiPrincipal;
        }

        public void setLoanEmiPrincipal(double loanEmiPrincipal) {
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
}
