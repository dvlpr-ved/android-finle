package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenerateContractResponse implements Serializable {
    @JsonProperty("status")
    private String status;
    @JsonProperty("statusDesp")
    private String statusDesp;
    @JsonProperty("url")
    private String url;
    @JsonProperty("templateName")
    private String templateName;
}
