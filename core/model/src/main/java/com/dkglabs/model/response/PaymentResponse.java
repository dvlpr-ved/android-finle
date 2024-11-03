package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by Himanshu Srivastava on 19,September,2023
 * Project e_savari
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "orderId",
        "status",
        "amount",
        "loanId",
        "customerId",
        "message",
        "paymentFrom"
})
public class PaymentResponse {
    /*{
            "orderId": "11006",
            "status": "INT",
            "amount": "1",
            "loanId": "L90000048",
            "customerId": "90000048",
            "message": "NA",
            "paymentFrom": null}
    */
    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("loanId")
    private String loanId;
    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("message")
    private String message;
    @JsonProperty("paymentFrom")
    private String paymentFrom;

    public PaymentResponse() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPaymentFrom() {
        return paymentFrom;
    }

    public void setPaymentFrom(String paymentFrom) {
        this.paymentFrom = paymentFrom;
    }
}
