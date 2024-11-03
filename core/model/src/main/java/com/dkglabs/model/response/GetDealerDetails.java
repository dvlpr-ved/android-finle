package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetDealerDetails {
    @JsonProperty("dealerId")
    private String dealerId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("mgrName")
    private String managerName;

    @JsonProperty("addressLineFirst")
    private String addressLineFirst;

    @JsonProperty("addressLineSecond")
    private String addressLineSecond;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("pinCode")
    private String pinCode;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("lat")
    private String latitude;

    @JsonProperty("lng")
    private String longitude;

    @JsonProperty("isActive")
    private String isActive;

    @JsonProperty("remarks")
    private String remarks;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("createdDate")
    private String createdDate;

    @JsonProperty("updatedBy")
    private String updatedBy;

    @JsonProperty("updatedDate")
    private String updatedDate;

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
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

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
// Getters and setters (omitted for brevity)

    @Override
    public String toString() {
        return "GetDealerDetails{" +
                "dealerId='" + dealerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", managerName='" + managerName + '\'' +
                ", addressLineFirst='" + addressLineFirst + '\'' +
                ", addressLineSecond='" + addressLineSecond + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", mobile='" + mobile + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", isActive='" + isActive + '\'' +
                ", remarks='" + remarks + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                '}';
    }
}
