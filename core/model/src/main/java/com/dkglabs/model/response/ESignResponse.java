package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ESignResponse implements Serializable {

    @JsonProperty("customerId")
    public String customerId;
    @JsonProperty("username")
    public String username;
    @JsonProperty("contractId")
    public String contractId;
    @JsonProperty("pdf")
    public String pdf;
    @JsonProperty("initialContractHash")
    public String initialContractHash;
    @JsonProperty("contractName")
    public String contractName;
    @JsonProperty("contractExecuterName")
    public String contractExecuterName;
    @JsonProperty("successRedirectUrl")
    public String successRedirectUrl;
    @JsonProperty("failureRedirectUrl")
    public String failureRedirectUrl;
    @JsonProperty("contractTtl")
    public Long contractTtl;
    @JsonProperty("callbackUrl")
    public String callbackUrl;
    @JsonProperty("fileTtl")
    public Long fileTtl;
    @JsonProperty("contractExpiresOn")
    public String contractExpiresOn;
    @JsonProperty("signerdetail")
    public List<SignerDetail> signerdetail;
    @JsonProperty("contractStatus")
    public String contractStatus;
    @JsonProperty("encryptionOpted")
    public Boolean encryptionOpted;
    @JsonProperty("esignProvider")
    public String esignProvider;
}
