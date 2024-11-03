package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Himanshu Srivastava on 30,June,2023
 * Project e_savari
 */
public class AadhaarResponse implements Serializable {

    @JsonProperty("documentType")
    private String documentType;
    @JsonProperty("name")
    private String name;
    @JsonProperty("loanId")
    private String loanId;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("addressString")
    private String addressString;
    @JsonProperty("pin")
    private String pin;

    public AadhaarResponse() {
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
