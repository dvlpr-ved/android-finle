package com.dkglabs.model.response;

import com.dkglabs.model.applyloan.DocDetails;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Himanshu Srivastava on 10,July,2023
 * Project e_savari
 */
public class RequireDocDetailResponse {
    @JsonProperty("requiredNbfcDocDetailList")
    private List<DocDetails> requiredNbfcDocDetailList;
    @JsonProperty("optionalNbfcDocDetailList")
    private List<DocDetails> optionalNbfcDocDetailList;

    public RequireDocDetailResponse() {
    }

    public List<DocDetails> getRequiredNbfcDocDetailList() {
        return requiredNbfcDocDetailList;
    }

    public void setRequiredNbfcDocDetailList(List<DocDetails> requiredNbfcDocDetailList) {
        this.requiredNbfcDocDetailList = requiredNbfcDocDetailList;
    }

    public List<DocDetails> getOptionalNbfcDocDetailList() {
        return optionalNbfcDocDetailList;
    }

    public void setOptionalNbfcDocDetailList(List<DocDetails> optionalNbfcDocDetailList) {
        this.optionalNbfcDocDetailList = optionalNbfcDocDetailList;
    }
}
