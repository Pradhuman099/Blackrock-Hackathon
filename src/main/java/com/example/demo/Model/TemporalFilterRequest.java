package com.example.demo.Model;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class TemporalFilterRequest {

    private List<QPeriod> q;
    private List<PPeriod> p;
    private List<KPeriod> k;
    private Double wage;

    public Double getWage() {
        return wage;
    }

    public void setWage(Double wage) {
        this.wage = wage;
    }

    @NotNull
    private List<Transaction> transactions;

    public List<QPeriod> getQ() {
        return q;
    }

    public void setQ(List<QPeriod> q) {
        this.q = q;
    }

    public List<PPeriod> getP() {
        return p;
    }

    public void setP(List<PPeriod> p) {
        this.p = p;
    }

    public List<KPeriod> getK() {
        return k;
    }

    public void setK(List<KPeriod> k) {
        this.k = k;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public TemporalFilterRequest(List<QPeriod> q, List<PPeriod> p, List<KPeriod> k, List<Transaction> transactions, Double wage) {
        this.q = q;
        this.p = p;
        this.k = k;
        this.transactions = transactions;
        this.wage = wage;
    }

    public TemporalFilterRequest() {
    }
}
