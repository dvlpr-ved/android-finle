package com.dkglabs.model.applyloan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "aadhaarFlag",
        "aadhaarNo",
        "electricityBillDetailsDto",
        "electricityBillFlag",
        "panNo"
})
public class DocumentDetails implements Serializable {
    /*
    "documentDetails": {
            "aadhaarFlag": "string",
            "aadhaarNo": "string",
            "electricityBillDetailsDto": {
                "consumerNo": "string",
                "electricityProvider": "string",
                "installationNumber": "string",
                "mobileNo": "string"
            },
            "electricityBillFlag": "string",
            "panNo": "string",
            "uploadedDocDetailsDtoList": [
            {
                "docName": "string",
                "vrfCode": "string",
                "vrfsCode": "string"
            }
        ]
    }
    */
    @JsonProperty("aadhaarFlag")
    private String aadhaarFlag;
    @JsonProperty("aadhaarNo")
    private String aadhaarNo;
    @JsonProperty("electricityBillDetailsDto")
    private ElectricityBillDetails electricityBillDetailsDto;
    @JsonProperty("electricityBillFlag")
    private String electricityBillFlag;
    @JsonProperty("panNo")
    private String panNo;

    @JsonProperty("aadhaarFlag")
    public String getAadhaarFlag() {
        return aadhaarFlag;
    }

    @JsonProperty("aadhaarFlag")
    public void setAadhaarFlag(String aadhaarFlag) {
        this.aadhaarFlag = aadhaarFlag;
    }

    @JsonProperty("aadhaarNo")
    public String getAadhaarNo() {
        return aadhaarNo;
    }

    @JsonProperty("aadhaarNo")
    public void setAadhaarNo(String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
    }

    public ElectricityBillDetails getElectricityBillDetailsDto() {
        return electricityBillDetailsDto;
    }

    public void setElectricityBillDetailsDto(ElectricityBillDetails electricityBillDetailsDto) {
        this.electricityBillDetailsDto = electricityBillDetailsDto;
    }

    public String getElectricityBillFlag() {
        return electricityBillFlag;
    }

    public void setElectricityBillFlag(String electricityBillFlag) {
        this.electricityBillFlag = electricityBillFlag;
    }

    @JsonProperty("panNo")
    public String getPanNo() {
        return panNo;
    }

    @JsonProperty("panNo")
    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }


}
