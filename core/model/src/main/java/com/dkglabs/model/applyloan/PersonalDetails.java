package com.dkglabs.model.applyloan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dateOfBirth",
        "firstName",
        "gender",
        "guardiansFirstName",
        "guardiansLastName",
        "lastName",
        "maritalStatus",
        "mobileNumber",
        "name",
        "phoneNumber",
        "qualification",
        "residenceOwnership",
        "yearOfResidence"
})
public class PersonalDetails implements Serializable {

    @JsonProperty("dateOfBirth")
    private String dateOfBirth;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("guardiansFirstName")
    private String guardiansFirstName;
    @JsonProperty("guardiansLastName")
    private String guardiansLastName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("maritalStatus")
    private String maritalStatus;
    @JsonProperty("mobileNumber")
    private String mobileNumber;
    @JsonProperty("name")
    private String name;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("qualification")
    private String qualification;
    @JsonProperty("residenceOwnership")
    private String residenceOwnership;
    @JsonProperty("yearOfResidence")
    private String yearOfResidence;

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGuardiansFirstName() {
        return guardiansFirstName;
    }

    public void setGuardiansFirstName(String guardiansFirstName) {
        this.guardiansFirstName = guardiansFirstName;
    }

    public String getGuardiansLastName() {
        return guardiansLastName;
    }

    public void setGuardiansLastName(String guardiansLastName) {
        this.guardiansLastName = guardiansLastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getResidenceOwnership() {
        return residenceOwnership;
    }

    public void setResidenceOwnership(String residenceOwnership) {
        this.residenceOwnership = residenceOwnership;
    }

    public String getYearOfResidence() {
        return yearOfResidence;
    }

    public void setYearOfResidence(String yearOfResidence) {
        this.yearOfResidence = yearOfResidence;
    }
}
