package com.dkglabs.model.applyloan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "nbfcApproval",
        "softApproval"
})
public class ApprovalDetails implements Serializable {

    @JsonProperty("nbfcApproval")
    private String nbfcApproval;
    @JsonProperty("softApproval")
    private String softApproval;

    @JsonProperty("nbfcApproval")
    public String getNbfcApproval() {
        return nbfcApproval;
    }

    @JsonProperty("nbfcApproval")
    public void setNbfcApproval(String nbfcApproval) {
        this.nbfcApproval = nbfcApproval;
    }

    @JsonProperty("softApproval")
    public String getSoftApproval() {
        return softApproval;
    }

    @JsonProperty("softApproval")
    public void setSoftApproval(String softApproval) {
        this.softApproval = softApproval;
    }


}
