package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

public class ExpenseDTO {

    @NotNull(message = "timestamp is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @NotNull(message = "amount is required")
    @PositiveOrZero(message = "amount must be >= 0")
    private Double amount;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ExpenseDTO(LocalDateTime date, Double amount) {
        this.date = date;
        this.amount = amount;
    }

    public ExpenseDTO() {
    }
}
