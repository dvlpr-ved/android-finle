package com.dkglabs.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LoanApplicantEMI")
public class LoanApplicantEMI {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "loanEmiId")
    private String loanEmiId;

    @NonNull
    @ColumnInfo(name = "loanId")
    private String loanId;

    @NonNull
    @ColumnInfo(name = "loanMonth")
    private Long loanMonth;

    @NonNull
    @ColumnInfo(name = "loanEmiAmount")
    private Double loanEmiAmount;

    @ColumnInfo(name = "loanCurrentBalance")
    private Double loanCurrentBalance;

    @ColumnInfo(name = "loanEmiInterest")
    private Double loanEmiInterest;

    @ColumnInfo(name = "loanEmiPrincipal")
    private Double loanEmiPrincipal;

    @ColumnInfo(name = "loanEmiPaymentDate")
    private String loanEmiPaymentDate;

    @ColumnInfo(name = "loanEmiStatus")
    private String loanEmiStatus;

    @ColumnInfo(name = "remarks")
    private String remarks;

    @ColumnInfo(name = "createdBy")
    private String createdBy;

    @ColumnInfo(name = "createdDate")
    private String createdDate;

    @ColumnInfo(name = "updatedDate")
    private String updatedDate;

    // Getter and Setter methods for each field

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
