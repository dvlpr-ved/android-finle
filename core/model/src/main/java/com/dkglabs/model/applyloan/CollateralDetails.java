package com.dkglabs.model.applyloan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "collateralSecurity",
        "collateralType",
        "collateralValue"
})
public class CollateralDetails implements Serializable {

    @JsonProperty("collateralSecurity")
    private String collateralSecurity;
    @JsonProperty("collateralType")
    private String collateralType;
    @JsonProperty("collateralValue")
    private String collateralValue;

    public String getCollateralSecurity() {
        return collateralSecurity;
    }

    public void setCollateralSecurity(String collateralSecurity) {
        this.collateralSecurity = collateralSecurity;
    }

    public String getCollateralType() {
        return collateralType;
    }

    public void setCollateralType(String collateralType) {
        this.collateralType = collateralType;
    }

    public String getCollateralValue() {
        return collateralValue;
    }

    public void setCollateralValue(String collateralValue) {
        this.collateralValue = collateralValue;
    }
}
