package com.dkglabs.model.applyloan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

/**
 * Created by Himanshu Srivastava on 21,June,2023
 * Project e_savari
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "email",
        "officeStdCode",
        "officeTelephone",
        "otherMobileNumber",
        "residenceStdCode",
        "residenceTelephone",
        "whatsappNo"
})
public class ContactDetails implements Serializable {
    @JsonProperty("email")
    private String email;
    @JsonProperty("officeStdCode")
    private String officeStdCode;
    @JsonProperty("officeTelephone")
    private String officeTelephone;
    @JsonProperty("otherMobileNumber")
    private String otherMobileNumber;
    @JsonProperty("residenceStdCode")
    private String residenceStdCode;
    @JsonProperty("residenceTelephone")
    private String residenceTelephone;
    @JsonProperty("whatsappNo")
    private String whatsappNo;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficeStdCode() {
        return officeStdCode;
    }

    public void setOfficeStdCode(String officeStdCode) {
        this.officeStdCode = officeStdCode;
    }

    public String getOfficeTelephone() {
        return officeTelephone;
    }

    public void setOfficeTelephone(String officeTelephone) {
        this.officeTelephone = officeTelephone;
    }

    public String getOtherMobileNumber() {
        return otherMobileNumber;
    }

    public void setOtherMobileNumber(String otherMobileNumber) {
        this.otherMobileNumber = otherMobileNumber;
    }

    public String getResidenceStdCode() {
        return residenceStdCode;
    }

    public void setResidenceStdCode(String residenceStdCode) {
        this.residenceStdCode = residenceStdCode;
    }

    public String getResidenceTelephone() {
        return residenceTelephone;
    }

    public void setResidenceTelephone(String residenceTelephone) {
        this.residenceTelephone = residenceTelephone;
    }

    public String getWhatsappNo() {
        return whatsappNo;
    }

    public void setWhatsappNo(String whatsappNo) {
        this.whatsappNo = whatsappNo;
    }
}
