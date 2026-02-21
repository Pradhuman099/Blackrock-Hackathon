package com.example.demo.Model;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.RoundingMode;
import java.time.LocalDateTime;

public class SavingsResult {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;

    private Double amount;

    // Used only for returns endpoints
    private Double profits;       // inflation-adjusted return
    private Double taxBenefit;    // only for NPS (0 for index)

    // Constructor for temporal grouping only
    public SavingsResult(LocalDateTime start,
                         LocalDateTime end,
                         Double amount) {
        this.start = start;
        this.end = end;
        this.amount = amount;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        if (amount == null) {
            this.amount = null;
        } else {
            this.amount = Math.round(amount * 100.0) / 100.0;
        }
    }

    public Double getProfits() {
        return profits;
    }

    public void setProfits(Double profits) {
        this.profits = profits;
    }

    public Double getTaxBenefit() {
        return taxBenefit;
    }

    public void setTaxBenefit(Double taxBenefit) {
        this.taxBenefit = taxBenefit;
    }

    public SavingsResult() {
    }

    public SavingsResult(LocalDateTime start, LocalDateTime end, Double amount, Double profits, Double taxBenefit) {
        this.start = start;
        this.end = end;
        this.amount = amount;
        this.profits = profits;
        this.taxBenefit = taxBenefit;
    }
}
