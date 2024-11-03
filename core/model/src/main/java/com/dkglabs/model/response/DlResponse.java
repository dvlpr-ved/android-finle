package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Himanshu Srivastava on 30,June,2023
 * Project e_savari
 */
public class DlResponse implements Serializable {

    @JsonProperty("documentType")
    private String documentType;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("doi")
    private String doi;
    @JsonProperty("name")
    private String name;
    @JsonProperty("addressString")
    private String addressString;
    @JsonProperty("pin")
    private String pin;
    @JsonProperty("doe")
    private String doe;

    public DlResponse() {
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDoe() {
        return doe;
    }

    public void setDoe(String doe) {
        this.doe = doe;
    }
}
