package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseResponse<T> {

    @JsonProperty("responseStatus")
    private ResponseStatus responseStatus;

    @JsonProperty("responseData")
    private T responseData;

    public T getResponseData() {
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
