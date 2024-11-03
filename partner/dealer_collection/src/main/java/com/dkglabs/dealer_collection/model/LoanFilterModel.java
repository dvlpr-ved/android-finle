package com.dkglabs.dealer_collection.model;

import java.io.Serializable;

public class LoanFilterModel implements Serializable {
    private String status;
    private String subStatus;

    public LoanFilterModel() {
    }

    public LoanFilterModel(String status, String subStatus) {
        this.status = status;
        this.subStatus = subStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }

}
