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
        "amount",
        "createDate",
        "orderId",
        "paymentSessionId"
})
public class PaymentOrderResponse {
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("createDate")
    private String createDate;
    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("paymentSessionId")
    private String paymentSessionId;

    public PaymentOrderResponse() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPaymentSessionId() {
        return paymentSessionId;
    }

    public void setPaymentSessionId(String paymentSessionId) {
        this.paymentSessionId = paymentSessionId;
    }
}
