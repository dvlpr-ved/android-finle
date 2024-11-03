package com.dkglabs.model.applyloan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accHolderName",
        "accountNumber",
        "bankAddress",
        "bankName",
        "ifscCode",
        "accountVerificationFlag"
})
public class BankDetails implements Serializable {

    @JsonProperty("accHolderName")
    private String accHolderName;
    @JsonProperty("accountNumber")
    private String accountNumber;
    @JsonProperty("bankAddress")
    private String bankAddress;
    @JsonProperty("bankName")
    private String bankName;
    @JsonProperty("ifscCode")
    private String ifscCode;
    @JsonProperty("accountVerificationFlag")
    private String accountVerificationFlag;

    @JsonProperty("accHolderName")
    public String getAccHolderName() {
        return accHolderName;
    }

    @JsonProperty("accHolderName")
    public void setAccHolderName(String accHolderName) {
        this.accHolderName = accHolderName;
    }

    @JsonProperty("accountNumber")
    public String getAccountNumber() {
        return accountNumber;
    }

    @JsonProperty("accountNumber")
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @JsonProperty("bankAddress")
    public String getBankAddress() {
        return bankAddress;
    }

    @JsonProperty("bankAddress")
    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    @JsonProperty("bankName")
    public String getBankName() {
        return bankName;
    }

    @JsonProperty("bankName")
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @JsonProperty("ifscCode")
    public String getIfscCode() {
        return ifscCode;
    }

    @JsonProperty("ifscCode")
    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getAccountVerificationFlag() {
        return accountVerificationFlag;
    }

    public void setAccountVerificationFlag(String accountVerificationFlag) {
        this.accountVerificationFlag = accountVerificationFlag;
    }
}
