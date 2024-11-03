package com.dkglabs.apply_loan.model;

/**
 * Created by Himanshu Srivastava on 05,September,2023
 * Project e_savari
 */
public class ElectricityProvider {
    String providerCode;
    String providerName;

    public ElectricityProvider(String providerCode, String providerName) {
        this.providerCode = providerCode;
        this.providerName = providerName;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    @Override
    public String toString() {
        return providerName;
    }
}
