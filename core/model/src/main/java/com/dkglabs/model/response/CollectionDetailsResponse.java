package com.dkglabs.model.response;

import com.dkglabs.model.response.LoanApplicantResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "collectionDetailsDtoList",
        "totalLoanDue",
        "totalLoanEmiDueAmount",
        "totalLateFeeAmount",
        "totalLastEmiTxnAmount",
        "totalLoanAmount",
        "totalPendingEmiAmount"
})
public class CollectionDetailsResponse {

    @JsonProperty("collectionDetailsDtoList")
    private List<LoanApplicantResponse> collectionDetailsDtoList;
    @JsonProperty("totalLoanDue")
    private Long totalLoanDue;
    @JsonProperty("totalLoanEmiDueAmount")
    private Double totalLoanEmiDueAmount;
    @JsonProperty("totalLateFeeAmount")
    private Double totalLateFeeAmount;
    @JsonProperty("totalLastEmiTxnAmount")
    private Double totalLastEmiTxnAmount;
    @JsonProperty("totalLoanAmount")
    private Double totalLoanAmount;
    @JsonProperty("totalPendingEmiAmount")
    private Double totalPendingEmiAmount;

    /**
     * No args constructor for use in serialization
     */
    public CollectionDetailsResponse() {
    }

    public List<LoanApplicantResponse> getCollectionDetailsDtoList() {
        return collectionDetailsDtoList;
    }

    public void setCollectionDetailsDtoList(List<LoanApplicantResponse> collectionDetailsDtoList) {
        this.collectionDetailsDtoList = collectionDetailsDtoList;
    }

    public Long getTotalLoanDue() {
        return totalLoanDue;
    }

    public void setTotalLoanDue(Long totalLoanDue) {
        this.totalLoanDue = totalLoanDue;
    }

    public Double getTotalLoanEmiDueAmount() {
        return totalLoanEmiDueAmount;
    }

    public void setTotalLoanEmiDueAmount(Double totalLoanEmiDueAmount) {
        this.totalLoanEmiDueAmount = totalLoanEmiDueAmount;
    }

    public Double getTotalLateFeeAmount() {
        return totalLateFeeAmount;
    }

    public void setTotalLateFeeAmount(Double totalLateFeeAmount) {
        this.totalLateFeeAmount = totalLateFeeAmount;
    }

    public Double getTotalLastEmiTxnAmount() {
        return totalLastEmiTxnAmount;
    }

    public void setTotalLastEmiTxnAmount(Double totalLastEmiTxnAmount) {
        this.totalLastEmiTxnAmount = totalLastEmiTxnAmount;
    }

    public Double getTotalLoanAmount() {
        return totalLoanAmount;
    }

    public void setTotalLoanAmount(Double totalLoanAmount) {
        this.totalLoanAmount = totalLoanAmount;
    }

    public Double getTotalPendingEmiAmount() {
        return totalPendingEmiAmount;
    }

    public void setTotalPendingEmiAmount(Double totalPendingEmiAmount) {
        this.totalPendingEmiAmount = totalPendingEmiAmount;
    }
}
