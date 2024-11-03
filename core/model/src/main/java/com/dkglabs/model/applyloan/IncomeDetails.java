package com.dkglabs.model.applyloan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "annualIncome",
        "borrowerType",
        "dependentMember",
        "earningMemberFirstName",
        "earningMemberLastName",
        "orgName",
        "otherLoan",
        "otherLoanAmount",
        "profession",
        "salary"
})
public class IncomeDetails implements Serializable {

    @JsonProperty("annualIncome")
    private String annualIncome;
    @JsonProperty("borrowerType")
    private String borrowerType;
    @JsonProperty("dependentMember")
    private String dependentMember;
    @JsonProperty("earningMemberFirstName")
    private String earningMemberFirstName;
    @JsonProperty("earningMemberLastName")
    private String earningMemberLastName;
    @JsonProperty("orgName")
    private String orgName;
    @JsonProperty("otherLoan")
    private String otherLoan;
    @JsonProperty("otherLoanAmount")
    private String otherLoanAmount;
    @JsonProperty("profession")
    private String profession;
    @JsonProperty("salary")
    private String salary;

    @JsonProperty("numberOfEarningMember")
    private String numberOfEarningMember;

    public String getNumberOfEarningMember() {
        return numberOfEarningMember;
    }

    public void setNumberOfEarningMember(String numberOfEarningMember) {
        this.numberOfEarningMember = numberOfEarningMember;
    }

    @JsonProperty("annualIncome")
    public String getAnnualIncome() {
        return annualIncome;
    }

    @JsonProperty("annualIncome")
    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }

    @JsonProperty("borrowerType")
    public String getBorrowerType() {
        return borrowerType;
    }

    @JsonProperty("borrowerType")
    public void setBorrowerType(String borrowerType) {
        this.borrowerType = borrowerType;
    }

    @JsonProperty("dependentMember")
    public String getDependentMember() {
        return dependentMember;
    }

    @JsonProperty("dependentMember")
    public void setDependentMember(String dependentMember) {
        this.dependentMember = dependentMember;
    }

    @JsonProperty("earningMemberFirstName")
    public String getEarningMemberFirstName() {
        return earningMemberFirstName;
    }

    @JsonProperty("earningMemberFirstName")
    public void setEarningMemberFirstName(String earningMemberFirstName) {
        this.earningMemberFirstName = earningMemberFirstName;
    }

    @JsonProperty("earningMemberLastName")
    public String getEarningMemberLastName() {
        return earningMemberLastName;
    }

    @JsonProperty("earningMemberLastName")
    public void setEarningMemberLastName(String earningMemberLastName) {
        this.earningMemberLastName = earningMemberLastName;
    }

    @JsonProperty("orgName")
    public String getOrgName() {
        return orgName;
    }

    @JsonProperty("orgName")
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @JsonProperty("otherLoan")
    public String getOtherLoan() {
        return otherLoan;
    }

    @JsonProperty("otherLoan")
    public void setOtherLoan(String otherLoan) {
        this.otherLoan = otherLoan;
    }

    @JsonProperty("otherLoanAmount")
    public String getOtherLoanAmount() {
        return otherLoanAmount;
    }

    @JsonProperty("otherLoanAmount")
    public void setOtherLoanAmount(String otherLoanAmount) {
        this.otherLoanAmount = otherLoanAmount;
    }

    @JsonProperty("profession")
    public String getProfession() {
        return profession;
    }

    @JsonProperty("profession")
    public void setProfession(String profession) {
        this.profession = profession;
    }

    @JsonProperty("salary")
    public String getSalary() {
        return salary;
    }

    @JsonProperty("salary")
    public void setSalary(String salary) {
        this.salary = salary;
    }


}
