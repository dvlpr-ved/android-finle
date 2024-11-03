package com.dkglabs.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LoanApplicant")
public class LoanApplicant {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "loanId")
    private String loanId;

    @NonNull
    @ColumnInfo(name = "userId")
    private String userId;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "partnerCode")
    private String partnerCode;

    @ColumnInfo(name = "loanAmount")
    private Double loanAmount;

    @ColumnInfo(name = "term")
    private String term;

    @ColumnInfo(name = "loanEmiAmount")
    private Double loanEmiAmount;

    @ColumnInfo(name = "loanEmiPaymentDate")
    private String loanEmiPaymentDate;

    @ColumnInfo(name = "emiDue")
    private Long emiDue;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "lateFeeDue")
    private Double lateFeeDue;

    @ColumnInfo(name = "extraPaid")
    private Double extraPaid;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "subStatus")
    private String subStatus;

    @ColumnInfo(name = "lastPaidAmount")
    private Double lastPaidAmount;

    @ColumnInfo(name = "lastPaidDate")
    private String lastPaidDate;

    @ColumnInfo(name = "dueEmiAmount")
    private Double dueEmiAmount;

    @ColumnInfo(name = "currentEmiPaymentDate")
    private String currentEmiPaymentDate;


    // Getters and setters for each field

    @NonNull
    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(@NonNull String loanId) {
        this.loanId = loanId;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}

