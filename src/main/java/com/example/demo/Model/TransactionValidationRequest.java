package com.example.demo.Model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class TransactionValidationRequest {

    @NotNull(message = "wage is required")
    @Positive(message = "wage must be positive")
    private Double wage;

    @NotNull(message = "transactions list is required")
    private List<Transaction> transactions;

    public Double getWage() {
        return wage;
    }

    public void setWage(Double wage) {
        this.wage = wage;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public TransactionValidationRequest(Double wage, List<Transaction> transactions) {
        this.wage = wage;
        this.transactions = transactions;
    }

    public TransactionValidationRequest() {
    }
}
