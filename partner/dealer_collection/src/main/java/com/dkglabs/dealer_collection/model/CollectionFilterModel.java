package com.dkglabs.dealer_collection.model;

import java.io.Serializable;

public class CollectionFilterModel implements Serializable {
    private String status;

    public CollectionFilterModel() {
    }

    public CollectionFilterModel(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
