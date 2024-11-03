package com.dkglabs.model.applyloan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

/**
 * Created by Himanshu Srivastava on 07,September,2023
 * Project e_savari
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "consumerNo",
        "electricityProvider",
        "installationNumber",
        "mobileNo"
})
public class ElectricityBillDetails implements Serializable {
    /*
    "electricityBillDetailsDto": {
            "consumerNo": "string",
            "electricityProvider": "string",
            "installationNumber": "string",
            "mobileNo": "string"
        }
    */

    @JsonProperty("consumerNo")
    private String consumerNo;
    @JsonProperty("electricityProvider")
    private String electricityProvider;
    @JsonProperty("installationNumber")
    private String installationNumber;
    @JsonProperty("mobileNo")
    private String mobileNo;

    public ElectricityBillDetails() {
    }

    public String getConsumerNo() {
        return consumerNo;
    }

    public void setConsumerNo(String consumerNo) {
        this.consumerNo = consumerNo;
    }

    public String getElectricityProvider() {
        return electricityProvider;
    }

    public void setElectricityProvider(String electricityProvider) {
        this.electricityProvider = electricityProvider;
    }

    public String getInstallationNumber() {
        return installationNumber;
    }

    public void setInstallationNumber(String installationNumber) {
        this.installationNumber = installationNumber;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
