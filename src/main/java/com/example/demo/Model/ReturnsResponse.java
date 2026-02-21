package com.example.demo.Model;

import java.util.List;

public class ReturnsResponse {

    private Double transactionsTotalAmount;
    private Double transactionsTotalCeiling;
    private List<SavingsResult> savingsByDates;

    public Double getTransactionsTotalAmount() {
        return transactionsTotalAmount;
    }

    public void setTransactionsTotalAmount(Double transactionsTotalAmount) {
        this.transactionsTotalAmount = transactionsTotalAmount;
    }

    public Double getTransactionsTotalCeiling() {
        return transactionsTotalCeiling;
    }

    public void setTransactionsTotalCeiling(Double transactionsTotalCeiling) {
        this.transactionsTotalCeiling = transactionsTotalCeiling;
    }

    public List<SavingsResult> getSavingsByDates() {
        return savingsByDates;
    }

    public void setSavingsByDates(List<SavingsResult> savingsByDates) {
        this.savingsByDates = savingsByDates;
    }

    public ReturnsResponse(Double transactionsTotalAmount, Double transactionsTotalCeiling, List<SavingsResult> savingsByDates) {
        this.transactionsTotalAmount = transactionsTotalAmount;
        this.transactionsTotalCeiling = transactionsTotalCeiling;
        this.savingsByDates = savingsByDates;
    }

    public ReturnsResponse() {
    }
}
