package com.dkglabs.model.applyloan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "kycFlag",
        "vkycFlag"
})
public class KycDetails implements Serializable {

    @JsonProperty("kycFlag")
    private String kycFlag;
    @JsonProperty("vkycFlag")
    private String vkycFlag;

    @JsonProperty("kycFlag")
    public String getKycFlag() {
        return kycFlag;
    }

    @JsonProperty("kycFlag")
    public void setKycFlag(String kycFlag) {
        this.kycFlag = kycFlag;
    }

    @JsonProperty("vkycFlag")
    public String getVkycFlag() {
        return vkycFlag;
    }

    @JsonProperty("vkycFlag")
    public void setVkycFlag(String vkycFlag) {
        this.vkycFlag = vkycFlag;
    }


}
