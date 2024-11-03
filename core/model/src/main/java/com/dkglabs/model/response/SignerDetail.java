package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SignerDetail implements Serializable {
    @JsonProperty("signerId")
    public String signerId;
    @JsonProperty("signerName")
    public String signerName;
    @JsonProperty("signatureType")
    public String signatureType;
    @JsonProperty("signerGender")
    public String signerGender;
    @JsonProperty("signatures")
    public List<Signature> signatures;
    @JsonProperty("esignUrl")
    public String esignUrl;
}

@Data
class Signature implements Serializable {
    @JsonProperty("pageNo")
    public List<String> pageNo;
    @JsonProperty("signaturePosition")
    public List<String> signaturePosition;
}
