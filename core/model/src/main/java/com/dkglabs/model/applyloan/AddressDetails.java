package com.dkglabs.model.applyloan;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Himanshu Srivastava on 17,June,2023
 * Project e_savari
 */
public class AddressDetails implements Serializable {
    @JsonProperty("presentAddressLineOne")
    private String presentAddressLineOne;
    @JsonProperty("presentAddressLineTwo")
    private String presentAddressLineTwo;
    @JsonProperty("presentCity")
    private String presentCity;
    @JsonProperty("presentPostalAddress")
    private String presentPostalAddress;
    @JsonProperty("presentDistrict")
    private String presentDistrict;
    @JsonProperty("presentState")
    private String presentState;
    @JsonProperty("presentCountry")
    private String presentCountry;
    @JsonProperty("permanentAddressLineOne")
    private String permanentAddressLineOne;
    @JsonProperty("permanentAddressLineTwo")
    private String permanentAddressLineTwo;
    @JsonProperty("permanentCity")
    private String permanentCity;
    @JsonProperty("permanentPostalAddress")
    private String permanentPostalAddress;
    @JsonProperty("permanentDistrict")
    private String permanentDistrict;
    @JsonProperty("permanentState")
    private String permanentState;
    @JsonProperty("permanentCountry")
    private String permanentCountry;

    public String getPresentAddressLineOne() {
        return presentAddressLineOne;
    }

    public void setPresentAddressLineOne(String presentAddressLineOne) {
        this.presentAddressLineOne = presentAddressLineOne;
    }

    public String getPresentAddressLineTwo() {
        return presentAddressLineTwo;
    }

    public void setPresentAddressLineTwo(String presentAddressLineTwo) {
        this.presentAddressLineTwo = presentAddressLineTwo;
    }

    public String getPresentCity() {
        return presentCity;
    }

    public void setPresentCity(String presentCity) {
        this.presentCity = presentCity;
    }

    public String getPresentPostalAddress() {
        return presentPostalAddress;
    }

    public void setPresentPostalAddress(String presentPostalAddress) {
        this.presentPostalAddress = presentPostalAddress;
    }

    public String getPresentDistrict() {
        return presentDistrict;
    }

    public void setPresentDistrict(String presentDistrict) {
        this.presentDistrict = presentDistrict;
    }

    public String getPresentState() {
        return presentState;
    }

    public void setPresentState(String presentState) {
        this.presentState = presentState;
    }

    public String getPresentCountry() {
        return presentCountry;
    }

    public void setPresentCountry(String presentCountry) {
        this.presentCountry = presentCountry;
    }

    public String getPermanentAddressLineOne() {
        return permanentAddressLineOne;
    }

    public void setPermanentAddressLineOne(String permanentAddressLineOne) {
        this.permanentAddressLineOne = permanentAddressLineOne;
    }

    public String getPermanentAddressLineTwo() {
        return permanentAddressLineTwo;
    }

    public void setPermanentAddressLineTwo(String permanentAddressLineTwo) {
        this.permanentAddressLineTwo = permanentAddressLineTwo;
    }

    public String getPermanentCity() {
        return permanentCity;
    }

    public void setPermanentCity(String permanentCity) {
        this.permanentCity = permanentCity;
    }

    public String getPermanentPostalAddress() {
        return permanentPostalAddress;
    }

    public void setPermanentPostalAddress(String permanentPostalAddress) {
        this.permanentPostalAddress = permanentPostalAddress;
    }

    public String getPermanentDistrict() {
        return permanentDistrict;
    }

    public void setPermanentDistrict(String permanentDistrict) {
        this.permanentDistrict = permanentDistrict;
    }

    public String getPermanentState() {
        return permanentState;
    }

    public void setPermanentState(String permanentState) {
        this.permanentState = permanentState;
    }

    public String getPermanentCountry() {
        return permanentCountry;
    }

    public void setPermanentCountry(String permanentCountry) {
        this.permanentCountry = permanentCountry;
    }
}
