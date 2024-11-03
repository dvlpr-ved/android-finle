package com.dkglabs.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by Himanshu Srivastava on 19,September,2023
 * Project e_savari
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "amount",
        "customerEmail",
        "customerId",
        "customerName",
        "customerPhone",
        "loanEmiId",
        "loanId",
        "source"
})
public class PaymentOrderRequest {
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("customerEmail")
    private String customerEmail;
    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("customerName")
    private String customerName;
    @JsonProperty("customerPhone")
    private String customerPhone;
    @JsonProperty("loanEmiId")
    private String loanEmiId;
    @JsonProperty("loanId")
    private String loanId;
    @JsonProperty("source")
    private String source;

    public PaymentOrderRequest() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getLoanEmiId() {
        return loanEmiId;
    }

    public void setLoanEmiId(String loanEmiId) {
        this.loanEmiId = loanEmiId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
