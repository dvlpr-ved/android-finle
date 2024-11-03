package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "country",
        "dist",
        "state",
        "po",
        "loc",
        "vtc",
        "subdist",
        "street",
        "house",
        "landmark"
})
public class AadhaarAddress implements Serializable {

    @JsonProperty("country")
    private String country;
    @JsonProperty("dist")
    private String dist;
    @JsonProperty("state")
    private String state;
    @JsonProperty("po")
    private String po;
    @JsonProperty("loc")
    private String loc;
    @JsonProperty("vtc")
    private String vtc;
    @JsonProperty("subdist")
    private String subdist;
    @JsonProperty("street")
    private String street;
    @JsonProperty("house")
    private String house;
    @JsonProperty("landmark")
    private String landmark;

    public AadhaarAddress() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getVtc() {
        return vtc;
    }

    public void setVtc(String vtc) {
        this.vtc = vtc;
    }

    public String getSubdist() {
        return subdist;
    }

    public void setSubdist(String subdist) {
        this.subdist = subdist;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    @Override
    public String toString() {

        String sb = (house.isEmpty() ? "" : house + " ") +
                (street.isEmpty() ? "" : street + " ") +
                (loc.isEmpty() ? "" : loc + " ") +
                (vtc.isEmpty() ? "" : vtc + " ") +
                (landmark.isEmpty() ? "" : landmark + " ") +
                (po.isEmpty() ? "" : po + " ") +
                (subdist.isEmpty() ? "" : subdist + " ") +
                (dist.isEmpty() ? "" : dist + " ") +
                (state.isEmpty() ? "" : state + " ") +
                (country.isEmpty() ? "" : country + " ");

        return sb;
    }

    public String getAddressLineOne() {

        String sb = (house.isEmpty() ? "" : house + " ") +
                (street.isEmpty() ? "" : street + " ") +
                (loc.isEmpty() ? "" : loc + " ") +
                (vtc.isEmpty() ? "" : vtc + " ") +
                (landmark.isEmpty() ? "" : landmark + " ");

        return sb;
    }
}
