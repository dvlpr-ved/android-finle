package com.dkglabs.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class SaveLoanFulfillment {

    @JsonProperty("additionalDocNumber")
    private String additionalDocNumber;

    @JsonProperty("chequeNumber")
    private String chequeNumber;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("createdDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date createdDate;

    @JsonProperty("dealerId")
    private String dealerId;

    @JsonProperty("imeiNumber")
    private String imeiNumber;

    @JsonProperty("loanId")
    private String loanId;

    @JsonProperty("refOneAddress")
    private String refOneAddress;

    @JsonProperty("refOneGender")
    private String refOneGender;

    @JsonProperty("refOneIdBack")
    private String refOneIdBack;

    @JsonProperty("refOneIdFront")
    private String refOneIdFront;

    @JsonProperty("refOneMobile")
    private String refOneMobile;

    @JsonProperty("refOneName")
    private String refOneName;

    @JsonProperty("refOnePan")
    private String refOnePan;

    @JsonProperty("refOnePin")
    private String refOnePin;

    @JsonProperty("refTwoAddress")
    private String refTwoAddress;

    @JsonProperty("refTwoGender")
    private String refTwoGender;

    @JsonProperty("refTwoIdBack")
    private String refTwoIdBack;

    @JsonProperty("refTwoIdFront")
    private String refTwoIdFront;

    @JsonProperty("refTwoMobile")
    private String refTwoMobile;

    @JsonProperty("refTwoName")
    private String refTwoName;

    @JsonProperty("refTwoPan")
    private String refTwoPan;

    @JsonProperty("refTwoPin")
    private String refTwoPin;

    @JsonProperty("updatedBy")
    private String updatedBy;

    @JsonProperty("updatedDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date updatedDate;

    // Getters and Setters

    public String getAdditionalDocNumber() {
        return additionalDocNumber;
    }

    public void setAdditionalDocNumber(String additionalDocNumber) {
        this.additionalDocNumber = additionalDocNumber;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getImeiNumber() {
        return imeiNumber;
    }

    public void setImeiNumber(String imeiNumber) {
        this.imeiNumber = imeiNumber;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getRefOneAddress() {
        return refOneAddress;
    }

    public void setRefOneAddress(String refOneAddress) {
        this.refOneAddress = refOneAddress;
    }

    public String getRefOneGender() {
        return refOneGender;
    }

    public void setRefOneGender(String refOneGender) {
        this.refOneGender = refOneGender;
    }

    public String getRefOneIdBack() {
        return refOneIdBack;
    }

    public void setRefOneIdBack(String refOneIdBack) {
        this.refOneIdBack = refOneIdBack;
    }

    public String getRefOneIdFront() {
        return refOneIdFront;
    }

    public void setRefOneIdFront(String refOneIdFront) {
        this.refOneIdFront = refOneIdFront;
    }

    public String getRefOneMobile() {
        return refOneMobile;
    }

    public void setRefOneMobile(String refOneMobile) {
        this.refOneMobile = refOneMobile;
    }

    public String getRefOneName() {
        return refOneName;
    }

    public void setRefOneName(String refOneName) {
        this.refOneName = refOneName;
    }

    public String getRefOnePan() {
        return refOnePan;
    }

    public void setRefOnePan(String refOnePan) {
        this.refOnePan = refOnePan;
    }

    public String getRefOnePin() {
        return refOnePin;
    }

    public void setRefOnePin(String refOnePin) {
        this.refOnePin = refOnePin;
    }

    public String getRefTwoAddress() {
        return refTwoAddress;
    }

    public void setRefTwoAddress(String refTwoAddress) {
        this.refTwoAddress = refTwoAddress;
    }

    public String getRefTwoGender() {
        return refTwoGender;
    }

    public void setRefTwoGender(String refTwoGender) {
        this.refTwoGender = refTwoGender;
    }

    public String getRefTwoIdBack() {
        return refTwoIdBack;
    }

    public void setRefTwoIdBack(String refTwoIdBack) {
        this.refTwoIdBack = refTwoIdBack;
    }

    public String getRefTwoIdFront() {
        return refTwoIdFront;
    }

    public void setRefTwoIdFront(String refTwoIdFront) {
        this.refTwoIdFront = refTwoIdFront;
    }

    public String getRefTwoMobile() {
        return refTwoMobile;
    }

    public void setRefTwoMobile(String refTwoMobile) {
        this.refTwoMobile = refTwoMobile;
    }

    public String getRefTwoName() {
        return refTwoName;
    }

    public void setRefTwoName(String refTwoName) {
        this.refTwoName = refTwoName;
    }

    public String getRefTwoPan() {
        return refTwoPan;
    }

    public void setRefTwoPan(String refTwoPan) {
        this.refTwoPan = refTwoPan;
    }

    public String getRefTwoPin() {
        return refTwoPin;
    }

    public void setRefTwoPin(String refTwoPin) {
        this.refTwoPin = refTwoPin;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
