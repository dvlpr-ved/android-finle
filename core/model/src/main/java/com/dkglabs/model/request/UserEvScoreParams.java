package com.dkglabs.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "userId",
        "loanId",
        "areaOfHouseInSqFt",
        "areaOfResidence",
        "criminalOffence",
        "girlChild",
        "healthAndLifeInsurance",
        "healthIssue",
        "hobbies",
        "houseType",
        "locationOfHouse",
        "numberOfFamilyMembers",
        "numberOfKids",
        "numberOfKidsInSchool",
        "parkingSpace",
        "politicalConnection",
        "severityOfCriminalOffence",
        "teetotaler"
})
public class UserEvScoreParams {

    @JsonProperty("userId")
    private String userId;
    @JsonProperty("loanId")
    private String loanId;
    @JsonProperty("areaOfHouseInSqFt")
    private String areaOfHouseInSqFt;
    @JsonProperty("areaOfResidence")
    private String areaOfResidence;
    @JsonProperty("criminalOffence")
    private String criminalOffence;
    @JsonProperty("girlChild")
    private String girlChild;
    @JsonProperty("healthAndLifeInsurance")
    private String healthAndLifeInsurance;
    @JsonProperty("healthIssue")
    private String healthIssue;
    @JsonProperty("hobbies")
    private String hobbies;
    @JsonProperty("houseType")
    private String houseType;
    @JsonProperty("locationOfHouse")
    private String locationOfHouse;
    @JsonProperty("numberOfFamilyMembers")
    private String numberOfFamilyMembers;
    @JsonProperty("numberOfKids")
    private String numberOfKids;
    @JsonProperty("numberOfKidsInSchool")
    private String numberOfKidsInSchool;
    @JsonProperty("parkingSpace")
    private String parkingSpace;
    @JsonProperty("politicalConnection")
    private String politicalConnection;
    @JsonProperty("severityOfCriminalOffence")
    private String severityOfCriminalOffence;
    @JsonProperty("teetotaler")
    private String teetotaler;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getAreaOfHouseInSqFt() {
        return areaOfHouseInSqFt;
    }

    public void setAreaOfHouseInSqFt(String areaOfHouseInSqFt) {
        this.areaOfHouseInSqFt = areaOfHouseInSqFt;
    }

    public String getAreaOfResidence() {
        return areaOfResidence;
    }

    public void setAreaOfResidence(String areaOfResidence) {
        this.areaOfResidence = areaOfResidence;
    }

    public String getCriminalOffence() {
        return criminalOffence;
    }

    public void setCriminalOffence(String criminalOffence) {
        this.criminalOffence = criminalOffence;
    }

    public String getGirlChild() {
        return girlChild;
    }

    public void setGirlChild(String girlChild) {
        this.girlChild = girlChild;
    }

    public String getHealthAndLifeInsurance() {
        return healthAndLifeInsurance;
    }

    public void setHealthAndLifeInsurance(String healthAndLifeInsurance) {
        this.healthAndLifeInsurance = healthAndLifeInsurance;
    }

    public String getHealthIssue() {
        return healthIssue;
    }

    public void setHealthIssue(String healthIssue) {
        this.healthIssue = healthIssue;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getLocationOfHouse() {
        return locationOfHouse;
    }

    public void setLocationOfHouse(String locationOfHouse) {
        this.locationOfHouse = locationOfHouse;
    }

    public String getNumberOfFamilyMembers() {
        return numberOfFamilyMembers;
    }

    public void setNumberOfFamilyMembers(String numberOfFamilyMembers) {
        this.numberOfFamilyMembers = numberOfFamilyMembers;
    }

    public String getNumberOfKids() {
        return numberOfKids;
    }

    public void setNumberOfKids(String numberOfKids) {
        this.numberOfKids = numberOfKids;
    }

    public String getNumberOfKidsInSchool() {
        return numberOfKidsInSchool;
    }

    public void setNumberOfKidsInSchool(String numberOfKidsInSchool) {
        this.numberOfKidsInSchool = numberOfKidsInSchool;
    }

    public String getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(String parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public String getPoliticalConnection() {
        return politicalConnection;
    }

    public void setPoliticalConnection(String politicalConnection) {
        this.politicalConnection = politicalConnection;
    }

    public String getSeverityOfCriminalOffence() {
        return severityOfCriminalOffence;
    }

    public void setSeverityOfCriminalOffence(String severityOfCriminalOffence) {
        this.severityOfCriminalOffence = severityOfCriminalOffence;
    }

    public String getTeetotaler() {
        return teetotaler;
    }

    public void setTeetotaler(String teetotaler) {
        this.teetotaler = teetotaler;
    }
}