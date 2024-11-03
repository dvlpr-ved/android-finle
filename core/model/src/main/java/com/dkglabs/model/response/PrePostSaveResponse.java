package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PrePostSaveResponse implements Serializable {
    @JsonProperty("responseData")
    private String responseData;
    public String getResponseData() {
        return responseData;
    }

    public PrePostSaveResponse(String responseData) {
        this.responseData = responseData;
    }

    public PrePostSaveResponse() {
    }
    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }
}
