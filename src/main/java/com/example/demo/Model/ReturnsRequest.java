package com.example.demo.Model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class ReturnsRequest {

    @NotNull @Positive
    private Integer age;

    @NotNull @Positive
    private Double wage;

    @NotNull @Positive
    private Double inflation;

    private List<QPeriod> q;
    private List<PPeriod> p;
    private List<KPeriod> k;

    @NotNull
    private List<Transaction> transactions;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getWage() {
        return wage;
    }

    public void setWage(Double wage) {
        this.wage = wage;
    }

    public Double getInflation() {
        return inflation;
    }

    public void setInflation(Double inflation) {
        this.inflation = inflation;
    }

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

    public ReturnsRequest(Integer age, Double wage, Double inflation, List<QPeriod> q, List<PPeriod> p, List<KPeriod> k, List<Transaction> transactions) {
        this.age = age;
        this.wage = wage;
        this.inflation = inflation;
        this.q = q;
        this.p = p;
        this.k = k;
        this.transactions = transactions;
    }

    public ReturnsRequest() {
    }
}