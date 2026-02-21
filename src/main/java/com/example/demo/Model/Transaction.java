package com.example.demo.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private double amount;
    private double ceiling;
    private double remanent;
    private Boolean inkPeriod;

    // Private constructor for builder
    private Transaction(TransactionBuilder builder) {
        this.date = builder.date;
        this.amount = builder.amount;
        this.ceiling = builder.ceiling;
        this.remanent = builder.remanent;
        this.inkPeriod = builder.inkPeriod;
    }

    // Keep no-arg constructor for framework compatibility
    public Transaction() {
    }

    // Keep all-args constructor for backward compatibility
    public Transaction(LocalDateTime date, double amount, double ceiling, double remanent) {
        this.date = date;
        this.amount = amount;
        this.ceiling = ceiling;
        this.remanent = remanent;
        this.inkPeriod = null;
    }

    // Static method to get builder
    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    // Getters
    public double getRemanent() {
        return remanent;
    }

    public void setRemanent(double remanent) {
        this.remanent = remanent;
    }

    public double getCeiling() {
        return ceiling;
    }

    public void setCeiling(double ceiling) {
        this.ceiling = ceiling;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getInkPeriod() {
        return inkPeriod;
    }

    public void setInkPeriod(Boolean inkPeriod) {
        this.inkPeriod = inkPeriod;
    }

    // Builder class
    public static class TransactionBuilder {
        private LocalDateTime date;
        private double amount;
        private double ceiling;
        private double remanent;
        private Boolean inkPeriod;

        public TransactionBuilder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public TransactionBuilder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public TransactionBuilder ceiling(double ceiling) {
            this.ceiling = ceiling;
            return this;
        }

        public TransactionBuilder remanent(double remanent) {
            this.remanent = remanent;
            return this;
        }

        public TransactionBuilder inkPeriod(Boolean inkPeriod) {
            this.inkPeriod = inkPeriod;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}
