package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data",
        "statusCode",
        "message",
        "loanId"
})
public class AadhaarOtpResponse implements Serializable {
    @JsonProperty("data")
    private OtpDataResponse data;
    @JsonProperty("statusCode")
    private Long statusCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("loanId")
    private String loanId;

    public AadhaarOtpResponse() {

    }

    public OtpDataResponse getData() {
        return data;
    }

    public void setData(OtpDataResponse data) {
        this.data = data;
    }

    public Long getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Long statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }
}
