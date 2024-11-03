package com.dkglabs.model;

public class CollectionCard {
    private Long totalLoanDue;
    private Double totalLoanEmiDueAmount;
    private Double totalLateFeeAmount;
    private Double totalLastEmiTxnAmount;
    private Double totalLoanAmount;
    private Double totalPendingEmiAmount;

    public CollectionCard() {

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
