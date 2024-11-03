package com.dkglabs.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by Robin Kumar on 1/12/2023
 * Partner Application
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "additionalDocNumber",
        "batteryNumber",
        "chassisNumber",
        "dealerId",
        "dispatchNumber",
        "insuranceNumber",
        "invoiceNumber",
        "loanId",
        "motorNumber",
        "passbook",
        "registrationNumber"
})
public class PreSendRequest {
    @JsonProperty("additionalDocNumber")
    private String additionalDocNumber;
    @JsonProperty("batteryNumber")
    private String batteryNumber;
    @JsonProperty("chassisNumber")
    private String chassisNumber;
    @JsonProperty("dealerId")
    private String dealerId;
    @JsonProperty("dispatchNumber")
    private String dispatchNumber;
    @JsonProperty("insuranceNumber")
    private String insuranceNumber;
    @JsonProperty("invoiceNumber")
    private String invoiceNumber;
    @JsonProperty("loanId")
    private String loanId;
    @JsonProperty("motorNumber")
    private String motorNumber;
    @JsonProperty("passbook")
    private String passbook;
    @JsonProperty("registrationNumber")
    private String registrationNumber;

    // Getter methods
    public String getAdditionalDocNumber() {
        return additionalDocNumber;
    }

    public String getBatteryNumber() {
        return batteryNumber;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public String getDealerId() {
        return dealerId;
    }

    public String getDispatchNumber() {
        return dispatchNumber;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getLoanId() {
        return loanId;
    }

    public String getMotorNumber() {
        return motorNumber;
    }

    public String getPassbook() {
        return passbook;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    // Setter methods
    public void setAdditionalDocNumber(String additionalDocNumber) {
        this.additionalDocNumber = additionalDocNumber;
    }

    public void setBatteryNumber(String batteryNumber) {
        this.batteryNumber = batteryNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public void setDispatchNumber(String dispatchNumber) {
        this.dispatchNumber = dispatchNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public void setMotorNumber(String motorNumber) {
        this.motorNumber = motorNumber;
    }

    public void setPassbook(String passbook) {
        this.passbook = passbook;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
