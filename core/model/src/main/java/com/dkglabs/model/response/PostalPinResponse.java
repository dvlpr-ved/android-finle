package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * Created by Himanshu Srivastava on 19,June,2023
 * Project e_savari
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Message",
        "Status",
        "PostOffice"
})
public class PostalPinResponse {
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("PostOffice")
    private List<PostOffice> postOffice;

    @JsonProperty("Message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("Message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("Status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("Status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("PostOffice")
    public List<PostOffice> getPostOffice() {
        return postOffice;
    }

    @JsonProperty("PostOffice")
    public void setPostOffice(List<PostOffice> postOffice) {
        this.postOffice = postOffice;
    }

}
