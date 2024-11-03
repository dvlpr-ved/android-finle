package com.dkglabs.model.response;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;

import java.util.Date;

public class NbfcDetails {
    @SerializedName("nbfcId")
    private String nbfcId;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("mgrName")
    private String mgrName;

    @SerializedName("addressLineFirst")
    private String addressLineFirst;

    @SerializedName("addressLineSecond")
    private String addressLineSecond;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    @SerializedName("pinCode")
    private String pinCode;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("phone")
    private String phone;

    @SerializedName("email")
    private String email;

    @SerializedName("lat")
    private Double lat;

    @SerializedName("lng")
    private Double lng;

    @SerializedName("isActive")
    private String isActive;

    @SerializedName("remarks")
    private String remarks;

    @SerializedName("createdBy")
    private String createdBy;

    @SerializedName("createdDate")
    private Date createdDate;

    @SerializedName("updatedBy")
    private String updatedBy;

    @SerializedName("updatedDate")
    private Date updatedDate;

    public String getNbfcId() {
        return nbfcId;
    }

    public void setNbfcId(String nbfcId) {
        this.nbfcId = nbfcId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMgrName() {
        return mgrName;
    }

    public void setMgrName(String mgrName) {
        this.mgrName = mgrName;
    }

    public String getAddressLineFirst() {
        return addressLineFirst;
    }

    public void setAddressLineFirst(String addressLineFirst) {
        this.addressLineFirst = addressLineFirst;
    }

    public String getAddressLineSecond() {
        return addressLineSecond;
    }

    public void setAddressLineSecond(String addressLineSecond) {
        this.addressLineSecond = addressLineSecond;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    // No manually defined constructors, getters, and setters
    public String toFormattedString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("nbfcId: ").append(nbfcId).append("\n")
                .append("firstName: ").append(firstName).append("\n")
                .append("lastName: ").append(lastName).append("\n")
                .append("mgrName: ").append(mgrName).append("\n")
                .append("addressLineFirst: ").append(addressLineFirst).append("\n")
                .append("addressLineSecond: ").append(addressLineSecond).append("\n")
                .append("city: ").append(city).append("\n")
                .append("state: ").append(state).append("\n")
                .append("pinCode: ").append(pinCode).append("\n")
                .append("mobile: ").append(mobile).append("\n")
                .append("phone: ").append(phone).append("\n")
                .append("email: ").append(email).append("\n")
                .append("lat: ").append(lat).append("\n")
                .append("lng: ").append(lng).append("\n")
                .append("isActive: ").append(isActive).append("\n")
                .append("remarks: ").append(remarks).append("\n")
                .append("createdBy: ").append(createdBy).append("\n")
                .append("createdDate: ").append(createdDate).append("\n")
                .append("updatedBy: ").append(updatedBy).append("\n")
                .append("updatedDate: ").append(updatedDate).append("\n");
        return stringBuilder.toString();
    }
}
